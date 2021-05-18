package com.rabbitmqdemo.listener;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    private static final String MY_QUEUE = "MyQueue";

    @Bean
    Queue myQueue(){
        return new Queue(MY_QUEUE,true);
    }

    @Bean
    Exchange myExchange(){
        return ExchangeBuilder.topicExchange("MyTopicExchange").durable(true).build();
    }

    //creating binding between exchange MyExchange and queue MyQueue
    @Bean
    Binding binding(){
//        return new Binding(MY_QUEUE, Binding.DestinationType.QUEUE, "MyTopicExchange", "topic", null);
        return BindingBuilder
                .bind(myQueue())
                .to(myExchange())
                .with("topic")
                .noargs();
    }


    @Bean
    ConnectionFactory connectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setUsername("guest"); //default username in rabbitmq
        cachingConnectionFactory.setPassword("guest");
        return cachingConnectionFactory;
    }

    @Bean
    MessageListenerContainer messageListenerContainer(){
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory());
        simpleMessageListenerContainer.setQueues(myQueue());
        simpleMessageListenerContainer.setMessageListener(new RabbitMQMessageListener());
        return simpleMessageListenerContainer;
    }
}
//Queue ->
//1. Define the queue to listen.
//2. Provide the Connnection to the Queue.
//3. Bind the Queue, Connection and Listener. --> MessageListnerContainer doing that
//Connection->
//used ConnectionFactory so that our application can connect to the rabbitmq broker-
//-and create the queue for us.
//MessageListener->
//for listening to message we created messageListener and for the class to listen the message
//we've created RabbitMQMessageListener class.
// => ie any message coming to this queue (myQueue here) will be coming to this class ie RabbitMQMessageListener class
//: can go and publish a message from rabbitmq dashboard inorder to check it, the message published from there
//will be displayed here on console
