package infra.assembly;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import domain.TransactionHistory;
import infra.EventStore;
import infra.MongoTransactionHistory;
import infra.web.AccountResource;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;

public class Application {

  private final AccountResource accountResource;

  public Application() {
    MongoDatabase mongoDatabase = new MongoClient("http://localhost:1234").getDatabase("account");
    TransactionHistory transactionHistory = new MongoTransactionHistory(new EventStore(mongoDatabase));
    accountResource = new AccountResource(transactionHistory);
    Javalin app = Javalin.create(JavalinConfig::enableCorsForAllOrigins).start(8080);
    endpoint(app);
  }

  private void endpoint(Javalin app) {
    app
        .post("/account/deposit", accountResource::postDeposit)
        .post("/account/withdraw", accountResource::postWithdraw)
        .get("/account/balance", accountResource::getBalance);
  }
}
