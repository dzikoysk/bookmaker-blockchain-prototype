@file:Suppress("UnusedReceiverParameter")

package bookmaker.blockchain.contracts

import org.intellij.lang.annotations.Language

const val PLACE_BET_FUNCTION = "placeBet"
const val ANNOUNCE_RESULT_FUNCTION = "announceResult"

@Language("Groovy")
const val BETTING_CONTRACT_SOURCE_CODE = """

// Import dependencies 
import bookmaker.blockchain.BookmakerBettingBlockchain
import bookmaker.blockchain.Hash
import bookmaker.blockchain.Operation
import bookmaker.blockchain.PaycheckEvent
import bookmaker.blockchain.SmartContract
import bookmaker.blockchain.SmartContractApi
import bookmaker.blockchain.SmartContractContext
import bookmaker.blockchain.UserBetOperation
import bookmaker.blockchain.PaycheckOperation
import bookmaker.blockchain.ResultAnnouncementOperation
import bookmaker.blockchain.BettingBlock
import bookmaker.blockchain.SmartContractFunction
import java.time.Instant
import java.util.stream.Collectors

// Smart contract definition
class BettingContract implements SmartContract {
        
    // Data class that represents single player bet
    static record PlayerBet(Hash userBetBlockHash, UUID playerId, double betAmount, String betResult) {}
    // List of all player bets
    private List<PlayerBet> playerBets = new LinkedList<>() 
    
    // SmartContract API instance
    private SmartContractApi api
    // SmartContract constructor
    BettingContract(SmartContractApi api) { this.api = api }    
    
    // Smart contract function that adds a new user's bet to the contract
    void placeBet(BettingBlock<UserBetOperation> userBetBlock) {
        // add new bet to the list of all bets
        playerBets.add(
            // map blockchain bet operation to PlayerBet data class
            new PlayerBet(
                userBetBlock.hash(), // bet id (block hash)
                userBetBlock.owner, // user id (owner of the block)
                userBetBlock.operation.value, // bet amount (in PLN)
                userBetBlock.operation.bettingOption // selected winner by user
            )
        )
    }
    
    // Smart contract function that announces the result of the match, calculates the prize pool and pays the winners
    double announceResult(BookmakerBettingBlockchain blockchain, BettingBlock<ResultAnnouncementOperation> resultAnnouncementBlock) {      
        // select winners
        List<PlayerBet> winners = playerBets.stream() // stream all bets
            .filter { playerBet -> playerBet.betResult == resultAnnouncementBlock.operation.result } // take bets that match the result
            .collect(Collectors.toList()) // collect winners to list
        
        // calculate prize pool by summing all bets
        double prizePool = playerBets.stream().mapToDouble { playerBet -> playerBet.betAmount }.sum()
        // sum prize pool of winners to calculate the odd relative to the paid value
        double winnersPool = winners.stream().mapToDouble { playerBet -> playerBet.betAmount }.sum()
        // relative odd
        double betOdd = 1.0 + ((prizePool - winnersPool) / winnersPool) 
        
        // register paycheck blocks for all users (winners and losers)
        playerBets.each { playerBet ->
            // check if this user won
            boolean won = playerBet.betResult() == resultAnnouncementBlock.operation.result
            
            // call paycheckPrize function to register paycheck block
            paycheckPrize(
                blockchain, // current blockchain instance
                new BettingBlock<PaycheckOperation>( // create new paycheck block
                    blockchain.getCurrentHash(), // set parent to hash of the current block in blockchain
                    resultAnnouncementBlock.owner, // set owner id to user id
                    Instant.now().toEpochMilli(), // set timestamp to now
                    null, // don't issue any new contract
                    new PaycheckOperation( // create new paycheck operation
                        playerBet.userBetBlockHash(), // set bet id equal to hash of the bet placing block
                        won, // define if user won or not
                        won ? (playerBet.betAmount() * betOdd) : 0.0D // if user won, set prize to amount multiplied by odd
                    )
                )
            )
        }
        
        // return final betting odd
        return betOdd
    }
    
    // Internal smart contract function that registers paycheck block in blockchain and emits PaycheckEvent to contract listeners
    private void paycheckPrize(BookmakerBettingBlockchain blockchain, BettingBlock<PaycheckOperation> paycheckBlock) {
        // register paycheck block in blockchain
        blockchain.registerBlock(paycheckBlock)
        // emit PaycheckEvent to contract listeners
        api.emitEvent(new PaycheckEvent(blockchain, paycheckBlock))
    }

    // Exports list of public functions from this contract
    @Override
    Map<String, SmartContractFunction<? extends Operation>> getFunctions() {
        return [
            "$PLACE_BET_FUNCTION": 
                { ctx -> return placeBet(ctx.block) } as SmartContractFunction<UserBetOperation>,
            "$ANNOUNCE_RESULT_FUNCTION": 
                { ctx -> return announceResult(ctx.blockchain, ctx.block) } as SmartContractFunction<ResultAnnouncementOperation>,
        ]
    }

    // Exports list of public, read-only variables from this contract
    @Override
    Map<String, Object> getMemory() {
        return [
            "playerBets": playerBets
        ]
    }

}
"""