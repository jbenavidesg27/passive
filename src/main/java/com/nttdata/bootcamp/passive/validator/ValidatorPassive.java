package com.nttdata.bootcamp.passive.validator;

import com.nttdata.bootcamp.passive.model.AccountCurrent;
import com.nttdata.bootcamp.passive.model.AccountSavings;
import com.nttdata.bootcamp.passive.model.Passive;
import com.nttdata.bootcamp.passive.model.Transaction;
import com.nttdata.bootcamp.passive.util.Constantes;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Validar Passive.
 *
 */
public class ValidatorPassive {
  
  /**
   * Validación de Passive.
   * 
   */
  public static Passive validatePassive(Passive bd, Passive ps) {
    BigDecimal monto;
    List<Transaction> transactions = new ArrayList<>();
    if (Objects.nonNull(ps.getAccountCurrent())) {
      if (bd.getClient().getDocuments().getDocumentType().equals(Constantes.TYPE_DNI)) {
        if (Boolean.TRUE.equals(bd.getFlagCurrent()) && Objects.nonNull(ps.getAccountCurrent())) {
          AccountCurrent current = new AccountCurrent();
          current = bd.getAccountCurrent().get(0);
          if (ps.getAccountCurrent().get(0).getTransactions().get(0).getTypeTransaction()
              .equals(Constantes.DEPOSITO)) {
            monto = current.getAccount().add(ps.getAccountCurrent().get(0).getAccount());
            current.setAccount(monto);
          } else {
            monto = current.getAccount().subtract(ps.getAccountCurrent().get(0).getAccount());
            current.setAccount(monto);
          }
          current.getTransactions().stream().forEach(p -> {
            transactions.add(p);
          });
          transactions.add(ps.getAccountCurrent().get(0).getTransactions().get(0));
          List<AccountCurrent> currents = new ArrayList<>();
          currents.add(current);
          bd.setAccountCurrent(currents);
          bd.setClient(bd.getClient());
        } else {
          bd.setAccountCurrent(ps.getAccountCurrent());
          bd.setClient(bd.getClient());
          bd.setFlagCurrent(ps.getFlagCurrent());
        }
      } else {
        bd.setAccountCurrent(ps.getAccountCurrent());
        bd.setClient(bd.getClient());
      }
    }
    if (Objects.nonNull(ps.getAccountSavings())) {
      if (bd.getClient().getDocuments().getDocumentType().equals(Constantes.TYPE_DNI)) {
        if (Boolean.TRUE.equals(bd.getFlagSavings()) && Objects.nonNull(ps.getAccountSavings())) {
          AccountSavings savings = new AccountSavings();
          savings = bd.getAccountSavings();
          if (ps.getAccountSavings().getTransactions().get(0).getTypeTransaction()
              .equals(Constantes.DEPOSITO)) {
            monto = savings.getAccount().add(ps.getAccountSavings().getAccount());
            savings.setAccount(monto);
          } else {
            monto = savings.getAccount().subtract(ps.getAccountSavings().getAccount());
            savings.setAccount(monto);
          }
          savings.getTransactions().stream().forEach(p -> {
            transactions.add(p);
          });
          transactions.add(ps.getAccountSavings().getTransactions().get(0));
          savings.setTransactions(transactions);
          bd.setAccountSavings(savings);
          bd.setClient(bd.getClient());
        } else {
          bd.setAccountSavings(ps.getAccountSavings());
          bd.setClient(bd.getClient());
          bd.setFlagSavings(ps.getFlagSavings());
        }
      }
    }
    return bd;
  }
}