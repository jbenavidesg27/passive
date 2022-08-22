package com.nttdata.bootcamp.passive.model.dto;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model Client.
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client {
  @Id
  private String id;
  
  private String typePerson;
  
  private List<Address> address;
  
  private Documents documents;
  
  private PersonLegal personLegal;
  
  private PersonNatural personNatural;

}
