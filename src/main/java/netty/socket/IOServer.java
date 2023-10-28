package netty.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class IOServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        // 接收新连接的线程，这个线程没有使用单例模式，也会多次创建，其实这里可以使用单例，之创建一次，后续的请求都不再创建
        // 上边的理解是错误的，这个线程并不会创建多次，只有服务端启动的时候会创建一次
        new Thread(() -> {
            while (true) {
                try {
                    // 阻塞方法获取新的连接
                    Socket socket = serverSocket.accept();

                    // 每一个新的连接都创建一个线程，负责读取数据
                    new Thread(() -> {
                        int len;
                        byte[] data = new byte[1024];
                        try {
                            InputStream inputStream = socket.getInputStream();
                            // 按字节流读取数据
                            while ((len = inputStream.read(data)) != -1) {
                                System.out.println(new String(data, 0, len));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
