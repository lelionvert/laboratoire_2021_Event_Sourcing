package domain.events;

import domain.Amount;

public record DepositEvent(Amount amount) implements TransactionEvent {
}
