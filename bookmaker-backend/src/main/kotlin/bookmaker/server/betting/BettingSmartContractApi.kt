package bookmaker.server.betting

import bookmaker.blockchain.Event
import bookmaker.blockchain.SmartContractApi
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class BettingSmartContractApi : SmartContractApi {

    private val listeners = mutableMapOf<KClass<*>, (Any?) -> Unit>()

    override fun emitEvent(event: Any) {
        listeners[event::class]?.invoke(event)
    }

    fun <E : Event> registerListener(eventType: KClass<E>, listener: (E) -> Unit) {
        listeners[eventType] = listener as (Any?) -> Unit
    }

}