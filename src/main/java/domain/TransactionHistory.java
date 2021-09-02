package domain;

import domain.events.TransactionEvent;
import java.util.List;

public interface TransactionHistory {

  List<TransactionEvent> all();

  void publish(TransactionEvent event);
}
