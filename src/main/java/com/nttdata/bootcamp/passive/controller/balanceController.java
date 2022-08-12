package com.nttdata.bootcamp.passive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.passive.model.Passive;
import com.nttdata.bootcamp.passive.service.PassiveService;

import reactor.core.publisher.Mono;

/**
 * Service Rest.
 *
 */
@RestController
@RequestMapping("/balance")
public class balanceController {
	
	@Autowired
	PassiveService passiveService;
	
	
	/**
	   * Balance  Account.
	   * 
	   */
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Passive>> findById(@PathVariable("id") String id){
		
		return passiveService.findById(id)
				.flatMap(p -> {
					Passive passive = new Passive();
					if(Boolean.TRUE.equals(p.getAccountSavings().getFlagPrincipal())) {
						p.getAccountSavings().getTransactions().removeAll(p.getAccountSavings().getTransactions());
						passive.setAccountSavings(p.getAccountSavings());
					}else if (Boolean.TRUE.equals(p.getAccountCurrent().get(0).getFlagPrincipal())) {
						p.getAccountCurrent().get(0).getTransactions().removeAll(p.getAccountCurrent().get(0).getTransactions());
						passive.setAccountCurrent(p.getAccountCurrent());
					}
					return Mono.just(passive);
				})
				.map(p -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(p));
	}

}
