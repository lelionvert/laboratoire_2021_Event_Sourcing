package infra;

import domain.TransactionHistory;
import domain.events.TransactionEvent;
import java.util.List;

public class MongoTransactionHistory implements TransactionHistory {

  private final EventStore eventStore;

  public MongoTransactionHistory(EventStore eventStore) {
    this.eventStore = eventStore;
  }

  @Override
  public List<TransactionEvent> all() {
    return eventStore.find();
  }

  @Override
  public void publish(TransactionEvent event) {
      eventStore.save(event);
  }
}
