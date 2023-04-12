package com.example.springboot_vue.rabbitmq_test;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQUtils {
    private static final String username = "guest";
    private static final String password = "guest";
    private static final String ip = "127.0.0.1";
    private static final int port = 5672;
    private static final String virtualHost = "";

    public static Connection createConnection() throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setHost(ip);
        connectionFactory.setPort(port);

        return connectionFactory.newConnection();
    }
}
