const ConvertLib = artifacts.require("ConvertLib");
const ioCoin = artifacts.require("ioCoin");

module.exports = function(deployer) {
  deployer.deploy(ConvertLib);
  deployer.link(ConvertLib, ioCoin);
  deployer.deploy(ioCoin);
};
