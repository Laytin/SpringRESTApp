package com.laytin.SpringRESTApp.utils.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper obm;

    @Autowired
    public RabbitMQSender(RabbitTemplate rabbitTemplate, ObjectMapper obm) {
        this.rabbitTemplate = rabbitTemplate;
        this.obm = obm;
    }

    public void sendObject(Object o, String key) {
        try {
            rabbitTemplate.convertAndSend("emailExchange", key,obm.writeValueAsString(o));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
