package com.nttdata.bootcamp.passive.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nttdata.bootcamp.passive.model.dto.ClientDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
	
	private AccountCurrent accountCurrent;
	
	private AccountSavings accountSavings;
	
	private List<FixedTerm> fixedTerm;
	
	private ClientDto client;
	

}
