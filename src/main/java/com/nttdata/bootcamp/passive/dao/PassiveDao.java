package com.nttdata.bootcamp.passive.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bootcamp.passive.model.Passive;

/**
 * DAO Passive.
 *
 */
public interface PassiveDao extends ReactiveMongoRepository<Passive, String> {

}
