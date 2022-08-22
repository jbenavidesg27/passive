package com.nttdata.bootcamp.passive.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.passive.model.Passive;
import com.nttdata.bootcamp.passive.model.Transaction;
import com.nttdata.bootcamp.passive.service.PassiveService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Rest.
 *
 */
@RestController
@RequestMapping("/transaction")
public class TransactionController {
	
	@Autowired
	PassiveService passiveService;
	
	/**
	   * List Transaction limit 10.
	   * 
	   */
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Flux<Transaction>>> findById(@PathVariable("id") String id){
		Mono<Passive> mono = passiveService.findById(id);
		return Mono.just(ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(mono.map(p -> {
					List<Transaction> transactions = new ArrayList<>() ;				
					if(Boolean.TRUE.equals(p.getFlagCurrent())) {
						transactions=p.getAccountCurrent().get(0).getTransactions().stream().
								limit(10).
								sorted(Comparator.comparing(Transaction::getDateTransaction).reversed()).
								collect(Collectors.toList());				
					}else if (Boolean.TRUE.equals(p.getFlagSavings())) {
						transactions=p.getAccountSavings().getTransactions().stream()
								.limit(10)
								.sorted(Comparator.comparing(Transaction::getDateTransaction).reversed())
								.collect(Collectors.toList());				
					}
					return transactions;
				}).flatMapIterable(list -> list)));
	}

}
