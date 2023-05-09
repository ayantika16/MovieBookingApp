package com.example.demo.Service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumeService 
{
	@KafkaListener(topics="movie-topic", groupId="mygroup")
	public void consumeFromTopic(String message)
	{
		System.out.println("Consumer message: "+ message);
	}

}