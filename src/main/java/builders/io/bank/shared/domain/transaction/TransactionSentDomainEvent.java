package builders.io.bank.shared.domain.transaction;

import builders.io.bank.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Objects;

public class TransactionSentDomainEvent extends DomainEvent {
    private final String method;
    private final String from;
    private final String to;
    private final BigDecimal value;

    public TransactionSentDomainEvent(String aggregateId, String method,
                                      String from,
                                      String to,
                                      BigDecimal value) {
        super(aggregateId);
        this.method = method;
        this.from = from;
        this.to = to;
        this.value = value;
    }

    public TransactionSentDomainEvent(
            String aggregateId,
            String eventId,
            String occurredOn,
            String method,
            String from,
            String to,
            BigDecimal value
    ) {
        super(aggregateId, eventId, occurredOn);
        this.method = method;
        this.from = from;
        this.to = to;
        this.value = value;
    }

    @Override
    public String eventName() {
        return "transaction.sent";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        HashMap<String, Serializable> primitives = new HashMap<>();
        primitives.put("method", method);
        primitives.put("from", from);
        primitives.put("to", to);
        primitives.put("value", value);
        return primitives;
    }

    public String method() {return method;}
    public String from() {return from;}
    public String to() {return to;}
    public BigDecimal value() {return value;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransactionSentDomainEvent that = (TransactionSentDomainEvent) o;
        return method.equals(that.method) &&
                from.equals(that.from) &&
                to.equals(that.to) &&
                value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method,from,to,value);
    }
}
