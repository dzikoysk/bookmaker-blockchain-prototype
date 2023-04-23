package bookmaker.server.betting

import bookmaker.blockchain.BettingBlockchainVirtualMachine
import bookmaker.blockchain.PaycheckEvent
import bookmaker.blockchain.SmartContractApi
import bookmaker.server.user.Transfer
import bookmaker.server.user.UserService
import java.time.LocalDateTime

class BetWebSettings(
    private val userService: UserService
) {

    fun smartContractApi(): SmartContractApi =
        BettingSmartContractApi().also {
            it.registerListener(PaycheckEvent::class) { event ->
                val paycheckOperation = event.paycheckBlock.operation
                val betBlock = event.blockchain.getBlock(paycheckOperation.userBetId)!!
                val user = userService.findUserById(betBlock.owner)!!

                user.wallet.history.add(
                    Transfer(
                        title = "Prize for bet in ${event.blockchain.id} blockchain",
                        date = LocalDateTime.now(),
                        value = paycheckOperation.prize,
                    )
                )
            }
        }

    fun bettingBlockchainVirtualMachine(
        smartContractApi: SmartContractApi = smartContractApi()
    ): BettingBlockchainVirtualMachine =
        GroovyVirtualMachine(
            smartContractApi = smartContractApi
        )

    fun betBlockchainService(
        bettingBlockchainVirtualMachine: BettingBlockchainVirtualMachine = bettingBlockchainVirtualMachine()
    ): BetBlockchainService =
        BetBlockchainService(
            bettingBlockchainVirtualMachine = bettingBlockchainVirtualMachine
        )

    fun betRepository(): BetRepository =
        InMemoryBetRepository()

    fun createBettingService(
        betRepository: BetRepository = betRepository(),
        betBlockchainService: BetBlockchainService = betBlockchainService()
    ): BettingService {
        return BettingService(
            betRepository = betRepository,
            betBlockchainService = betBlockchainService,
            userService = userService
        )
    }

}