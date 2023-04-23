package bookmaker.simulations.specification

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import bookmaker.blockchain.BettingOption
import bookmaker.blockchain.PLN
import bookmaker.server.auth.toSha256
import bookmaker.server.betting.BetWebSettings
import bookmaker.server.betting.BettingService
import bookmaker.server.betting.BettingService.PlaceBetRequest
import bookmaker.server.user.Permission
import bookmaker.server.user.Permission.BET
import bookmaker.server.user.Transfer
import bookmaker.server.user.User
import bookmaker.server.user.UserService
import bookmaker.server.user.UserService.RegistrationRequest
import java.time.LocalDateTime
import java.util.UUID

data class SimulationContext(
    val logging: Boolean = true,
    val logger: Logger = LoggerFactory.getLogger(SimulationContext::class.java),
    val userService: UserService = UserService(),
    val bettingService: BettingService = BetWebSettings(userService).createBettingService()
) {

    fun createPlatformManager() =
        userService.register(
            RegistrationRequest(
                username = "platform-manager",
                password = "manager-password".toSha256(),
                name = "Ludwik",
                surname = "Narbutt",
                permissions = Permission.values().toList(),
            )
        )

    fun generatePlayer(idx: Int) =
        userService.register(
            RegistrationRequest(
                username = "player-$idx",
                password = "password-$idx",
                name = "Ludwik",
                surname = "Narbutt",
                permissions = listOf(BET),
            )
        ).also {
            if (logging) logger.info("Stworzono gracza ${it.username} o id ${it.id}")
        }

    fun addMoney(player: User, amount: PLN) {
        player.wallet.history.add(
            Transfer(
                title = "Initial deposit",
                date = LocalDateTime.now(),
                value = amount
            )
        )
        if (logging) logger.info("Zasilono portfel gracza ${player.username} kwotą $amount PLN")
    }

    fun placeBet(player: User, betId: UUID, option: BettingOption, value: PLN) {
        bettingService.placeBet(
            PlaceBetRequest(
                userId = player.id,
                betId = betId,
                bettingOption = option,
                value = value
            )
        )
        if (logging) logger.info("Gracz ${player.username} postawił $value PLN na opcję $option")
    }

}

fun simulation(
    ctx: SimulationContext = SimulationContext(),
    body: SimulationContext.() -> Unit
) = body(ctx)