package com.nttdata.bootcamp.passive.service;

import com.nttdata.bootcamp.common.event.AccountEvent;
import com.nttdata.bootcamp.common.event.PersonEvent;
import com.nttdata.bootcamp.passive.model.Passive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Passive Service.
 *
 */
public interface PassiveService {
  Mono<Passive> save(Passive passive);
  
  Mono<Passive> update(Passive passive);
  
  Flux<Passive> findAll();
  
  Mono<Passive> findById(String id);
  
  AccountEvent newAccountEvent(Passive personEvent);

  void cancelAccountEvent(PersonEvent personEvent);

}