import smartContractCode from './bets-smart-contract'

const placeBetBlock = (parent, user, option, value) => ({
  parent: parent,
  ownerId: user,
  timestamp: 1680500400000,
  contract: {
    smartContractId: "c4d3de3d-1fea-4d7c-29f63c4c3454",
    function: "placeBet",
  },
  operation: {
    type: "User Bet Operation",
    bettingOption: option,
    value: value
  }
})

const blockchain = {
  id: "f8c3de3d-1fea-4d7c-29f63c4c3454",
  blocks: [
    {
      parent: null,
      ownerId: "f8c3de3d-1fea-4d7c-29f63c4c3454",
      timestamp: 1680500400000,
      contract: null,
      operation: {
        type: "Bet Definition Operation",
        betId: "f8c3de3d-1fea-4d7c-29f63c4c3454",
        startDate: new Date("2023-03-01"),
        endDate: new Date(),
      }
    },
    {
      parent: "ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb",
      ownerId: "f8c3de3d-1fea-4d7c-29f63c4c3454",
      timestamp: 1680500400000,
      contract: null,
      operation: {
        type: "Smart Contract Operation",
        smartContractSource: smartContractCode,
      }
    },
    placeBetBlock("ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb", "f8c3de3d-1fea-4d7c-29f63c4c3454", "FNC", 10.00),
    placeBetBlock("aa421112ca2bbdcafac231b39a23dc4da786eff8147c4e72b9807785aabc3222", "dec3de3a-2ced-ed71-394063c4c341", "G2", 20.00),
    placeBetBlock("1a421112ca2bbdcafac231b39a23dc4da786eff8147c4e72b9807785adfdf754", "4d7cde3a-2ce4-1271-394063cf8c3d", "G2", 25.00),
    placeBetBlock("3e23e8160039594a33894f6564e1b1348bbd7a0088d42c4acb73eeaed59c009d", "22da2341-bd02-1219-92234cbdefff", "FNC", 30.00),
    placeBetBlock("18ac3e7343f016890c510e93f935261169d9e3f565436429830faf0934f4f8e4", "e3f56543-4f8e-4f8e-4f8e4f8e4f8e", "FNC", 35.00),
    placeBetBlock("454349e422f05297191ead13e21d3db520e5abef52055e4964b82fb213f593a1", "9423f593-a1e2-1d3d-b520e5abef52", "FNC", 40.00),
    placeBetBlock("2d93e717289648ac9b01cb58e72b7b8f193fd9e9d82c3e6f01eec09bf461b237", "e72b7b8f-193f-d9e9-d82c3e6f01ee", "G2", 30.00),
    placeBetBlock("34e42f3c42312acb4b2032ec3e847810e1d4d92806d5727df7d1382424943bfa", "6727df7d-1382-4249-4249424943bf", "G2", 35.00),
    placeBetBlock("2d93e717289648ac9b01cb58e72b7b8f193fd9e9d82c3e6f01eec09bf461b237", "f72b7b8f-593f-d9eb-d82c3e6f01e3", "G2", 30.00),
    placeBetBlock("e6cda6af51597e5886e15ecea5dc53695f32185862b5b594982c40bf7c0c60bb", "d74b7b8f-f93f-b9e9-e22c3e6f01fe", "G2", 30.00),
    placeBetBlock("8f3e7f2c4c73a3d4debdc96b5c1062c2672e43ab9d2017ea9dfaeb0eb97e2712", "132b7b8f-e93f-a9ed-f12c3d6f01d1", "G2", 30.00),
  ]
}

export default blockchain