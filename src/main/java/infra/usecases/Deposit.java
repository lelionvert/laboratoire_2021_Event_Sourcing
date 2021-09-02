package infra.usecases;

import domain.Account;
import domain.Amount;
import domain.TransactionHistory;

public class Deposit {

  private final TransactionHistory transactionHistory;
  private final Amount amount;

  public Deposit(TransactionHistory transactionHistory, Amount amount) {
    this.transactionHistory = transactionHistory;
    this.amount = amount;
  }

  public void execute() {
    new Account(transactionHistory).deposit(amount);
  }
}
