package com.nttdata.bootcamp.passive.service.impl;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nttdata.bootcamp.common.dto.AccountPassiveDto;
import com.nttdata.bootcamp.common.event.AccountEvent;
import com.nttdata.bootcamp.common.event.AccountStatus;
import com.nttdata.bootcamp.common.event.PersonEvent;
import com.nttdata.bootcamp.passive.dao.PassiveDao;
import com.nttdata.bootcamp.passive.model.Passive;
import com.nttdata.bootcamp.passive.service.PassiveService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implement Passive.
 *
 */
@Service
public class PassiverServiceImpl implements PassiveService {

  
  @Autowired
  PassiveDao passiveDao;
  
  @Autowired
  PassiveStatusPublisher publisher;

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Mono<Passive> save(Passive passive) {
	  
    return  passiveDao.save(passive);
  }

  @Override
  public Mono<Passive> update(Passive passive) {
    
    return passiveDao.save(passive);
  }

  @Override
  public Flux<Passive> findAll() {
    
    return passiveDao.findAll();
  }

  @Override
  public Mono<Passive> findById(String id) {
    
    return passiveDao.findById(id);
  }

	@Override
	@Transactional
	public AccountEvent newAccountEvent(Passive personEvent) {
		
		ModelMapper mapper = new ModelMapper();
		AccountPassiveDto passiveDto = mapper.map(personEvent, AccountPassiveDto.class);
		AccountEvent event ;
		if(passiveDto.getId() != null) {
			event = new AccountEvent(passiveDto, AccountStatus.CREATED);
			publisher.publishPersonEvent(passiveDto, AccountStatus.CREATED);
		
		}else {
			event = new AccountEvent(passiveDto, AccountStatus.FAILED);
			publisher.publishPersonEvent(passiveDto, AccountStatus.FAILED);
		}				
		return event;
	}

	@Override
	@Transactional
		public void cancelAccountEvent(PersonEvent personEvent) {
		if(Objects.nonNull(personEvent.getPersonDto()) ) {
			Passive passive = new Passive();
			passive.getPerson().setId(personEvent.getPersonDto().getId());
			Example<Passive> ePassive = Example.of(passive);
			passiveDao.findAll(ePassive).map(p ->{
				passiveDao.delete(p);
				return p;
			}).defaultIfEmpty(passive);
		}		 
		
	}

}
