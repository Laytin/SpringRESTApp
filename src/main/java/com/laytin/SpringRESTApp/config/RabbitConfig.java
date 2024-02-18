package com.laytin.SpringRESTApp.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class RabbitConfig {
    //private String[] keys = {"order-create","order-edit"};

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");
        cachingConnectionFactory.setVirtualHost("/");
        return cachingConnectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public Queue queueCreate() {
        return new Queue("q.order-create");
    }
    @Bean
    public Queue queueEdit() {
        return new Queue("q.order-edit");
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange("emailExchange", true, false);
    }

    @Bean
    public Binding binding1(Queue queueCreate, DirectExchange exchange) {
        return BindingBuilder.bind(queueCreate).to(exchange).with("r.order-create");
    }
    @Bean
    public Binding binding2(Queue queueEdit, DirectExchange exchange) {
        return BindingBuilder.bind(queueEdit).to(exchange).with("r.order-edit");
    }
}
