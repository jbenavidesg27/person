package com.persona.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.persona.kafka.producer.PersonProducer;
import com.persona.model.Person;
import com.persona.service.PersonService;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Rest.
 *
 */
@RestController
@RequestMapping("/person")
public class PersonController {
  
  @Autowired
  PersonService personService;
  
  @Autowired
  PersonProducer personProducer;
  
  /**
   * Peticiones Rest.
   * List all active.
   */
  @GetMapping
  public Mono<ResponseEntity<Flux<Person>>>  findAll() {
    Flux<Person> fx = personService.findAll();
    return Mono.just(ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(fx));
  }
  
  /**
   * List Active for Id.
   * 
   */
  @GetMapping("/{id}")
  public Mono<ResponseEntity<Person>> findById(@PathVariable("id") String id) {
    return personService.findById(id)
    		.flatMap(j -> {					
				try {
					ObjectMapper mapper = new ObjectMapper();
					// Java objects to JSON string - compact-print
			        String jsonString = mapper.writeValueAsString(j);
			        personProducer.sendMessages(jsonString);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return Mono.just(j);
			})
        .map(p -> ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(p));
  }
  
  /**
   * Save Active.
   * 
   */
  @PostMapping
  public Mono<ResponseEntity<Person>> save(@RequestBody Person person, 
      final ServerHttpRequest req) {
    return personService.save(person)
        .map(p -> ResponseEntity.created(URI.create(req.getURI()
        .toString().concat("/").concat(p.getId())))
            .contentType(MediaType.APPLICATION_JSON)
            .body(p));
  }
  
  /**
   * Update active for Id.
   * 
   */
  @PutMapping("/{id}")
  public Mono<ResponseEntity<Person>> update(@PathVariable("id") String id, 
      @RequestBody Person person) {
    Mono<Person> monoBody = Mono.just(person);
    Mono<Person> monoBd = personService.findById(id);
    return monoBd.zipWith(monoBody, (bd, ps) -> {
      bd.setId(id);
      bd.setDocuments(ps.getDocuments());
      bd.setPersonLegal(ps.getPersonLegal());
      bd.setPersonNatural(ps.getPersonNatural());
      bd.setTypePerson(ps.getTypePerson());
      bd.setAddress(ps.getAddress());
      return bd;
    })
        .flatMap(personService::update)
        .map(y -> ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(y))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

}
