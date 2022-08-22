package com.nttdata.bootcamp.passive.kafka.producer;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nttdata.bootcamp.common.event.AccountEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class PassiveProducer {
	@Bean
    public Sinks.Many<AccountEvent> personSinks(){
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<AccountEvent>> accountPassiveConsumer(Sinks.Many<AccountEvent> sinks){
       return sinks::asFlux;
    }

}
