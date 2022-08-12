package com.nttdata.bootcamp.passive.validator;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.nttdata.bootcamp.passive.model.Passive;
import com.nttdata.bootcamp.passive.model.Transaction;
import com.nttdata.bootcamp.passive.util.Constantes;



/**
 * Validar Passive.
 *
 */

public class ValidatorPassive {
	
	
	
	private ValidatorPassive() {
		
	}
	 /**
	   * Validación de Passive.
	   * 
	 */
	public static Passive validatePassive(Passive bd, Passive ps) {
		
		List<Transaction> transactions = new ArrayList<>();
		if (Objects.nonNull(ps.getAccountCurrent())) {
			if (bd.getClient().getDocuments().getDocumentType().equals(Constantes.TYPE_DNI)
					&& Boolean.TRUE.equals(bd.getFlagCurrent())) {
				Passive bdAccountCurrent = validateMontoAccountCurrent(bd,ps);
				bdAccountCurrent.getAccountCurrent().get(0).getTransactions().stream().forEach(p -> transactions.add(p));
				transactions.add(ps.getAccountCurrent().get(0).getTransactions().get(0));
				bdAccountCurrent.getAccountCurrent().get(0).setTransactions(transactions);
				bd.setAccountCurrent(bdAccountCurrent.getAccountCurrent());
				bd.setClient(bd.getClient());
			} else {
				bd.setAccountCurrent(ps.getAccountCurrent());
				bd.setClient(bd.getClient());
				bd.setFlagCurrent(ps.getFlagCurrent());
			}
		} else {
			if (bd.getClient().getDocuments().getDocumentType().equals(Constantes.TYPE_DNI)
					&& Boolean.TRUE.equals(bd.getFlagSavings())) {
				Passive bdAccountSavings = validateMontoAccountSavings(bd,ps);				
				bdAccountSavings.getAccountSavings().getTransactions().stream().forEach(p -> transactions.add(p));
				transactions.add(ps.getAccountSavings().getTransactions().get(0));
				bdAccountSavings.getAccountSavings().setTransactions(transactions);
				bd.setAccountSavings(bdAccountSavings.getAccountSavings());
				bd.setClient(bd.getClient());
			} else {
				bd.setAccountSavings(ps.getAccountSavings());
				bd.setClient(bd.getClient());
				bd.setFlagSavings(ps.getFlagSavings());
			}
		}				
		return bd;
	}
	 /**
	   * Validación de Monot AccountCurrent.
	   * 
	 */
	public static Passive validateMontoAccountCurrent(Passive bd,Passive ps) {
		BigDecimal montoAccountCurrent;
		if (ps.getAccountCurrent().get(0).getTransactions().get(0).getTypeTransaction()
				.equals(Constantes.DEPOSITO)) {
			montoAccountCurrent = bd.getAccountCurrent().get(0).getAccount().add(ps.getAccountCurrent().get(0).getAccount());// Noncompliant
			bd.getAccountCurrent().get(0).setAccount(montoAccountCurrent);
		} else {
			if ((bd.getAccountCurrent().get(0).getAccount().compareTo(ps.getAccountCurrent().get(0).getAccount()) == 0)
					|| (bd.getAccountCurrent().get(0).getAccount()
							.compareTo(ps.getAccountCurrent().get(0).getAccount()) == 1)) {
				montoAccountCurrent = bd.getAccountCurrent().get(0).getAccount()
						.subtract(ps.getAccountCurrent().get(0).getAccount());
				bd.getAccountCurrent().get(0).setAccount(montoAccountCurrent);
			}else {
				 new Exception("Se excedió a la cantidad permitidad") ;
			}
		}		
		return bd;	
	}
	
	/**
	   * Validación de Monot AccountSavings.
	   * 
	 */
	public static Passive validateMontoAccountSavings(Passive bd,Passive ps) {
		BigDecimal montoAccountSaving;
		if (ps.getAccountSavings().getTransactions().get(0).getTypeTransaction().equals(Constantes.DEPOSITO)) {
			montoAccountSaving = bd.getAccountSavings().getAccount().add(ps.getAccountSavings().getAccount());
			bd.getAccountSavings().setAccount(montoAccountSaving);
		} else {
			if ((bd.getAccountSavings().getAccount().compareTo(ps.getAccountSavings().getAccount()) == 0)
					|| (bd.getAccountSavings().getAccount().compareTo(ps.getAccountSavings().getAccount()) == 1)) {
				montoAccountSaving = bd.getAccountSavings().getAccount().subtract(ps.getAccountSavings().getAccount());
				bd.getAccountSavings().setAccount(montoAccountSaving);
			}
		}
		return bd;
	}

}
