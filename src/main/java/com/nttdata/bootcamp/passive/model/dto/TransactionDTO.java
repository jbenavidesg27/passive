package com.nttdata.bootcamp.passive.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO {
	private String typeTransaction;
	private BigDecimal account;
	private Date dateTransaction;

}
