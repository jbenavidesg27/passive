package com.nttdata.bootcamp.passive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.passive.dao.IPassiveDao;
import com.nttdata.bootcamp.passive.model.Passive;
import com.nttdata.bootcamp.passive.service.IPassiveService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PassiverServiceImpl implements IPassiveService{
	
	@Autowired
	IPassiveDao passiveDao;

	@Override
	public Mono<Passive> save(Passive passive) {
		
		return passiveDao.save(passive);
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

}
