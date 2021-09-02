package infra;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import domain.events.DepositEvent;
import domain.events.TransactionEvent;
import org.bson.Document;

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
}
