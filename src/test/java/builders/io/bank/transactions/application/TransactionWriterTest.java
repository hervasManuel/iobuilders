package builders.io.bank.transactions.application;

import builders.io.bank.transactions.domain.Transaction;
import builders.io.bank.transactions.domain.TransactionRepository;
import builders.io.bank.transactions.domain.TxnMethod;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.bus.EventBus;

import java.math.BigDecimal;

import static builders.io.bank.shared.domain.UUIDMother.UUID;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionWriterTest {

    @Mock
    private EventBus eventBus;
    @Mock
    private TransactionRepository repository;
    @InjectMocks
    private TransactionWriter instance;

    @Test
    void writeTransactionInLedger() {
        String trxMethod = TxnMethod.ADD_BALANCE.name();
        BigDecimal bigDecimalMock = new BigDecimal(1);

        TxnMethod trxMethodMock = TxnMethod.ADD_BALANCE;
        try (MockedStatic<TxnMethod> txnMethodStatic = mockStatic(TxnMethod.class)) {
            txnMethodStatic.when(() -> TxnMethod.valueOf(trxMethod))
                    .thenReturn(trxMethodMock);
        }

        Transaction transaction = new Transaction(UUID, trxMethodMock, "fromMock", "toMock", bigDecimalMock);

        try (MockedStatic<Transaction> transactionMock = mockStatic(Transaction.class)) {
            transactionMock.when(() -> Transaction.write(UUID, trxMethodMock,
                            "fromMock", "toMock", bigDecimalMock))
                    .thenReturn(transaction);
        }
        instance.writeTransactionInLedger(UUID,trxMethod,"fromMock","toMock" ,bigDecimalMock);
        verify(repository).save(transaction);
    }
}