package infra;

import static org.assertj.core.api.Assertions.assertThat;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import domain.Amount;
import domain.events.DepositEvent;
import domain.events.TransactionEvent;
import domain.events.WithdrawEvent;
import java.math.BigDecimal;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventStoreTest {

  private MongoServer server;
  private MongoDatabase mongoDatabase;
  private MongoCollection<Document> transactions;

  @BeforeEach
  void setUp() {

    // Fake in-memory MongoDB server
    server = new MongoServer(new MemoryBackend());

    mongoDatabase = new MongoClient(new ServerAddress(server.bind()))
        .getDatabase("account");

    transactions = mongoDatabase.getCollection("transactions");

  }

  @AfterEach
  void tearDown() {
    server.shutdownNow();
  }

  @Test
  void should_save_deposit_event() {
    EventStore eventStore = new EventStore(mongoDatabase);

    DepositEvent depositEvent = new DepositEvent(new Amount(BigDecimal.TEN));
    eventStore.save(depositEvent);

    Document actual = transactions.find().map(document -> document).first();

    assertThat(actual.getString("type")).isEqualTo("deposit");
    assertThat(actual.getDouble("amount")).isEqualTo(10.0);
  }

  @Test
  void should_save_withdraw_event() {
    EventStore eventStore = new EventStore(mongoDatabase);

    WithdrawEvent withdrawEvent = new WithdrawEvent(new Amount(BigDecimal.TEN));
    eventStore.save(withdrawEvent);

    Document actual = transactions.find().map(document -> document).first();

    assertThat(actual.getString("type")).isEqualTo("withdraw");
    assertThat(actual.getDouble("amount")).isEqualTo(10.0);
  }

  @Test
  void find_shoul_return_list_of_transaction_event() {
    Document depositDocument = new Document();
    depositDocument.append("type", "deposit");
    depositDocument.append("amount", 10.0);

    Document withdrawDocument = new Document();
    withdrawDocument.append("type", "withdraw");
    withdrawDocument.append("amount", 10.0);

    transactions.insertMany(List.of(depositDocument, withdrawDocument));

    EventStore eventStore = new EventStore(mongoDatabase);

    List<TransactionEvent> events = eventStore.find();

    assertThat(events).containsOnly(new DepositEvent(new Amount(BigDecimal.TEN.setScale(1))), new WithdrawEvent(new Amount(BigDecimal.TEN.setScale(1))));
  }
}