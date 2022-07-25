package com.nttdata.bootcamp.passive.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bootcamp.passive.model.Passive;

public interface IPassiveDao extends ReactiveMongoRepository<Passive, String>{

}
