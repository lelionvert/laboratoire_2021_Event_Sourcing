package infra.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import domain.Account;
import domain.Amount;
import domain.TransactionHistory;
import domain.events.DepositEvent;
import domain.events.WithdrawEvent;
import java.math.BigDecimal;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetBalanceTest {

  @Mock
  private TransactionHistory transactionHistory;

  @Test
  void execute_should_get_balance() {
    given(transactionHistory.all()).willReturn(List.of(new DepositEvent(new Amount(BigDecimal.TEN)),
                                                 new WithdrawEvent(new Amount(BigDecimal.TEN))
    ));

    assertThat(new GetBalance(transactionHistory).execute()).isEqualTo(new Amount(BigDecimal.ZERO));
  }
}