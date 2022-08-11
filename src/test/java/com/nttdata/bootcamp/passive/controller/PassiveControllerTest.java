package com.nttdata.bootcamp.passive.controller;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.nttdata.bootcamp.passive.dao.PassiveDao;
import com.nttdata.bootcamp.passive.model.Passive;
import com.nttdata.bootcamp.passive.model.dto.Client;
import com.nttdata.bootcamp.passive.model.dto.Documents;
import com.nttdata.bootcamp.passive.service.impl.PassiverServiceImpl;
import com.nttdata.bootcamp.passive.util.Constantes;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PassiveController.class)
@Import(PassiverServiceImpl.class)

public class PassiveControllerTest {
	
	@Autowired
	WebTestClient webTestClient;
	
	@MockBean
	private PassiveDao passiveDao;
	
	@Test
	void findAll() {
		Passive passive = new Passive();
		passive.setId("1");
        

        Passive passive2 = new Passive();
        passive2.setId("2");
       

        List<Passive> list = new ArrayList<>();
        list.add(passive);
        list.add(passive2);

        Mockito.when(passiveDao.findAll()).thenReturn(Flux.fromIterable(list));

    
		webTestClient.get()
					 .uri("/passive")
					 .accept(MediaType.APPLICATION_JSON)
					 .exchange()
					 .expectStatus().isOk()
					 .expectHeader().contentType(MediaType.APPLICATION_JSON)
					 .expectBodyList(Passive.class)
					 .hasSize(2);
	}
	
	  @Test
	    void createPassiveTest(){
		  Client client  = new Client();
		  Documents documents = new Documents();
		  documents.setDocumentType(Constantes.TYPE_DNI);
		  documents.setDocumentType("48018807");
		  client.setDocuments(documents);
		  Passive passive = new Passive();
		  passive.setId("1");
		  passive.setClient(client);
		  Mockito.when(passiveDao.save(any())).thenReturn(Mono.just(passive));
		  webTestClient.post()
	                .uri("/passive")
	                .body(Mono.just(passive), Passive.class)
	                .accept(MediaType.APPLICATION_JSON)
	                .exchange()
	                .expectStatus().isCreated()
	                .expectHeader().contentType(MediaType.APPLICATION_JSON)
	                .expectBody()
	                .jsonPath("$.id").isNotEmpty();
	    }
	  
	  @Test
	    void updatePassiveTest(){
		  Client client  = new Client();
		  Documents documents = new Documents();
		  documents.setDocumentType(Constantes.TYPE_DNI);
		  documents.setDocumentType("48018807");
		  client.setDocuments(documents);
		  Passive passive = new Passive();
		  passive.setId("1");
		  passive.setClient(client);
		  Mockito.when(passiveDao.findById("1")).thenReturn(Mono.just(passive));
		  Mockito.when(passiveDao.save(any())).thenReturn(Mono.just(passive));
		  webTestClient.put()
	                .uri("/passive/"+passive.getId())
	                .body(Mono.just(passive), Passive.class)
	                .accept(MediaType.APPLICATION_JSON)
	                .exchange()
	                .expectStatus().isOk()
	                .expectHeader().contentType(MediaType.APPLICATION_JSON)
	                .expectBody()
	                .jsonPath("$.id").isNotEmpty();
	    }

}
