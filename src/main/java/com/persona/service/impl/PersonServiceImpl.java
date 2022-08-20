package com.persona.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nttdata.bootcamp.common.dto.PersonDto;
import com.nttdata.bootcamp.common.event.PersonStatus;
import com.persona.dao.PersonDao;
import com.persona.model.Person;
import com.persona.service.PersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implement.
 *
 */
@Service
public class PersonServiceImpl implements PersonService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);

  
  @Autowired
  PersonDao personDao;
  
  @Autowired
  PersonStatusPublisher publisher;

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Mono<Person> save(PersonDto person) {	  
	  ModelMapper mapper =  new ModelMapper();
	  Person personModel = mapper.map(person, Person.class);
	  personModel.setPersonStatus(PersonStatus.CREATED);
	  Mono<Person> personMono = personDao.save(personModel);	
	  LOGGER.info("Guardando{}",personMono);
//	  personMono.flatMap(p -> {
//		  PersonDto persons = new PersonDto();
//		  ClientDto client = new ClientDto();
//		  DocumentsDto doDto = new DocumentsDto();
//		  if(!p.getId().isEmpty()) {
//			  persons.setId(p.getId());
//			  persons.setAccount(p.getAccount());
//			  client.setBusinessName(p.getClient().getBusinessName());
//			  client.setNames(p.getClient().getNames());
//			  client.setSurnames(p.getClient().getSurnames());
//			  persons.setClient(client);
//			  doDto.setDocumentType(p.getDocuments().getDocumentType());
//			  doDto.setNroDocument(p.getDocuments().getNroDocument());
//			  persons.setDocuments(doDto);
//			  persons.setTypeAccount(p.getTypeAccount());
//			  persons.setTypePerson(p.getTypePerson());
////			  
//		  }
//		  publisher.publishPersonEvent(persons, PersonStatus.CREATED);
//		  
//		  return Mono.just(p) ;
//	  });
//	  publisher.publishPersonEvent(person, PersonStatus.CREATED);
    
    return personMono;
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
