import static java.util.stream.Collectors.toUnmodifiableList;

import java.util.ArrayList;
import java.util.List;

public class FakeEventStream implements EventStream {

  private final List<TransactionEvent> events = new ArrayList<>();

  @Override
  public List<TransactionEvent> all() {
    return events.stream()
        .collect(toUnmodifiableList());
  }

  @Override
  public void publish(TransactionEvent event) {
    events.add(event);
  }
}
