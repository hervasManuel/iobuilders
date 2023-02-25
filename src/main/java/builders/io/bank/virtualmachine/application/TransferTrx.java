package builders.io.bank.virtualmachine.application;

import builders.io.bank.transactions.application.TransactionWriter;
import builders.io.bank.wallet.application.WalletUpdateBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
public class TransferTrx implements TrxOperation {
    private static final Logger logger = LoggerFactory.getLogger(TransferTrx.class);
    public static final String TRANSFER = "TRANSFER";
    private final WalletUpdateBalance walletUpdateBalance;
    private final TransactionWriter transactionWriter;
    private final String transactionId;
    private final String from;
    private final String to;
    private final BigDecimal value;

    public TransferTrx(String transactionId,
                       String from,
                       String to,
                       BigDecimal value,
                       WalletUpdateBalance walletUpdateBalance,
                       TransactionWriter transactionWriter){
        this.transactionId = transactionId;
        this.from = from;
        this.to = to;
        this.value = value;
        this.walletUpdateBalance = walletUpdateBalance;
        this.transactionWriter =  transactionWriter;
    }
    @Override
    public void execute(){
        try {
            logger.info("Execution TRANSFER transaction -> From Address: {} Value: {}",from,value.negate());
            logger.info("                               -> To   Address: {} Value: {}",to,value);
            walletUpdateBalance.subtractBalance(from,value);
            walletUpdateBalance.addBalance(to,value);
            transactionWriter.writeTransactionInLedger(transactionId,TRANSFER,from,to,value);
            logger.info("TRANSFER transaction {} wrote in Ledger", transactionId);
        }
        catch (Exception e){
            logger.warn("Transaction execution TRANSFER canceled!: {}", e.getMessage());
        }
    }
}
