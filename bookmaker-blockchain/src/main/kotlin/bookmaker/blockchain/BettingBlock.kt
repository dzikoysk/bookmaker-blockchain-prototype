package bookmaker.blockchain

import bookmaker.blockchain.Hash.Companion
import java.time.Instant
import java.util.UUID

@Suppress("UnstableApiUsage")
val BETTING_BLOCK_HASH_GENERATOR = HashGenerator<BettingBlock<out Operation>> { from, into ->
    into.putString(from.parent?.value ?: "")
    into.putString(from.owner.toString())
    into.putLong(from.createdAt)
    into.putString(from.issuedContract?.hash()?.value ?: "")
    into.putString(from.operation.hash().value)
}

data class BettingBlock<OPERATION : Operation>(
    /** Parent hash */
    val parent: Hash? = null,
    /** User's ID */
    val owner: UUID,
    /** Block creation date */
    val createdAt: Long = Instant.now().toEpochMilli(),
    /* Issued smart contract if used, otherwise null */
    val issuedContract: IssuedSmartContract? = null,
    /** Operation to record in chain */
    val operation: OPERATION
) : Hashable {
    private val hash by lazy { Hash.of(this, BETTING_BLOCK_HASH_GENERATOR) }
    override fun hash(): Hash = hash
}
