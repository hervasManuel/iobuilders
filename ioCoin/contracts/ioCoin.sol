// SPDX-License-Identifier: MIT
pragma solidity ^0.8.13;

import "./ConvertLib.sol";

contract ioCoin {
	mapping (address => uint) balances;
	string private name;                   //Name of token
	string private symbol;                 //Token identifier

	event Transfer(address indexed _from, address indexed _to, uint256 _value);

	constructor() {
		name = "ioCoin";
		symbol = "IOB";
		balances[tx.origin] = 10000;
	}

	function mint(address addr, uint256 value) public returns (bool) {
		require(addr != address(0));
		balances[addr] += value;
		emit Transfer(address(0), addr, value);
		return true;
	}

	function balanceOf(address addr) public view returns (uint256) {
		return balances[addr];
	}

	function getBalanceInEth(address addr) public view returns(uint){
		return ConvertLib.convert(getBalance(addr),2);
	}

	function getBalance(address addr) public view returns(uint) {
		return balances[addr];
	}

	function transferFrom(address from, address to,	uint256 value) public returns (bool) {
		require(value <= balances[from]);
		require(to != address(0));

		balances[from] -= value;
		balances[to] += value;

		emit Transfer(from, to, value);
		return true;
	}



}
