import java.math.BigDecimal;

public record Amount(BigDecimal value) {

  public Amount add(Amount amount) {
     return new Amount(this.value.add(amount.value));
  }
}
