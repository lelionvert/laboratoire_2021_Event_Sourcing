import java.math.BigDecimal;

public class Account {

  private final EventStream eventStream;

  public Account(EventStream eventStream) {
    this.eventStream = eventStream;
  }

  public Amount balance() {
    return eventStream.all().stream()
        .map(TransactionEvent::amount)
        .reduce(new Amount(BigDecimal.ZERO),Amount::add);
  }

  public void deposit(Amount amount) {
    eventStream.publish(new DepositEvent(amount));
  }

  public void withdraw(Amount amount) {

    eventStream.publish(new WithdrawEvent(amount));

  }
}
