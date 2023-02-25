pragma solidity ^0.8.13;

import "truffle/Assert.sol";
import "truffle/DeployedAddresses.sol";
import "../contracts/ioCoin.sol";

contract TestIoCoin {

  function testInitialBalanceUsingDeployedContract() public {
    ioCoin meta = ioCoin(DeployedAddresses.ioCoin());

    uint expected = 10000;

    Assert.equal(meta.getBalance(tx.origin), expected, "Owner should have 10000 ioCoin initially");
  }

  function testInitialBalanceWithNewioCoin() public {
    ioCoin meta = new ioCoin();

    uint expected = 10000;

    Assert.equal(meta.getBalance(tx.origin), expected, "Owner should have 10000 ioCoin initially");
  }

}
