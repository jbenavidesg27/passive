package com.nttdata.bootcamp.passive.model.dto;

import java.util.Date;

import com.nttdata.bootcamp.passive.model.AccountGeneric;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FixedTermDTO extends AccountGeneric {
	
	private Date endDate;

}
