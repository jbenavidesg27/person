package com.persona.service.impl;

import com.persona.dao.PersonDao;
import com.persona.model.Person;
import com.persona.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implement.
 *
 */
@Service
public class PersonServiceImpl implements PersonService {
  
  @Autowired
  PersonDao personDao;

  @Override
  public Mono<Person> save(Person person) {
    
    return personDao.save(person);
  }

  @Override
  public Mono<Person> update(Person person) {
    
    return personDao.save(person);
  }

  @Override
  public Flux<Person> findAll() {
    
    return personDao.findAll();
  }

  @Override
  public Mono<Person> findById(String id) {
    
    return personDao.findById(id);
  }

}
