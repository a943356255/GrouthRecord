package com.example.springboot_vue.controller.crud_interface;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * message（消息体）、replyCode（响应 code）、replyText（响应内容）、exchange（交换机）、routingKey（队列）。
 * 如果消息未能投递到目标 queue 里将触发回调 returnCallback ，一旦向 queue 投递消息未成功，这里一般会记录下当前消息的详细投递数据，方便后续做重发或者补偿等操作。
 */
@Component
public class ReturnCallbackService implements RabbitTemplate.ReturnsCallback{

    /*
      这里，returnedMessage是一个对象，它其实就包含了上边说的那几种属性
     */
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        System.out.println("returnedMessage = " + returnedMessage);
    }
}
