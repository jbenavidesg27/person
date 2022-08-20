package com.persona.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.common.dto.PersonDto;
import com.nttdata.bootcamp.common.event.PersonEvent;
import com.nttdata.bootcamp.common.event.PersonStatus;

import reactor.core.publisher.Sinks;

@Service
public class PersonStatusPublisher {
	 @Autowired
	 private Sinks.Many<PersonEvent> personSinks;
	 
	 public void publishPersonEvent(PersonDto orderRequestDto, PersonStatus orderStatus){
		 PersonEvent personEvent=new PersonEvent(orderRequestDto,orderStatus);
		 personSinks.tryEmitNext(personEvent);
	    }

}
