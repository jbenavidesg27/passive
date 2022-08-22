package com.nttdata.bootcamp.passive.kafka.consumer;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nttdata.bootcamp.common.event.AccountEvent;
import com.nttdata.bootcamp.common.event.PersonEvent;
import com.nttdata.bootcamp.common.event.PersonStatus;
import com.nttdata.bootcamp.passive.adapte.AdapterPassive;
import com.nttdata.bootcamp.passive.model.Passive;
import com.nttdata.bootcamp.passive.service.PassiveService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Configuration
public class PersonConsumer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonConsumer.class);
	
	
	@Autowired
	PassiveService passiveService;
	
	@Autowired
	AdapterPassive adapPassive;
	
	
	
	@Bean
    public Function<Flux<PersonEvent>, Flux<AccountEvent>> personSupplier() {
        return personEventFlux -> personEventFlux.flatMap(this::processAccount);
    }
	
	
	private Mono<AccountEvent> processAccount(PersonEvent personEvent) {
        // get the user id
        // check the balance availability
        // if balance sufficient -> Payment completed and deduct amount from DB
        // if payment not sufficient -> cancel order event and update the amount in DB
        if(PersonStatus.CREATED.equals(personEvent.getPersonStatus())){
        	Passive passive  =adapPassive.adaptePassive(personEvent);       	
        	passiveService.save(passive).subscribe(o -> LOGGER.info("transaction {}",o));        	
            return  Mono.fromSupplier(()->this.passiveService.newAccountEvent(passive));
        }else{
            return Mono.fromRunnable(()->this.passiveService.cancelAccountEvent(personEvent));
        }
    }

	
}
	
	
	
	
	


