package com.example.springboot_vue.controller.crud_interface;

import com.example.springboot_vue.mapper.CityMapper;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * correlationData：对象内部只有一个 id 属性，用来表示当前消息的唯一性。
 * ack：消息投递到 broker 的状态，true 表示成功。
 * cause：表示投递失败的原因。
 * 消息只要被 rabbitmq broker 接收到就会触发 confirmCallback 回调 。
 *
 * 这里回调的通知，应该是一个异步线程触发的，没办法保证事务
 */
@Component
public class ConfirmCallbackService implements RabbitTemplate.ConfirmCallback {

    public volatile int mark = 0;

    @Autowired
    CityMapper cityMapper;

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack) {
            System.out.println("消息发送异常!");
        } else {
            System.out.println("消息接收时的id = " + correlationData.getId());
            cityMapper.update(correlationData.getId());
        }
    }
}
