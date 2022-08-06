package com.nttdata.bootcamp.passive.model.dto;

import java.math.BigDecimal;
import java.util.List;

import com.nttdata.bootcamp.passive.model.AccountGeneric;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCurrentDTO extends AccountGeneric {
	private BigDecimal maintdenance;
	private List<TransactionDTO> transactions;

}
