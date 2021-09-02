package infra;

import com.mongodb.Function;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import domain.Amount;
import domain.events.DepositEvent;
import domain.events.TransactionEvent;
import domain.events.WithdrawEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

public class EventStore {

  private final MongoCollection<Document> transactions;

  public EventStore(MongoDatabase mongoDatabase) {
    transactions = mongoDatabase.getCollection("transactions");

  }

  public void save(TransactionEvent event) {
      Document document = new Document();

      if(event instanceof  DepositEvent) {
        document.append("type", "deposit");
        document.append("amount", event.amount().value().doubleValue());
      } else {
        document.append("type", "withdraw");
        document.append("amount", event.amount().value().negate().doubleValue());
      }
      transactions.insertOne(document);
  }

  public List<TransactionEvent> find() {
    return transactions.find()
        .map(this::map)
        .into(new ArrayList<>());
  }

  private TransactionEvent map(Document document) {
    if (document.getString("type").equals("deposit")) {
      return new DepositEvent(new Amount(BigDecimal.valueOf(document.getDouble("amount"))));
    }
    return new WithdrawEvent(new Amount(BigDecimal.valueOf(document.getDouble("amount"))));
  }
}
