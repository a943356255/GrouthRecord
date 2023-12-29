package com.example.springboot_vue.rabbitmq_test;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RabbitMQProvider {

    public static void main(String[] args) throws IOException, TimeoutException {
        new RabbitMQProvider().testConnection();
    }

    // 这里只是发送消息，消息发布后就会关闭
    public void testConnection() throws IOException, TimeoutException {
        Connection connection = RabbitMQUtils.createConnection();
        System.out.println("连接成功");
        // 创建管道
        Channel channel = connection.createChannel();

        String exchangeName = "testRabbitMQ";
        String routingKey = "test";

        // 通过通道建立交换机 参数2：路由模式
        channel.exchangeDeclare(exchangeName, "direct", true);

        // 创建一个临时队列
        // String queue = channel.queueDeclare().getQueue();
        // 参数3：指定对应的key  只能获取对应key发送的消息
        // channel.queueBind(queue, "exchangeNameDirect", "key1");
        // channel.queueBind(queue, "exchangeNameDirect", "error");
        // channel.queueBind(queue, "exchangeNameDirect", "waring");

        String queenName = "queueName";
        // 通道绑定对应消息队列
        // 参数1：队列名称，不存在自动创建 参数2：是否要持久化，true持久化队列
        // 参数3：是否独占队列，true独占 参数4：是否在消费化后自动删除队列，true自动删
        // 参数5：额外附加参数
        channel.queueDeclare(queenName, true, false, false, null);

        // 将队列和交换级绑定
        channel.queueBind(queenName, exchangeName, routingKey);

        byte[] messageByte = "this is the message that I want to say".getBytes();

        // 发布消息，最简单的形式
        channel.basicPublish(exchangeName, routingKey, null, messageByte);

        // 发布消息
        // 参数1：交换机名称  参数2：队列名称  参数3：传递消息额外设置  参数4：消息的具体内容 参数3这样写表示消息也持久化
        channel.basicPublish("", queenName, MessageProperties.PERSISTENT_TEXT_PLAIN, "hello, rabbitmq".getBytes());

        // 发布消息，并设置一些我不知道的东西 (构建BasicProperties的对象)
        channel.basicPublish(exchangeName, routingKey,
                new AMQP.BasicProperties().
                        builder().
                        contentType("text/plain").
                        deliveryMode(2).
                        priority(1).
                        userId("guest").
                        build(),
                messageByte);

        // 发送消息指定头信息：
        Map<String, Object> headers = new HashMap<>();
        headers.put("latitude",  51.5252949);
        headers.put("longitude", -0.0905493);

        channel.basicPublish(exchangeName, routingKey,
                new AMQP.BasicProperties.Builder()
                        .headers(headers)
                        .build(),
                messageByte);

        // 发送一个有过期时间的消息
        channel.basicPublish(exchangeName, routingKey,
                new AMQP.BasicProperties.Builder()
                        .expiration("60000")
                        .build(),
                messageByte);

        channel.close();
        connection.close();
    }
}
