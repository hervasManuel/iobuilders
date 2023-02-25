package builders.io.bank.virtualmachine.application;

import builders.io.bank.transactions.application.TransactionWriter;
import builders.io.bank.wallet.application.WalletUpdateBalance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddBalanceTrxTest {

    @Mock
    private WalletUpdateBalance walletUpdateBalance;
    @Mock
    private TransactionWriter transactionWriter;


    @InjectMocks
    private AddBalanceTrx instance;

    @Test
    void execute() {
        instance.execute();
        verify(walletUpdateBalance).addBalance(any(), any());
        verify(transactionWriter).writeTransactionInLedger(any(), any(),any(),any(),any());
    }
}