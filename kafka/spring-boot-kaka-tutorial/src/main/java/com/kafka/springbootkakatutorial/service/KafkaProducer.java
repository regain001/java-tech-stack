package com.kafka.springbootkakatutorial.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;


@Service
public class KafkaProducer {

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        LOGGER.info(String.format("Message sent %s", message));

        // If we just send the message to Broker
        // kafkaTemplate.send(topicName, message);

        // Or If we want to use the message after sending to the broker.
        // This thread will block until success or failure.
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);
        try {
            System.out.println("Sent message=[" + message + "] with offset=["+future.get().getRecordMetadata().offset()+"]");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
