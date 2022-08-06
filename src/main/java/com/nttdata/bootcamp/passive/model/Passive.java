package com.nttdata.bootcamp.passive.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nttdata.bootcamp.passive.model.dto.Client;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
  
  private Client client;
  

}
