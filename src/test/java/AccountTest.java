import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

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
  private EventStream eventStream;

  @Test
  void new_account_should_have_initial_balance_at_0() {
    given(eventStream.all()).willReturn(List.of());
    Account account = new Account(eventStream);

    Amount amount = account.balance();

    assertThat(amount).isEqualTo(new Amount(BigDecimal.ZERO));
  }

  @Test
  void deposit_should_add_amount_in_balance() {
    EventStream fakeEventStream = new FakeEventStream();
    Account account = new Account(fakeEventStream);

    account.deposit(AMOUNT_TEN);
    Amount amount = account.balance();

    assertThat(amount).isEqualTo(AMOUNT_TEN);
  }

  @Test
  void deposit_should_publish_deposit_event() {
    Account account = new Account(eventStream);

    account.deposit(AMOUNT_TEN);

    then(eventStream).should().publish(new DepositEvent(AMOUNT_TEN));
  }

  @Test
  void account_with_moula_should_have_one_deposit_of_moula() {
    given(eventStream.all()).willReturn(List.of(new DepositEvent(AMOUNT_TEN)));
    Account account = new Account(eventStream);

    Amount balance = account.balance();

    assertThat(balance).isEqualTo(AMOUNT_TEN);
  }

  @Test
  void balance_is_sum_of_deposits() {
    given(eventStream.all()).willReturn(List.of(new DepositEvent(AMOUNT_TEN), new DepositEvent(AMOUNT_TEN)));
    Account account = new Account(eventStream);
    Amount balance = account.balance();

    assertThat(balance).isEqualTo(new Amount(new BigDecimal(20)));
  }

  @Test
  void withdraw_should_subtract_amount_from_balance() {
    EventStream fakeEventStream = new FakeEventStream();
    Account account = new Account(fakeEventStream);
    account.withdraw(AMOUNT_TEN);
    Amount balance = account.balance();
    assertThat(balance).isEqualTo(new Amount(new BigDecimal(-10)));
  }

  @Test
  void multiple_withdraws_and_deposits_should_compute_balance(){
    FakeEventStream fakeEventStream = new FakeEventStream();
    Account account = new Account(fakeEventStream);
    account.deposit(AMOUNT_TEN);
    account.withdraw(AMOUNT_TEN);
    Amount balance = account.balance();
    assertThat(balance).isEqualTo(new Amount(BigDecimal.ZERO));
  }
  
}