package builders.io.bank.virtualmachine.application;

import builders.io.bank.transactions.application.TransactionWriter;
import builders.io.bank.wallet.application.WalletUpdateBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class AddBalanceTrx implements TrxOperation {
    private static final Logger logger = LoggerFactory.getLogger(AddBalanceTrx.class);
    public static final String ADD_BALANCE = "ADD_BALANCE";
    public static final String NULL_ADDRESS = "NullAddress";
    private final WalletUpdateBalance walletUpdateBalance;
    private final TransactionWriter transactionWriter;
    private final String transactionId;
    private final String to;
    private final BigDecimal value;


    public AddBalanceTrx(String transactionId,
                         String to,
                         BigDecimal value,
                         WalletUpdateBalance walletUpdateBalance,
                         TransactionWriter transactionWriter) {
        this.transactionId = transactionId;
        this.to = to;
        this.value=value;
        this.walletUpdateBalance = walletUpdateBalance;
        this.transactionWriter =  transactionWriter;
    }
    @Override
    public void execute(){
        try{
            logger.info("Execution ADD_BALANCE transaction: Address: {} Value: {}",to,value);
            walletUpdateBalance.addBalance(to,value);
            transactionWriter.writeTransactionInLedger(transactionId,ADD_BALANCE,NULL_ADDRESS,to,value);
            logger.info("ADD_BALANCE transaction {} wrote in Ledger", transactionId);
        }
        catch (Exception e){
            logger.warn("Execution ADD_BALANCE transaction canceled!: {}",e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
