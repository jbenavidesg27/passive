package com.nttdata.bootcamp.passive.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassiveDTO {
	
	private String id;
	
	private Boolean flagSavings;
	
	private Boolean flagCurrent;
	
	private List<AccountCurrentDTO> accountCurrent;
	
	private AccountSavingsDTO accountSavings;
	
	private FixedTermDTO fixedTerm;
	
	private Client client;

}
