package com.persona.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

@Configuration
public class PersonKafkaConfig {
	public SenderOptions<String, String> senderOptions(){	
		Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);        
        return SenderOptions.create(config) ;
	}

    @Bean(name = "kafkaStringSender")
    KafkaSender<String, String> kafkaSender() {
        return KafkaSender.create(senderOptions());
    }

}
