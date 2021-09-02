package domain;

import domain.events.DepositEvent;
import domain.events.TransactionEvent;
import domain.events.WithdrawEvent;
import java.math.BigDecimal;

public class Account {

  private final TransactionHistory transactionHistory;

  public Account(TransactionHistory transactionHistory) {
    this.transactionHistory = transactionHistory;
  }

  public Amount balance() {

    return transactionHistory.all().stream()
        .map(TransactionEvent::amount)
        .reduce(new Amount(BigDecimal.ZERO),Amount::add);
  }

  public void deposit(Amount amount) {
    transactionHistory.publish(new DepositEvent(amount));
  }

  public void withdraw(Amount amount) {
    transactionHistory.publish(new WithdrawEvent(amount));
  }
}
