package com.persona.service;

import com.persona.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Person.
 *
 */
public interface PersonService {

  Mono<Person> save(Person person);
  
  Mono<Person> update(Person person);
  
  Flux<Person> findAll();
  
  Mono<Person> findById(String id);

}
