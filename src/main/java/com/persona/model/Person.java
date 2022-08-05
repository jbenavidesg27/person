package com.persona.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
  
  private List<Address> address;
  
  private Documents documents;
  
  private PersonLegal personLegal;
  
  private PersonNatural personNatural;
  
  

}
