package com.persona.dao;

import com.persona.model.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * DAO Person connect Mongo.
 *
 */
public interface PersonDao extends ReactiveMongoRepository<Person, String> {

}
