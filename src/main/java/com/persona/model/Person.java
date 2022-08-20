package com.persona.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nttdata.bootcamp.common.event.AccountStatus;
import com.nttdata.bootcamp.common.event.PersonStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model Person.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "person")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {
  
  @Id
  private String id;
  
  private String typePerson;
  
  private String typeAccount;
  
  private String account;
  
  private Documents documents;
  
  private Client client;
  
  
  private PersonStatus personStatus;
 
  private AccountStatus accountStatus;
  
  

}
