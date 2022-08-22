package com.nttdata.bootcamp.passive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.common.dto.AccountPassiveDto;
import com.nttdata.bootcamp.common.event.AccountEvent;
import com.nttdata.bootcamp.common.event.AccountStatus;

import reactor.core.publisher.Sinks;

@Service
public class PassiveStatusPublisher {
	@Autowired
	 private Sinks.Many<AccountEvent> personSinks;
	 
	 public void publishPersonEvent(AccountPassiveDto accountPassiveDto, AccountStatus accountStatus){
		 AccountEvent accountEvent=new AccountEvent(accountPassiveDto,accountStatus);
		 personSinks.tryEmitNext(accountEvent);
	    }

}
