package infra.usecases;

import domain.Account;
import domain.Amount;
import domain.TransactionHistory;

public class Withdraw {

  private final TransactionHistory transactionHistory;
  private final Amount amount;

  public Withdraw(TransactionHistory transactionHistory, Amount amount) {
    this.transactionHistory = transactionHistory;
    this.amount = amount;
  }

  public void execute() {
    new Account(transactionHistory).withdraw(amount);
  }
}
