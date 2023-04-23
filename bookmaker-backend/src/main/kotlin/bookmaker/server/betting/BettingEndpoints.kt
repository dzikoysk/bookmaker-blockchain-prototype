package bookmaker.server.betting

import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.http.UnauthorizedResponse
import io.javalin.http.bodyAsClass
import bookmaker.blockchain.BettingOption
import bookmaker.blockchain.PLN
import bookmaker.server.auth.AbstractAuthenticatedEndpoints
import bookmaker.server.user.Permission.BET
import bookmaker.server.user.Permission.CREATE_BET
import bookmaker.server.betting.BettingService.CreateBetRequest
import bookmaker.server.betting.BettingService.PlaceBetRequest
import bookmaker.server.user.UserService
import java.time.LocalDateTime
import java.util.UUID

class BettingEndpoints(
    private val authenticationEnabled: Boolean = true,
    userService: UserService,
    private val bettingService: BettingService
) : AbstractAuthenticatedEndpoints(userService) {

    data class CreateBetRequestDto(
        val title: String,
        val description: String,
        val startDate: LocalDateTime,
        val endDate: LocalDateTime,
        val bettingOptions: List<BettingOption>
    )

    fun createBet() = Handler { ctx ->
        ctx.loggedUser { user ->
            if (!user.permissions.contains(CREATE_BET)) {
                throw UnauthorizedResponse("User does not have permission to create a new bet")
            }

            ctx.result(
                with(ctx.bodyAsClass<CreateBetRequestDto>()) {
                    bettingService.createBet(
                        CreateBetRequest(
                            ownerId = user.id,
                            title = title,
                            description = description,
                            startDate = startDate,
                            endDate = endDate,
                            bettingOptions = bettingOptions
                        )
                    ).toString()
                }
            )
        }
    }

    data class PlaceBetDto(
        val userId: UUID? = null,
        val betId: UUID,
        val bettingOption: BettingOption,
        val value: PLN
    )

    fun placeBet() = Handler { ctx ->
        if (authenticationEnabled) {
            ctx.loggedUser { user ->
                if (!user.permissions.contains(BET)) {
                    throw UnauthorizedResponse("User does not have permission to bet")
                }
                placeBet(ctx, user.id)
            }
        } else {
            placeBet(ctx, null)
        }
    }

    private fun placeBet(ctx: Context, userIdFromSession: UUID?) {
        val betResult = with(ctx.bodyAsClass<PlaceBetDto>()) {
            bettingService.placeBet(
                PlaceBetRequest(
                    userId = userIdFromSession ?: userId!!,
                    betId = betId,
                    bettingOption = bettingOption,
                    value = value
                )
            )
        }

        ctx.result(betResult.block.hash().toString())
    }

    fun getBets() = Handler { ctx ->
        ctx.json(
            bettingService.getAllBets()
        )
    }

}