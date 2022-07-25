package com.nttdata.bootcamp.passive.model.dto;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDto {
	@Id
	private String id;
	
	private String typePerson;
	
	private List<Address> address;
	
	private Documents documents;
	
	private PersonLegal personLegal;
	
	private PersonNatural personNatural;

}
