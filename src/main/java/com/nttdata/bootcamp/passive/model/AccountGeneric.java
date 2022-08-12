package com.nttdata.bootcamp.passive.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model Account Generic.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountGeneric {
  @Id
  String id;
  private String description;
  private BigDecimal account;
  private Date dateTransaction;
  private Boolean flagPrincipal;

}
