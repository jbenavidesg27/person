package com.persona.kafka.producer;

import java.time.Instant;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Component
public class PersonProducer {
private static final Logger log = LoggerFactory.getLogger(PersonProducer.class.getName());	
	
	private final KafkaSender<String, String> kafkaSender; 
	
	private static final String TOPIC = "person-topic";

	public PersonProducer(@Qualifier("kafkaStringSender")KafkaSender<String, String> kafkaSender) {
		this.kafkaSender = kafkaSender;
	}
	
	public void sendMessages(String message) throws InterruptedException {
		
		kafkaSender.send(Flux.just(message)
                .map(i -> SenderRecord.create(new ProducerRecord<>(TOPIC, i), i)))
                .doOnError(e -> log.error("Send failed", e))
				.subscribe(r -> {
					RecordMetadata metadata = r.recordMetadata();
					Instant timestamp = Instant.ofEpochMilli(metadata.timestamp());
					log.info("Message {}", r.correlationMetadata());
					log.info("Sent successfully, topic-partition={} offset={} timestamp={}"
							,metadata.topic()+metadata.partition(), 
							metadata.offset(),timestamp);
					});
    }
	
	public void close() {
		kafkaSender.close();
    }
	
	

}
