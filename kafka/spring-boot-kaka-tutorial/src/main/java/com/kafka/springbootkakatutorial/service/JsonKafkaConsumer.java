package com.kafka.springbootkakatutorial.service;

import com.kafka.springbootkakatutorial.paload.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class JsonKafkaConsumer {
    private static Logger LOGGER = LoggerFactory.getLogger(JsonKafkaConsumer.class);

    @KafkaListener(topics = "${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.group-id}")
    private void consume(User user) {
        LOGGER.info(String.format("Json message received -> %s", user.toString()));
    }
}
