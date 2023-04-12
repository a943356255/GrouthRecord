package com.example.springboot_vue.netty;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestNIO {

    public TestNIO() throws IOException {

    }

    public static void main(String[] args) throws IOException {
        TestNIO testNetty = new TestNIO();
        testNetty.testFileChannel();
    }

    public void testFileChannel(){

        // 1.输入输出流 2.RandomAccessFile
        try (FileChannel fileChannel = new FileInputStream("D:\\mysql\\first.txt").getChannel()) {
            // 准备缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(10);

            // read the data from channel and put it into buffer
            // the read function will return a number, if the number is -1 means that the channel has reached end-of-stream
            while (fileChannel.read(byteBuffer) != -1) {
                // change the buffer type to read
                byteBuffer.flip();
                // check whether there are left bytes in the buffer, if nothing, will return false
                while (byteBuffer.hasRemaining()) {
                    // get one byte per time
                    byte b = byteBuffer.get();
                    System.out.println((char) b);
                }
                // change the buffer type to write
                byteBuffer.clear();

                // read the buffer from the beginning
                // if you already read all the bytes or some bytes, and you want to read it again,
                // then you can use this function( rewind() ) to change the point to the beginning
                // byteBuffer.rewind();

                // mark a position that you think it's important
                // byteBuffer.mark();

                // you can use this function to change the index to the position where you have marked
                // byteBuffer.reset();

                // this function will not change the index
                // byteBuffer.get(index)
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testTransferTo() {

        try (
            FileChannel from = new FileInputStream("D:\\mysql\\first.txt").getChannel();
            FileChannel to = new FileInputStream("D:\\mysql\\to.txt").getChannel()
        ) {
            // this function will copy the data from one to another
            // this function will limit the size of the file, the max file size is 2G
            from.transferTo(0, from.size(), to);

            // if the file is bigger than 2g, you can deal it like this
            long size = from.size();
            for (long i = size; i > 0;) {
                // this function will return how much data you have already transfer
                i -= from.transferTo(0, from.size(), to);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createServer() throws IOException {
        // use nio (single thread)
        ByteBuffer buffer = ByteBuffer.allocate(16);
        // create a server
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // this will change the serverSocketChannel to non-block
        // default value is true
        serverSocketChannel.configureBlocking(false);
        // binding the port
        serverSocketChannel.bind(new InetSocketAddress(9000));

        // create a list for channel
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            // accept is used to create a connection with the client
            // SocketChannel is used to communicate with the client
            // if the serverSocketChannel didn't execute serverSocketChannel.configureBlocking(false);,
            // then the accept is a block method which means if there is no client connecting, it will do nothing but wait there
            // if it execute serverSocketChannel.configureBlocking(false), the method will be a non-block method
            // if in the non-block state, socketChannel will be null when there is nothing to accept
            SocketChannel socketChannel = serverSocketChannel.accept();
            // this message will not print until client connect the server
            System.out.println("测试是否通过");
            channels.add(socketChannel);

            for (SocketChannel channel: channels) {
                // accept the data from the client
                // in the block state if there is nothing in the buffer, it will wait there
                // in the non-block state, if there is nothing, it will return 0 and continue running.
                channel.read(buffer);
                buffer.flip();

                buffer.clear();
            }
        }
    }

    public void createClient() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(9000));
        System.out.println("waiting....");
    }

    // p30会详细讲解这段代码的流程
    public void testSelector() throws IOException {
        // create selector
        // one selector can manage more than one channel
        Selector selector = Selector.open();

        // create channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        // binding the channel to the selector
        // when the event happened, we can use the selectionKey to find which channel trigger the event
        SelectionKey serverSocketChannelKey = serverSocketChannel.register(selector, 0, null);
        // this will make the serverSocketChannelKey only deal the accept event
        serverSocketChannelKey.interestOps(SelectionKey.OP_ACCEPT);
        serverSocketChannel.bind(new InetSocketAddress(9000));

        while (true) {
            // it will be blocked until there are some events
            // the event type should be the appointed (serverSocketChannelKey.interestOps(SelectionKey.OP_ACCEPT);)
            // if there is something not deal, it will not be block
            selector.select();

            // the event
            // selectedKeys include all the happened events
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // as for why, watch p30
                iterator.remove();
                // distinguish the event type
                if (key.isAcceptable()) {
                    // ServerSocketChannel is used to build a connection
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    SelectionKey selectionKey = socketChannel.register(selector, 0, null);
                    selectionKey.interestOps(SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    try {
                        // SocketChannel is used to read data
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        int read = channel.read(buffer);
                        // if the client cut the connection, it will trigger a read event but maybe it send nothing
                        // so we can use this way to check whether the connection being cut.
                        // if we don't cancel the key, it will be considered a untreated event can make the server wrong
                        if (read == -1) {
                            key.cancel();
                        } else {
                            channel.read(buffer);
                            buffer.flip();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // if there is an exception, then we should remove the key from the selector
                        key.cancel();
                    }
                }

                // cancel one event if you don't want to deal it
//                key.cancel();
            }
        }
    }
}
