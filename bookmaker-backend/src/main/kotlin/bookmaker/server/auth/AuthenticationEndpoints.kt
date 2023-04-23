package bookmaker.server.auth

import io.javalin.http.Handler
import io.javalin.http.UnauthorizedResponse
import io.javalin.http.bodyAsClass
import bookmaker.server.auth.AuthenticationService.AuthenticationRequest

class AuthenticationEndpoints(private  val authenticationService: AuthenticationService) {


    fun authenticate() = Handler { ctx ->
        ctx.bodyAsClass<AuthenticationRequest>()
            .let { authenticationService.authenticate(it) }
            ?.let {
                ctx.sessionAttribute("userId", it.id)
                ctx.json(it)
            }
            ?: throw UnauthorizedResponse("Invalid credentials")
    }

}