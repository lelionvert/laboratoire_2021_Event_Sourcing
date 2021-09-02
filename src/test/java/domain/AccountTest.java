package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import domain.events.DepositEvent;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountTest {

  private static final Amount AMOUNT_TEN = new Amount(BigDecimal.TEN);
  @Mock
  private TransactionHistory transactionHistory;

  @Test
  void new_account_should_have_initial_balance_at_0() {
    given(transactionHistory.all()).willReturn(List.of());
    Account account = new Account(transactionHistory);

    Amount amount = account.balance();

    assertThat(amount).isEqualTo(new Amount(BigDecimal.ZERO));
  }

  @Test
  void deposit_should_add_amount_in_balance() {
    TransactionHistory fakeTransactionHistory = new FakeTransactionHistory();
    Account account = new Account(fakeTransactionHistory);

    account.deposit(AMOUNT_TEN);
    Amount amount = account.balance();

    assertThat(amount).isEqualTo(AMOUNT_TEN);
  }

  @Test
  void deposit_should_publish_deposit_event() {
    Account account = new Account(transactionHistory);

    account.deposit(AMOUNT_TEN);

    then(transactionHistory).should().publish(new DepositEvent(AMOUNT_TEN));
  }

  @Test
  void account_with_moula_should_have_one_deposit_of_moula() {
    given(transactionHistory.all()).willReturn(List.of(new DepositEvent(AMOUNT_TEN)));
    Account account = new Account(transactionHistory);

    Amount balance = account.balance();

    assertThat(balance).isEqualTo(AMOUNT_TEN);
  }

  @Test
  void balance_is_sum_of_deposits() {
    given(transactionHistory.all()).willReturn(List.of(new DepositEvent(AMOUNT_TEN), new DepositEvent(AMOUNT_TEN)));
    Account account = new Account(transactionHistory);
    Amount balance = account.balance();

    assertThat(balance).isEqualTo(new Amount(new BigDecimal(20)));
  }

  @Test
  void withdraw_should_subtract_amount_from_balance() {
    TransactionHistory fakeTransactionHistory = new FakeTransactionHistory();
    Account account = new Account(fakeTransactionHistory);
    account.withdraw(AMOUNT_TEN);
    Amount balance = account.balance();
    assertThat(balance).isEqualTo(new Amount(new BigDecimal(-10)));
  }

  @Test
  void multiple_withdraws_and_deposits_should_compute_balance(){
    FakeTransactionHistory fakeEventStream = new FakeTransactionHistory();
    Account account = new Account(fakeEventStream);
    account.deposit(AMOUNT_TEN);
    account.withdraw(AMOUNT_TEN);
    Amount balance = account.balance();
    assertThat(balance).isEqualTo(new Amount(BigDecimal.ZERO));
  }

}