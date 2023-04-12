package com.example.springboot_vue.rabbitmq_test;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQCustomer {

    public static void main(String[] args) throws IOException, TimeoutException {
        new RabbitMQCustomer().testCustomer();
    }

    // 消费方会一直监听，每当有消息就会处理
    public void testCustomer() throws IOException, TimeoutException {

        Connection connection = RabbitMQUtils.createConnection();
        Channel channel = connection.createChannel();

        // 设置每个队列每次只能处理一条消息 配合下边的false，不按照自动分配。可以实现处理快的多处理
        // channel.basicQos(1);

        channel.basicConsume("testRabbitMQ", true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                // 获取key
                String routingKey = envelope.getRoutingKey();
                // 获取响应头信息
                String contentType = properties.getContentType();

                System.out.println(new String(body));
            }
        });
    }

}
