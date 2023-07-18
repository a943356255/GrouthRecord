package proxy;

import cn.hutool.core.thread.GlobalThreadPool;
import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Slf4j
public class SimpleTransProxy {

    public static void main(String[] args) throws IOException {
        int port = 8006;
        ServerSocketChannel localServer = ServerSocketChannel.open();
        localServer.bind(new InetSocketAddress(port));
        Reactor reactor = new Reactor();
        // REACTOR线程
//        GlobalThreadPool.REACTOR_EXECUTOR.submit(reactor::run);

        // WORKER单线程调试
        while (localServer.isOpen()) {
            // 此处阻塞等待连接
            SocketChannel remoteClient = localServer.accept();

            // 工作线程
            GlobalThreadPool.submit(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    // 代理到远程
                    SocketChannel remoteServer = new ProxyHandler().proxy(remoteClient);

                    // 透明传输
                    reactor.pipe(remoteClient, remoteServer)
                            .pipe(remoteServer, remoteClient);
                }
            });
        }
    }
}

@Data
class ProxyHandler {
    private String method;
    private String host;
    private int port;
    private SocketChannel remoteServer;
    private SocketChannel remoteClient;

    /**
     * 原始信息
     */
    private List<ByteBuffer> buffers = new ArrayList<>();
    private StringBuilder stringBuilder = new StringBuilder();

    /**
     * 连接到远程
     * @param remoteClient
     * @return
     * @throws IOException
     */
    public SocketChannel proxy(SocketChannel remoteClient) throws IOException {
        this.remoteClient = remoteClient;
        connect();
        return this.remoteServer;
    }

    public void connect() throws IOException {
        // 解析METHOD, HOST和PORT
        beforeConnected();

        // 链接REMOTE SERVER
        createRemoteServer();

        // CONNECT请求回应，其他请求WRITE THROUGH
        afterConnected();
    }

    protected void beforeConnected() throws IOException {
        // 读取HEADER
        readAllHeader();

        // 解析HOST和PORT
        parseRemoteHostAndPort();
    }

    /**
     * 创建远程连接
     * @throws IOException
     */
    protected void createRemoteServer() throws IOException {
        remoteServer = SocketChannel.open(new InetSocketAddress(host, port));
    }

    /**
     * 连接建立后预处理
     * @throws IOException
     */
    protected void afterConnected() throws IOException {
        // 当CONNECT请求时，默认写入200到CLIENT
        if ("CONNECT".equalsIgnoreCase(method)) {
            // CONNECT默认为443端口，根据HOST再解析
            remoteClient.write(ByteBuffer.wrap("HTTP/1.0 200 Connection Established\r\nProxy-agent: nginx\r\n\r\n".getBytes()));
        } else {
            writeThrouth();
        }
    }

    protected void writeThrouth() {
        buffers.forEach(byteBuffer -> {
            try {
                remoteServer.write(byteBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 读取请求内容
     * @throws IOException
     */
    protected void readAllHeader() throws IOException {
        while (true) {
            ByteBuffer clientBuffer = newByteBuffer();
            int read = remoteClient.read(clientBuffer);
            clientBuffer.flip();
            appendClientBuffer(clientBuffer);
            if (read < clientBuffer.capacity()) {
                break;
            }
        }
    }

    /**
     * 解析出HOST和PROT
     * @throws IOException
     */
    protected void parseRemoteHostAndPort() throws IOException {
        // 读取第一批，获取到METHOD
        method = parseRequestMethod(stringBuilder.toString());

        // 默认为80端口，根据HOST再解析
        port = 80;
        if ("CONNECT".equalsIgnoreCase(method)) {
            port = 443;
        }

        this.host = parseHost(stringBuilder.toString());

        URI remoteServerURI = URI.create(host);
        host = remoteServerURI.getHost();

        if (remoteServerURI.getPort() > 0) {
            port = remoteServerURI.getPort();
        }
    }

    protected void appendClientBuffer(ByteBuffer clientBuffer) {
        buffers.add(clientBuffer);
        stringBuilder.append(new String(clientBuffer.array(), clientBuffer.position(), clientBuffer.limit()));
    }

    protected static ByteBuffer newByteBuffer() {
        // buffer必须大于7，保证能读到method
        return ByteBuffer.allocate(128);
    }

    private static String parseRequestMethod(String rawContent) {
        // create uri
        return rawContent.split("\r\n")[0].split(" ")[0];
    }

    private static String parseHost(String rawContent) {
        String[] headers = rawContent.split("\r\n");
        String host = "host:";
        for (String header : headers) {
            if (header.length() > host.length()) {
                String key = header.substring(0, host.length());
                String value = header.substring(host.length()).trim();
                if (host.equalsIgnoreCase(key)) {
                    if (!value.startsWith("http://") && !value.startsWith("https://")) {
                        value = "http://" + value;
                    }
                    return value;
                }
            }
        }
        return "";
    }

}

@Slf4j
@Data
class Reactor {

    private Selector selector;

    private volatile boolean finish = false;

    @SneakyThrows
    public Reactor() {
        selector = Selector.open();
    }

    @SneakyThrows
    public Reactor pipe(SocketChannel from, SocketChannel to) {
        from.configureBlocking(false);
        from.register(selector, SelectionKey.OP_READ, new SocketPipe(this, from, to));
        return this;
    }

    @SneakyThrows
    public void run() {
        try {
            while (!finish) {
                if (selector.selectNow() > 0) {
                    Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                    while (it.hasNext()) {
                        SelectionKey selectionKey = it.next();
                        if (selectionKey.isValid() && selectionKey.isReadable()) {
                            ((SocketPipe) selectionKey.attachment()).pipe();
                        }
                        it.remove();
                    }
                }
            }
        } finally {
            close();
        }
    }

    @SneakyThrows
    public synchronized void close() {
        if (finish) {
            return;
        }
        finish = true;
        if (!selector.isOpen()) {
            return;
        }
        for (SelectionKey key : selector.keys()) {
            closeChannel((Channel) key.channel());
            key.cancel();
        }
        if (selector != null) {
            selector.close();
        }
    }

    public void cancel(SelectableChannel channel) {
        SelectionKey key = channel.keyFor(selector);
        if (Objects.isNull(key)) {
            return;
        }
        key.cancel();
    }

    @SneakyThrows
    public void closeChannel(Channel channel) {
        SocketChannel socketChannel = (SocketChannel)channel;
        if (socketChannel.isConnected() && socketChannel.isOpen()) {
            socketChannel.shutdownOutput();
            socketChannel.shutdownInput();
        }
        socketChannel.close();
    }
}

@Data
@AllArgsConstructor
class SocketPipe {

    private Reactor reactor;

    private SocketChannel from;

    private SocketChannel to;

    @SneakyThrows
    public void pipe() {
        // 取消监听
        clearInterestOps();

//        GlobalThreadPool.PIPE_EXECUTOR.submit(new Runnable() {
//            @SneakyThrows
//            @Override
//            public void run() {
//                int totalBytesRead = 0;
//                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//                while (valid(from) && valid(to)) {
//                    byteBuffer.clear();
//                    int bytesRead = from.read(byteBuffer);
//                    totalBytesRead = totalBytesRead + bytesRead;
//                    byteBuffer.flip();
//                    to.write(byteBuffer);
//                    if (bytesRead < byteBuffer.capacity()) {
//                        break;
//                    }
//                }
//                if (totalBytesRead < 0) {
//                    reactor.closeChannel((Channel) from);
//                    reactor.cancel(from);
//                } else {
//                    // 重置监听
//                    resetInterestOps();
//                }
//            }
//        });
    }

    protected void clearInterestOps() {
        from.keyFor(reactor.getSelector()).interestOps(0);
        to.keyFor(reactor.getSelector()).interestOps(0);
    }

    protected void resetInterestOps() {
        from.keyFor(reactor.getSelector()).interestOps(SelectionKey.OP_READ);
        to.keyFor(reactor.getSelector()).interestOps(SelectionKey.OP_READ);
    }

    private boolean valid(SocketChannel channel) {
        return channel.isConnected() && channel.isRegistered() && channel.isOpen();
    }
}
