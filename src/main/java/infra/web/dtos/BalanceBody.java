package infra.web.dtos;

public class BalanceBody {

  private double value;

  public BalanceBody(double value) {
    this.value = value;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }
}
