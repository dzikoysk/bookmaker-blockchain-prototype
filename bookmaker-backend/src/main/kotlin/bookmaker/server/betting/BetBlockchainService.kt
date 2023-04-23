package bookmaker.server.betting

import bookmaker.blockchain.BettingBlock
import bookmaker.blockchain.BetDefinitionOperation
import bookmaker.blockchain.BettingBlockchainVirtualMachine
import bookmaker.blockchain.BettingOption
import bookmaker.blockchain.BookmakerBettingBlockchain
import bookmaker.blockchain.IssuedSmartContract
import bookmaker.blockchain.Operation
import bookmaker.blockchain.TransactionResult
import java.time.LocalDateTime
import java.util.UUID

class BetBlockchainService(
    private val bettingBlockchainVirtualMachine: BettingBlockchainVirtualMachine
) {

    private val bets = mutableMapOf<UUID, BookmakerBettingBlockchain>()

    fun createBetBlockchain(
        bet: Bet,
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        bettingOptions: List<BettingOption>
    ): BookmakerBettingBlockchain {
        val blockchain = BookmakerBettingBlockchain(
            id = bet.id,
            vm = bettingBlockchainVirtualMachine,
            blockZero = BettingBlock(
                owner = bet.ownerId,
                operation = BetDefinitionOperation(
                    betId = UUID.randomUUID(),
                    startDate = startDate,
                    endDate = endDate,
                    bettingOptions = bettingOptions,
                )
            )
        )

        bets[bet.id] = blockchain
        return blockchain
    }

    fun registerNewBlock(betId: UUID, owner: UUID, issuedSmartContract: IssuedSmartContract? = null, operation: Operation): TransactionResult =
        bets[betId]!!.registerBlock(
            BettingBlock(
                owner = owner,
                issuedContract = issuedSmartContract,
                operation = operation
            )
        )

}