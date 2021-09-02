package infra.web;

import domain.Amount;
import domain.TransactionHistory;
import infra.usecases.Deposit;
import infra.usecases.GetBalance;
import infra.usecases.Withdraw;
import infra.web.dtos.BalanceBody;
import infra.web.dtos.TransactionBody;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import java.math.BigDecimal;

public class AccountResource {

  private final TransactionHistory transactionHistory;

  public AccountResource(TransactionHistory transactionHistory) {
    this.transactionHistory = transactionHistory;
  }

  public void postDeposit(Context context) {
    TransactionBody body = context.bodyAsClass(TransactionBody.class);
    Deposit deposit = new Deposit(transactionHistory, mapTo(body));
    deposit.execute();
    context.status(HttpCode.CREATED);
  }

  public void postWithdraw(Context context) {
    TransactionBody body = context.bodyAsClass(TransactionBody.class);
    Withdraw withdraw = new Withdraw(transactionHistory, mapTo(body));
    withdraw.execute();
    context.status(HttpCode.CREATED);
  }

  public void getBalance(Context context) {
    GetBalance getBalance = new GetBalance(transactionHistory);
    Amount balance = getBalance.execute();
    context.json(mapTo(balance))
        .status(HttpCode.OK);
  }

  private BalanceBody mapTo(Amount balance) {
    return new BalanceBody(balance.value().doubleValue());
  }

  private Amount mapTo(TransactionBody body) {
    return new Amount(BigDecimal.valueOf(body.getAmount()));
  }
}
