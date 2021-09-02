package infra.usecases;

import domain.Account;
import domain.Amount;
import domain.TransactionHistory;

public class GetBalance {

  private final TransactionHistory transactionHistory;

  public GetBalance(TransactionHistory transactionHistory) {
    this.transactionHistory = transactionHistory;
  }

  public Amount execute() {
    return new Account(transactionHistory).balance();
  }
}
