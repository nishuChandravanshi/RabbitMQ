package com.rabbitmqdemo.listener;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQExchangeConfiguration {
    //one way of creating Exchange
    @Bean
    Exchange exampleExchange(){
        return new TopicExchange("ExampleExchange");
    }

    //other way
    @Bean
    Exchange example2ndExchange(){
        return ExchangeBuilder.directExchange("Example2ndExchange").autoDelete().internal().build();
    }

}
