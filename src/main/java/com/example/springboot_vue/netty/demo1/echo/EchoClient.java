package com.example.springboot_vue.netty.demo1.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoClient {

    public static void main(String[] args) {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler((new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline p = socketChannel.pipeline();
                            p.addLast(new LoggingHandler(LogLevel.INFO));
                            p.addLast((ChannelHandler) new EchoClientHandler());
                        }
                    }));

            ChannelFuture f = bootstrap.connect("127.0.0.1", 8090).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();

        }
    }

}
