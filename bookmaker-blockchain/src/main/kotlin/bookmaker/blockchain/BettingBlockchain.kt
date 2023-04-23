package bookmaker.blockchain

import java.util.Collections
import java.util.LinkedList
import java.util.UUID

typealias SmartContractSource = String
typealias PLN = Double
typealias BettingOption = String

interface BettingBlockchainVirtualMachine {
    fun runSmartContract(smartContractCode: SmartContractSource): SmartContract
}

class BookmakerBettingBlockchain(
    /* Blockchain ID */
    val id: UUID,
    /* Virtual machine for smart contracts */
    private val vm: BettingBlockchainVirtualMachine,
    /* Main block that starts chain */
    blockZero: BettingBlock<out Operation>
) {

    // default registry to keep order of blocks
    private val registry = Collections.synchronizedList(LinkedList<BettingBlock<out Operation>>().apply { add(blockZero) })
    // cache for faster access to blocks by hash (expensive operation)
    private val blocksByHashCache = mutableMapOf<Hash, BettingBlock<out Operation>>()
    // running smart contracts
    private val smartContracts = mutableMapOf<UUID, SmartContract>()

    fun registerBlock(rawBlock: BettingBlock<out Operation>): TransactionResult {
        val block = when {
            rawBlock.parent == null -> rawBlock.copy(parent = getCurrentHash())
            else -> rawBlock
        }

        require(!blocksByHashCache.containsKey(block.hash())) { "Block already exists" }
        registry.add(block)
        blocksByHashCache[block.hash()] = block

        val operation = block.operation
        val issuedContract = block.issuedContract

        val smartContractResult = when {
            operation is SmartContractOperation -> {
                require(issuedContract == null) { "Smart contract cannot issue another smart contract" }
                val smartContractId = UUID.nameUUIDFromBytes(operation.smartContractSource.toByteArray())
                require(smartContracts[smartContractId] == null) { "Smart contract already exists" }
                smartContracts[smartContractId] = vm.runSmartContract(operation.smartContractSource)
                smartContractId
            }
            issuedContract != null -> {
                smartContracts[issuedContract.smartContractId]!!
                    .getFunctions()[issuedContract.function]!!
                    .let {
                        @Suppress("UNCHECKED_CAST")
                        it as SmartContractFunction<Operation>
                    }
                    .execute(
                        SmartContractContext(
                            blockchain = this,
                            block = block
                        ) as SmartContractContext<Operation>
                    )
            }
            else -> null
        }

        return TransactionResult(
            block = block,
            smartContractResult = smartContractResult
        )
    }

    fun getSmartContract(id: UUID): SmartContract? =
        smartContracts[id]

    fun getBlock(hash: Hash): BettingBlock<out Operation>? =
        blocksByHashCache[hash]

    fun getCurrentHash(): Hash? =
        registry.lastOrNull()?.hash()

    fun getBlockZero(): BettingBlock<out Operation> =
        registry.first()

}

class TransactionResult(
    val block: BettingBlock<out Operation>,
    val smartContractResult: Any?
) {
    @Suppress("UNCHECKED_CAST")
    fun <R> getSmartContractResultAs(): R = smartContractResult as R
}
