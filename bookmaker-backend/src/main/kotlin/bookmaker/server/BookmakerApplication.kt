package bookmaker.server

import io.javalin.Javalin
import bookmaker.server.auth.AuthenticationEndpoints
import bookmaker.server.auth.AuthenticationService
import bookmaker.server.betting.BettingEndpoints
import bookmaker.server.betting.BettingService
import bookmaker.server.betting.BetWebSettings
import bookmaker.server.user.UserEndpoints
import bookmaker.server.user.UserService

fun main() {
    createBookmakerApplication()
        .server
        .start(8080)
}

class BookmakerApplication(
    val userService: UserService,
    val authenticationService: AuthenticationService,
    val bettingService: BettingService,
    val server: Javalin
)

fun createBookmakerApplication(authenticationEnabled: Boolean = true): BookmakerApplication {
    val userService = UserService()
    val authenticationService = AuthenticationService(userService)
    val bettingService = BetWebSettings(userService).createBettingService()

    val userEndpoints = UserEndpoints(userService)
    val authEndpoints = AuthenticationEndpoints(authenticationService)
    val bettingEndpoints = BettingEndpoints(authenticationEnabled, userService, bettingService)

    val server = Javalin
        .create { config ->
            config.showJavalinBanner = false
        }
        .post("/api/users/create", userEndpoints.register())
        .post("/api/auth/login", authEndpoints.authenticate())
        .post("/api/bets/create", bettingEndpoints.createBet())
        .post("/api/bets/place", bettingEndpoints.placeBet())
        .get("/api/bets/get", bettingEndpoints.getBets())

    return BookmakerApplication(
        userService = userService,
        authenticationService = authenticationService,
        bettingService = bettingService,
        server = server
    )
}
