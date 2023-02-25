package builders.io.bank.blockchain.infrastructure;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Type;
import org.web3j.exceptions.MessageDecodingException;
import org.web3j.protocol.Web3j;
import org.web3j.abi.datatypes.Function;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.io.IOException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class BlockchainGetController implements BlockchainGetEndpoint {
    private static final Logger logger = LoggerFactory.getLogger(BlockchainGetController.class);
    public static final String TRANSACTION_INVALID = "Transaction Invalid";
    private final Web3j web3j = Web3j.build(new HttpService("http://localhost:7545"));

    @Override
    public ResponseEntity blockchain(String from, String to) {
        logger.info("Successfully connected to Ganache");

        try {
            Web3ClientVersion clientVersion = web3j.web3ClientVersion().send();
            logger.info("Client version: {}", clientVersion.getWeb3ClientVersion());

            EthGasPrice gasPrice = web3j.ethGasPrice().send();
            logger.info("Default Gas Price: {}", gasPrice.getGasPrice());

            EthGetBalance balance = getEthBalance(from);
            logger.info("Balance of 'from' Wallet: {} Wei", balance.getBalance());

            logger.info("Balance in Ether format: {} ETH",
                    Convert.fromWei(web3j.ethGetBalance(from,
                                                        DefaultBlockParameterName.LATEST)
                                    .send().getBalance().toString(),
                                    Convert.Unit.ETHER));

            logger.info("Send Transaction: Transfer from: {}", from);
            logger.info("                             to: {}", to);
            logger.info("                          value: 50 ETH");
            String hash = sendTransaction(from,to);
            logger.info("Transaction executed. Hash: {}", hash);
            return new ResponseEntity<>("Transaction sent", HttpStatus.OK);
        } catch (IOException | ExecutionException | InterruptedException | MessageDecodingException e) {
            logger.error("Error: {}", e.getMessage());
            Thread.currentThread().interrupt();
            return new ResponseEntity<>("Error sending json-rpc requests: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public EthGetBalance getEthBalance(String address) throws ExecutionException, InterruptedException {
        return this.web3j.ethGetBalance(address,
                        DefaultBlockParameterName.LATEST)
                .sendAsync()
                .get();
    }
    @Async
    public String sendTransaction(String from, String to) {
        String transactionHash;
        try {
            List outputParams = new ArrayList<TypeReference<Type>>();
            Function function = new Function("transferFrom",
                                             Arrays.asList(new org.web3j.abi.datatypes.Address(from),
                                                           new org.web3j.abi.datatypes.Address(to),
                                                           new org.web3j.abi.datatypes.generated.Uint256(50)),
                                             outputParams);

            String encodedFunction = FunctionEncoder.encode(function);

            BigInteger nonce = BigInteger.valueOf(100);
            BigInteger gasPrice = BigInteger.valueOf(2000000000);
            BigInteger gasLimit = BigInteger.valueOf(6721975);

            Transaction transaction = Transaction.createFunctionCallTransaction(from,
                                                                                nonce,
                                                                                gasPrice,
                                                                                gasLimit,
                                                                                to,
                                                                                encodedFunction);

            EthSendTransaction transactionResponse = web3j.ethSendTransaction(transaction).sendAsync().get();
            transactionHash = transactionResponse.getTransactionHash();

        } catch (ExecutionException | InterruptedException  ex) {
            logger.error("Error sending transaction");
            Thread.currentThread().interrupt();
            return TRANSACTION_INVALID;
        }
        return transactionHash;
    }
}


