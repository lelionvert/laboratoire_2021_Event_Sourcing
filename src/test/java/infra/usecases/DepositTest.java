package infra.usecases;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import domain.Amount;
import domain.TransactionHistory;
import domain.events.DepositEvent;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DepositTest {

  @Mock
  private TransactionHistory transactionHistory;

  @Test
  void execute_should_deposit_amount_on_account() {
    Amount amount = new Amount(BigDecimal.TEN);
    Deposit deposit = new Deposit(transactionHistory, amount);

    deposit.execute();

    then(transactionHistory).should().publish(new DepositEvent(amount));
  }
}