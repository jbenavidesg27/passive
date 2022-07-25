package com.nttdata.bootcamp.passive.service;

import com.nttdata.bootcamp.passive.model.Passive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPassiveService {
	Mono<Passive> save(Passive passive);
	Mono<Passive> update(Passive passive);
	Flux<Passive> findAll();
	Mono<Passive> findById(String id);

}
