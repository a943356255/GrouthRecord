package com.example.springboot_vue.netty.demo2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class Main {

    public static void main(String[] args) {

    }

    // Reactor单线程模式
    public void singleReactor() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(eventLoopGroup);
    }

    // 非主从Reactor多线程模式
    public void nonMasterMoreReactor() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(eventLoopGroup);
    }

    // 主从Reactor多线程模式
    public void masterMoreReactor() {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boosGroup, workGroup);
    }
}
