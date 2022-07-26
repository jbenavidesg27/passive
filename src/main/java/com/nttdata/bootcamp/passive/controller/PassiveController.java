package com.nttdata.bootcamp.passive.controller;

import java.net.URI;
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

import com.nttdata.bootcamp.passive.model.Passive;
import com.nttdata.bootcamp.passive.service.IPassiveService;
import com.nttdata.bootcamp.passive.util.Constantes;
import com.nttdata.bootcamp.passive.validator.ValidatorPassive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/passive")
public class PassiveController {
	@Autowired
	IPassiveService passiveService;
	
//	@Autowired
//	ValidatorPassive validatorPassive;
	
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
		return Mono.just(passive)
				.flatMap(p -> {
					
					if(p.getClient().getDocuments().getDocumentType().equals(Constantes.TYPE_RUC)) {
						if(Objects.nonNull(p.getAccountCurrent())) {
							p.setAccountCurrent(p.getAccountCurrent());
						}									
					}else if (p.getClient().getDocuments().getDocumentType().equals(Constantes.TYPE_DNI)) {
						if(Objects.nonNull(p.getAccountCurrent())) {
							p.setAccountCurrent(p.getAccountCurrent());
						}else if (Objects.nonNull(p.getAccountSavings())) {
							p.setAccountSavings(p.getAccountSavings());								
						}else {
							p.setFixedTerm(p.getFixedTerm());
						}
						
					}			
					return Mono.just(p);					
					})
				.flatMap(passiveService::save)				
				.map( p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(p));
	}
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<Passive>> update(@PathVariable("id") String id,@RequestBody Passive passive){
		Mono<Passive> monoBody = Mono.just(passive);
		Mono<Passive> monoBD = passiveService.findById(id);
		return monoBD.zipWith(monoBody, (bd, ps) -> {
			bd = ValidatorPassive.validatePassive(bd, ps);
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
