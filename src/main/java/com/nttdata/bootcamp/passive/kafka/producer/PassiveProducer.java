package com.nttdata.bootcamp.passive.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PassiveProducer {

	private static final Logger log = LoggerFactory.getLogger(PassiveProducer.class.getName());

	private final KafkaTemplate<String, String> kafkaTemplate;

	public PassiveProducer(@Qualifier("kafkaStringTemplate") KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(String message) {
		log.info("Producing message {}", message);
		this.kafkaTemplate.send("passive-topic", message);
	}

}
