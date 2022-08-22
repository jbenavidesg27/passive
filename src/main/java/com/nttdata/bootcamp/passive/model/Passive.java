package com.nttdata.bootcamp.passive.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nttdata.bootcamp.common.event.AccountStatus;
import com.nttdata.bootcamp.common.event.PersonStatus;
import com.nttdata.bootcamp.passive.model.dto.Client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model Passive.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "passives")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Passive {
  @Id
  private String id;
  
  private Boolean flagSavings;
  
  private Boolean flagCurrent;
  
  private List<AccountCurrent> accountCurrent;
  
  private AccountSavings accountSavings;
  
  private FixedTerm fixedTerm;
  
  private Client person;
  
  private PersonStatus personStatus;
  
  private AccountStatus accountStatus;
  

}
