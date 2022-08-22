package com.nttdata.bootcamp.passive.adapte;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nttdata.bootcamp.common.dto.PersonDto;
import com.nttdata.bootcamp.common.event.AccountStatus;
import com.nttdata.bootcamp.common.event.PersonEvent;
import com.nttdata.bootcamp.passive.model.AccountCurrent;
import com.nttdata.bootcamp.passive.model.AccountSavings;
import com.nttdata.bootcamp.passive.model.FixedTerm;
import com.nttdata.bootcamp.passive.model.Passive;
import com.nttdata.bootcamp.passive.model.Transaction;
import com.nttdata.bootcamp.passive.model.dto.Client;
import com.nttdata.bootcamp.passive.model.dto.Documents;
import com.nttdata.bootcamp.passive.model.dto.Persons;
import com.nttdata.bootcamp.passive.util.Constantes;

@Component
public class AdapterPassive {
	
	public Passive adaptePassive (PersonEvent personEvent) {
		PersonDto personDto = personEvent.getPersonDto();
		Passive passive = new Passive();
		List<Transaction> transactions = new ArrayList<>();
		if (personDto.getTypeAccount().equals(Constantes.TYPE_ACCOUNT_AHORRO)) {
			AccountSavings savings = new AccountSavings();
			savings.setAccount(new BigDecimal(personDto.getAccount()));
			savings.setDateTransaction(new Date());
			savings.setDescription(Constantes.CREATE_ACCOUNT_AHORRO);
			savings.setFlagPrincipal(Boolean.TRUE);
			savings.setMaxMovement(Constantes.MAX_MOVIMIENTO);
			savings.setMovement(Constantes.MIN_MOVIMIENTO);
			Transaction transaction = new Transaction();
			transaction.setAccount(new BigDecimal(personDto.getAccount()));
			transaction.setDateTransaction(new Date());
			transaction.setTypeTransaction(Constantes.DEPOSITO);
			transactions.add(transaction);
			savings.setTransactions(transactions);
			passive.setAccountSavings(savings);
			passive.setFlagSavings(Boolean.TRUE);

		} else if (personDto.getTypeAccount().equals(Constantes.TYPE_ACCOUNT_CORRIENTE)) {
			List<AccountCurrent> currents = new ArrayList<>();
			AccountCurrent current = new AccountCurrent();
			current.setAccount(new BigDecimal(personDto.getAccount()));
			current.setDateTransaction(new Date());
			current.setDescription(Constantes.CREATE_ACCOUNT_CORRIENTE);
			current.setFlagPrincipal(Boolean.TRUE);
			current.setMaintenance(Constantes.MANTENIMIENTO);
			Transaction transaction = new Transaction();
			transaction.setAccount(new BigDecimal(personDto.getAccount()));
			transaction.setDateTransaction(new Date());
			transaction.setTypeTransaction(Constantes.DEPOSITO);
			transactions.add(transaction);
			current.setTransactions(transactions);
			currents.add(current);
			passive.setAccountCurrent(currents);
			passive.setFlagCurrent(Boolean.TRUE);
		} else {
			FixedTerm term = new FixedTerm();
			term.setAccount(new BigDecimal(personDto.getAccount()));
			term.setDateTransaction(new Date());
			term.setDescription(Constantes.CREATE_ACCOUNT_CORRIENTE);
			term.setFlagPrincipal(Boolean.TRUE);
			term.setEndDate(null);
			passive.setFixedTerm(term);
		}
		Client client = new Client();
		Documents documents = new Documents();
		documents.setDocumentType(personDto.getDocuments().getDocumentType());
		documents.setNroDocument(personDto.getDocuments().getNroDocument());
		client.setDocuments(documents);
		Persons person = new Persons();
		person.setBusinessName(personDto.getClient().getBusinessName());
		person.setNames(personDto.getClient().getNames());
		person.setSurnames(personDto.getClient().getSurnames());
		client.setClient(person);		
		client.setId(personDto.getId());
		client.setTypePerson(personDto.getTypePerson());
		passive.setPerson(client);
		passive.setAccountStatus(AccountStatus.CREATED);
		passive.setPersonStatus(personEvent.getPersonStatus());
		return passive;
		
	}

}
