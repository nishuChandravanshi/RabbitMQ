package com.rabbitmqdemo.listener;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQQueueConfiguration {
    //one way of creating queue
    @Bean
    Queue exampleQueue(){
        return new Queue("ExampleQueue",false);
    }
    //other way  (similary there are many ways)
    @Bean
    Queue example2ndQueue(){
        return QueueBuilder.durable("Example2ndQueue").autoDelete().exclusive().build();
    }

}
