package com.nttdata.bootcamp.passive.controller;

import java.net.URI;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.bootcamp.passive.kafka.consumer.PersonConsumer;
import com.nttdata.bootcamp.passive.kafka.producer.PassiveProducer;
import com.nttdata.bootcamp.passive.model.Passive;
import com.nttdata.bootcamp.passive.model.dto.PassiveDTO;
import com.nttdata.bootcamp.passive.service.PassiveService;
import com.nttdata.bootcamp.passive.util.Constantes;
import com.nttdata.bootcamp.passive.validator.ValidatorPassive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Rest.
 *
 */
@RestController
@RequestMapping("/passive")
public class PassiveController {

<<<<<<< HEAD
  @Autowired
  PassiveService passiveService;
  
=======
	@Autowired
	PassiveService passiveService;
	
	@Autowired
	PassiveProducer passiveProducer;
	
	@Autowired
	PersonConsumer personConsumer;
	
>>>>>>> 0d1b6fae522710a8a6bfb6f6341a9da446617744
/**
 * Peticiones Rest.
 * List all Passive.
 */
<<<<<<< HEAD
  @GetMapping
  public Mono<ResponseEntity<Flux<Passive>>>  findAll(){
    Flux<Passive> fx = passiveService.findAll();
    return Mono.just(ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(fx));
  }
  /**
     * List Passive for Id.
     * 
     */
  @GetMapping("/{id}")
  public Mono<ResponseEntity<Passive>> findById(@PathVariable("id") String id){
    return passiveService.findById(id)
        .map(p -> ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(p));
  }
  /**
  * Save Passive.
   * 
   */
  @PostMapping
  public Mono<ResponseEntity<Passive>> save(@RequestBody PassiveDTO passive,
      final ServerHttpRequest req){
    ModelMapper mapper = new ModelMapper();
    Passive passiveModel = mapper.map(passive, Passive.class);
    return Mono.just(passiveModel)
        .flatMap(p -> {
          
          if(p.getClient().getDocuments().getDocumentType().equals(Constantes.TYPE_RUC)) {
            if(Objects.nonNull(p.getAccountCurrent())) {
              p.setAccountCurrent(p.getAccountCurrent());
            }                  
          }else if (p.getClient().getDocuments().getDocumentType().equals(Constantes.TYPE_DNI)) {
            if(Objects.nonNull(p.getAccountCurrent())) {
              p.setAccountCurrent(p.getAccountCurrent());
            }else if (Objects.nonNull(p.getAccountSavings())) {
              p.setAccountSavings(p.getAccountSavings());                
            }else {
              p.setFixedTerm(p.getFixedTerm());
            }
            
          }      
          return Mono.just(p);          
          })
        .flatMap(passiveService::save)        
        .map( p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
            .contentType(MediaType.APPLICATION_JSON)
            .body(p));
  }
  /**
     * Update Passive for Id.
     * 
     */
  @PutMapping("/{id}")
  public Mono<ResponseEntity<Passive>> update(@PathVariable("id") String id,@RequestBody PassiveDTO passive){
    ModelMapper mapper = new ModelMapper();
    Passive passiveModel = mapper.map(passive, Passive.class);
    Mono<Passive> monoBody = Mono.just(passiveModel);
    Mono<Passive> monoBD = passiveService.findById(id);
    return monoBD.zipWith(monoBody, (bd, ps) -> {
      bd = ValidatorPassive.validatePassive(bd, ps);
      bd.setId(id);
      return bd;
    })
        .flatMap(passiveService::update)
        .map(y -> ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON).
            body(y))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
=======
	@GetMapping
	public Mono<ResponseEntity<Flux<Passive>>>  findAll(){
		Flux<Passive> fx = passiveService.findAll();
		return Mono.just(ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(fx));
	}
	/**
	   * List Passive for Id.
	   * 
	   */
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Passive>> findById(@PathVariable("id") String id){
		return passiveService.findById(id)	
				.flatMap(j -> {					
					try {
						ObjectMapper mapper = new ObjectMapper();
						// Java objects to JSON string - compact-print
				        String jsonString = mapper.writeValueAsString(j);
						passiveProducer.sendMessage(jsonString);
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return Mono.just(j);
				})
				.map(p -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(p))
				;
	}
	/**
	   * Save Passive.
	   * 
	   */
	@PostMapping
	public Mono<ResponseEntity<Passive>> save(@RequestBody PassiveDTO passive, final ServerHttpRequest req){
		ModelMapper mapper = new ModelMapper();
		Passive passiveModel = mapper.map(passive, Passive.class);
		return Mono.just(passiveModel)
				.flatMap(p -> {
					try {
						p.setClient(personConsumer.consume(null));
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(p.getClient().getDocuments().getDocumentType().equals(Constantes.TYPE_RUC)) {
						if(Objects.nonNull(p.getAccountCurrent())) {
							p.setAccountCurrent(p.getAccountCurrent());
						}									
					}else if (p.getClient().getDocuments().getDocumentType().equals(Constantes.TYPE_DNI)) {
						if(Objects.nonNull(p.getAccountCurrent())) {
							p.setAccountCurrent(p.getAccountCurrent());
						}else if (Objects.nonNull(p.getAccountSavings())) {
							p.setAccountSavings(p.getAccountSavings());								
						}else {
							p.setFixedTerm(p.getFixedTerm());
						}
						
					}			
					return Mono.just(p);					
					})
				.flatMap(passiveService::save)				
				.map( p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(p));
	}
	/**
	   * Update Passive for Id.
	   * 
	   */
	@PutMapping("/{id}")
	public Mono<ResponseEntity<Passive>> update(@PathVariable("id") String id,@RequestBody PassiveDTO passive){
		ModelMapper mapper = new ModelMapper();
		Passive passiveModel = mapper.map(passive, Passive.class);
		Mono<Passive> monoBody = Mono.just(passiveModel);
		Mono<Passive> monoBD = passiveService.findById(id);
		return monoBD.zipWith(monoBody, (bd, ps) -> {
			bd = ValidatorPassive.validatePassive(bd, ps);
			bd.setId(id);
			return bd;
		})
				.flatMap(passiveService::update)
				.map(y -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON).
						body(y))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
>>>>>>> 0d1b6fae522710a8a6bfb6f6341a9da446617744


}
