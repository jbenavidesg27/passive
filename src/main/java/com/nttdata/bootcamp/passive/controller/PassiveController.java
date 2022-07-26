package com.nttdata.bootcamp.passive.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.passive.model.AccountCurrent;
import com.nttdata.bootcamp.passive.model.AccountSavings;
import com.nttdata.bootcamp.passive.model.Passive;
import com.nttdata.bootcamp.passive.model.Transaction;
import com.nttdata.bootcamp.passive.service.IPassiveService;
import com.nttdata.bootcamp.passive.util.Constantes;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/passive")
public class PassiveController {
	@Autowired
	IPassiveService passiveService;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<Passive>>>  findAll(){
		Flux<Passive> fx = passiveService.findAll();
		return Mono.just(ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(fx));
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Passive>> findById(@PathVariable("id") String id){
		return passiveService.findById(id)
				.map(p -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(p));
	}
	
	@PostMapping
	public Mono<ResponseEntity<Passive>> save(@RequestBody Passive passive, final ServerHttpRequest req){
		return passiveService.save(passive)
				.map( p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(p));
	}
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<Passive>> update(@PathVariable("id") String id,@RequestBody Passive passive){
		Mono<Passive> monoBody = Mono.just(passive);
		Mono<Passive> monoBD = passiveService.findById(id);
		return monoBD.zipWith(monoBody, (bd, ps) -> {
					BigDecimal monto;	
					List<Transaction> transactions =  new ArrayList<>();
			if (Objects.nonNull(bd.getAccountCurrent())) {
				if (Boolean.TRUE.equals(bd.getFlagCurrent()) && Objects.nonNull(ps.getAccountCurrent())) {
					AccountCurrent current = new AccountCurrent();
					current = bd.getAccountCurrent();
					if (ps.getAccountCurrent().getTransactions().get(0).getTypeTransaction()
							.equals(Constantes.DEPOSITO)) {
						monto = current.getAccount().add(ps.getAccountCurrent().getAccount());
						current.setAccount(monto);
					} else {
						monto = current.getAccount().subtract(ps.getAccountCurrent().getAccount());
						current.setAccount(monto);
					}
					current.getTransactions().stream().forEach(p -> {
						transactions.add(p);
					});
					transactions.add(ps.getAccountCurrent().getTransactions().get(0));
					bd.setAccountCurrent(current);
				}
			}
			if (Objects.nonNull(bd.getAccountSavings())) {
				if (Boolean.TRUE.equals(bd.getFlagSavings()) && Objects.nonNull(ps.getAccountSavings())) {
					AccountSavings savings = new AccountSavings();
					savings = bd.getAccountSavings();
					if (ps.getAccountSavings().getTransactions().get(0).getTypeTransaction()
							.equals(Constantes.DEPOSITO)) {
						monto = savings.getAccount().add(ps.getAccountSavings().getAccount());
						savings.setAccount(monto);
					} else {
						monto = savings.getAccount().subtract(ps.getAccountSavings().getAccount());
						savings.setAccount(monto);
					}
					savings.getTransactions().stream().forEach(p -> {
						transactions.add(p);
					});
					transactions.add(ps.getAccountSavings().getTransactions().get(0));
					savings.setTransactions(transactions);
					bd.setAccountSavings(savings);
				} else {
					new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
			}
					bd.setId(id);
					return bd;
				})
				.flatMap(passiveService::update)
				.map(y -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON).
						body(y))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}


}
