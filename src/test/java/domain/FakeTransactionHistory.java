package domain;

import domain.events.TransactionEvent;
import java.util.ArrayList;
import java.util.List;

public class FakeTransactionHistory implements TransactionHistory {

  private final List<TransactionEvent> events = new ArrayList<>();

  @Override
  public List<TransactionEvent> all() {
    return events.stream().toList();
  }

  @Override
  public void publish(TransactionEvent event) {
    events.add(event);
  }
}
