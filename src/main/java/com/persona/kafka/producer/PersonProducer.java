package com.persona.kafka.producer;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.nttdata.bootcamp.common.event.PersonEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class PersonProducer {
	@Bean
    public Sinks.Many<PersonEvent> personSinks(){
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<PersonEvent>> personSupplier(Sinks.Many<PersonEvent> sinks){
       return sinks::asFlux;
    }

	
	

}
