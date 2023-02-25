package builders.io.bank.transactions.application;

import builders.io.bank.transactions.domain.Transaction;
import builders.io.bank.transactions.domain.TransactionRepository;
import builders.io.bank.transactions.domain.TxnMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.math.BigDecimal;

@Service
public final class TransactionWriter {
    private static final Logger logger = LoggerFactory.getLogger(TransactionWriter.class);
    private final EventBus eventBus;
    private final TransactionRepository repository;
    public TransactionWriter(EventBus eventBus, TransactionRepository repository){
        this.eventBus = eventBus;
        this.repository = repository;
    }
    public void writeTransactionInLedger(String transactionID, String trxMethod, String from, String to, BigDecimal value){
        logger.info("Writing transaction in Ledger");
        TxnMethod method = TxnMethod.valueOf(trxMethod);
        Transaction transaction = Transaction.write(transactionID,
                method,
                from,
                to,
                value);
        repository.save(transaction);
        transaction.pullDomainEvents().forEach( domainEvent ->
               eventBus.notify("transactions_topic", Event.wrap(domainEvent)));
    }
}
