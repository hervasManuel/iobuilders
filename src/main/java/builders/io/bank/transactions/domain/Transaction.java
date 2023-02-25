package builders.io.bank.transactions.domain;

import builders.io.bank.shared.domain.AggregateRoot;
import builders.io.bank.shared.domain.transaction.TransactionSentDomainEvent;
import builders.io.bank.shared.domain.transaction.TransactionWroteDomainEvent;

import java.math.BigDecimal;
import java.util.Objects;

public final class Transaction extends AggregateRoot {
    private final String transactionId;
    private final TxnMethod txnMethod;
    private final String from;
    private final String to;
    private final BigDecimal value;

    public Transaction(String transactionId,
                       TxnMethod txnMethod,
                       String from,
                       String to,
                       BigDecimal value){
        this.transactionId = transactionId;
        this.txnMethod = txnMethod;
        this.from = from;
        this.to = to;
        this.value = value;
    }
    public static Transaction send(String transactionId,
                                   TxnMethod txnMethod,
                                   String from,
                                   String to,
                                   BigDecimal value){
        Transaction transaction = new Transaction(transactionId, txnMethod,from, to, value);
        transaction.recordEvent(new TransactionSentDomainEvent(transactionId,
                                                          txnMethod.toString(),
                                                          from,
                                                          to,
                                                          value
        ));
        return transaction;
    }

    public static Transaction write(String transactionId,
                                   TxnMethod txnMethod,
                                   String from,
                                   String to,
                                   BigDecimal value){
        Transaction transaction = new Transaction(transactionId, txnMethod,from, to, value);
        transaction.recordEvent(new TransactionWroteDomainEvent(transactionId,
                txnMethod.toString(),
                from,
                to,
                value
        ));
        return transaction;
    }
    public String transactionId() {return transactionId;}
    public TxnMethod txnMethod() {return txnMethod;}
    public String from() {return from;}
    public String to() {return to;}
    public BigDecimal value() {return value;}

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Transaction transaction = (Transaction) o;
        return  txnMethod.equals(transaction.txnMethod) &&
                from.equals(transaction.from) &&
                to.equals(transaction.to) &&
                value.equals(transaction.value);
    }
    @Override
    public int hashCode() {return Objects.hash(txnMethod, from, to, value);}
}
