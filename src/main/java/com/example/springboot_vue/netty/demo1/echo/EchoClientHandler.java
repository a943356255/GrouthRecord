package com.example.springboot_vue.netty.demo1.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

public class EchoClientHandler {

    private final ByteBuf firstMessage;

    public EchoClientHandler() {
        firstMessage = Unpooled.wrappedBuffer("测试数据...".getBytes(StandardCharsets.UTF_8));
    }
}
