@file:Suppress("UnstableApiUsage")

package bookmaker.blockchain

import bookmaker.blockchain.Hash.Companion
import java.time.LocalDateTime
import java.util.UUID

interface Operation : Hashable

interface Event

val SMART_CONTRACT_OPERATION_HASH_GENERATOR = HashGenerator<SmartContractOperation> { from, into ->
    into.putString(from.smartContractSource)
}

data class SmartContractOperation(
    /* Smart contract start */
    val smartContractSource: SmartContractSource
) : Operation {
    override fun hash(): Hash = Hash.of(this, SMART_CONTRACT_OPERATION_HASH_GENERATOR)
}

val ISSUED_SMART_CONTRACT_HASH_GENERATOR = HashGenerator<IssuedSmartContract> { from, into ->
    into.putString(from.smartContractId.toString())
    into.putString(from.function)
}

data class IssuedSmartContract(
    /* Smart contract ID */
    val smartContractId: UUID,
    /* Smart contract start */
    val function: String
) : Hashable {
    override fun hash(): Hash = Hash.of(this, ISSUED_SMART_CONTRACT_HASH_GENERATOR)
}

val BET_DEFINITION_OPERATION_HASH_GENERATOR = HashGenerator<BetDefinitionOperation> { from, into ->
    into.putString(from.betId.toString())
    into.putString(from.startDate.toString())
    into.putString(from.endDate.toString())
}

data class BetDefinitionOperation(
    /* Bet ID in regular database with title, description etc. */
    val betId: UUID,
    /* Bet start */
    val startDate: LocalDateTime,
    /* Bet end */
    val endDate: LocalDateTime,
    /* Bet options */
    val bettingOptions: List<BettingOption>
) : Operation {
    override fun hash(): Hash = Hash.of(this, BET_DEFINITION_OPERATION_HASH_GENERATOR)
}

val USER_BET_OPERATION_HASH_GENERATOR = HashGenerator<UserBetOperation> { from, into ->
    into.putString(from.bettingOption)
    into.putString(from.value.toString())
}

data class UserBetOperation(
    /* User bet value */
    val bettingOption: BettingOption,
    /* Bet amount */
    val value: PLN
) : Operation {
    override fun hash(): Hash = Hash.of(this, USER_BET_OPERATION_HASH_GENERATOR)
}

val RESULT_ANNOUNCEMENT_OPERATION_HASH_GENERATOR = HashGenerator<ResultAnnouncementOperation> { from, into ->
    into.putString(from.result)
}

data class ResultAnnouncementOperation(
    val result: BettingOption
) : Operation {
    override fun hash(): Hash = Hash.of(this, RESULT_ANNOUNCEMENT_OPERATION_HASH_GENERATOR)
}

val PAYCHECK_OPERATION_HASH_GENERATOR = HashGenerator<PaycheckOperation> { from, into ->
    into.putString(from.userBetId.toString())
    into.putBoolean(from.won)
    into.putDouble(from.prize)
}

data class PaycheckOperation(
    val userBetId: Hash,
    /* Identifies if system counted */
    val won: Boolean,
    /* Won prize */
    val prize: PLN
) : Operation {
    override fun hash(): Hash = Hash.of(this, PAYCHECK_OPERATION_HASH_GENERATOR)
}

data class PaycheckEvent(
    val blockchain: BookmakerBettingBlockchain,
    val paycheckBlock: BettingBlock<PaycheckOperation>
) : Event