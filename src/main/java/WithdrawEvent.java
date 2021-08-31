public record WithdrawEvent(Amount amount) implements TransactionEvent {

  @Override
  public Amount amount() {
    return new Amount(amount.value().negate());
  }
}
