package bookmaker.blockchain

interface SmartContractApi {
    fun emitEvent(event: Any)
}

interface SmartContract {
    /* Smart contract global variables */
    fun getMemory(): Map<String, Any>
    /* Smart contract external API functions */
    fun getFunctions(): Map<String, SmartContractFunction<out Operation>>
}

data class SmartContractContext<OPERATION : Operation>(
    val blockchain: BookmakerBettingBlockchain,
    val block: BettingBlock<OPERATION>
)

fun interface SmartContractFunction<OPERATION : Operation> {
    fun execute(context: SmartContractContext<OPERATION>): Any?
}