package com.nttdata.bootcamp.passive.dao;

import com.nttdata.bootcamp.passive.model.Passive;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * DAO Passive.
 *
 */
public interface PassiveDao extends ReactiveMongoRepository<Passive, String> {

}
