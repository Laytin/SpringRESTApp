package com.laytin.SpringRESTApp.utils.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendObject(Object o, String key) {
        System.out.println("sending: "+key+ " : "+ o.toString() );
        rabbitTemplate.convertAndSend("testExchange", "order-create","helloworld");
    }
}
