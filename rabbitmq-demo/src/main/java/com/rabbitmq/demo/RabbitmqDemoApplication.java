package com.rabbitmq.demo;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqDemoApplication implements CommandLineRunner {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	public static void main(String[] args) {
		SpringApplication.run(RabbitmqDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		rabbitTemplate.convertAndSend( "Hello Nishu!"); //--> convertAndSend(Object obj)-> sends the msg to all the queues using default binding, its not good to use this
//		rabbitTemplate.convertAndSend("TestExchange", "testRouting", "Hello Nishu!!!!");

		SimpleMessage simpleMessage = new SimpleMessage();
		simpleMessage.setName("firstName");
		simpleMessage.setDescription("firstDescription!");
		rabbitTemplate.convertAndSend("TestExchange", "testRouting", simpleMessage);

		//this message routing to ExampleQueue which is created in rabbitmq-demo-listener project, can see code
		rabbitTemplate.convertAndSend("TestExchange","exampleQueue","message from RabbitmqDemo!!!");

		//sending message to MyTopicExchange --> which has a binding (with "topic" as routingKey) to MyQueue queue
		rabbitTemplate.convertAndSend("MyTopicExchange", "topic","Finally done creating exchange, queue, and binding them with writing program ie without using rabbitmq management ui!");
	}
}
