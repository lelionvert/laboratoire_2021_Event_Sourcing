package infra.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;

import domain.Amount;
import domain.TransactionHistory;
import domain.events.DepositEvent;
import domain.events.WithdrawEvent;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WithdrawTest {

  @Mock
  private TransactionHistory transactionHistory;

  @Test
  void execute_should_withdraw_amount_from_account() {
    Amount amount = new Amount(BigDecimal.TEN);
    Withdraw withdraw = new Withdraw(transactionHistory, amount);

    withdraw.execute();

    then(transactionHistory).should().publish(new WithdrawEvent(amount));
  }
}