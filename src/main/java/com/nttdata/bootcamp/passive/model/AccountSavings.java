package com.nttdata.bootcamp.passive.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model Account Savings.
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountSavings extends AccountGeneric {
  
  private BigDecimal maxMovement;
  private BigDecimal movement;
  private List<Transaction> transactions;
  
}
