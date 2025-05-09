package com.test.transactionservice.notification;

import com.test.transactionservice.controller.TransactionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {
    private static final Logger LOGGER
            = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message, String topicName) {
        LOGGER.info("Sending : {}", message);
        LOGGER.info("--------------------------------");

        kafkaTemplate.send(topicName, message);
    }
}
