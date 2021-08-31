import java.util.List;

public interface EventStream {

  List<TransactionEvent> all();

  void publish(TransactionEvent event);
}
