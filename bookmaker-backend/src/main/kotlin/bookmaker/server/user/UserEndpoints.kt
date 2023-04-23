package bookmaker.server.user

import io.javalin.http.Handler
import io.javalin.http.bodyAsClass
import bookmaker.server.user.Permission.BET
import bookmaker.server.user.UserService.RegistrationRequest

class UserEndpoints(private val userService: UserService) {

    fun register() = Handler { ctx ->
        ctx.bodyAsClass<RegistrationRequest>()
            .copy(permissions = listOf(BET)) // verify account
            .let { userService.register(it) }
            .let { ctx.json(it.toDto()) }
    }

}