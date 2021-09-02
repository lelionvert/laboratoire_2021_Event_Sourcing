package domain.events;

import domain.Amount;

public interface TransactionEvent {

  Amount amount();
}
