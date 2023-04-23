package bookmaker.server.auth

import io.javalin.http.Context
import io.javalin.http.UnauthorizedResponse
import bookmaker.server.user.User
import bookmaker.server.user.UserService
import java.util.UUID

abstract class AbstractAuthenticatedEndpoints(private val userService: UserService) {

    fun <R> Context.loggedUser(body: (User) -> R) {
        val userId = sessionAttribute<UUID>("userId") ?: throw UnauthorizedResponse("Client is not logged in")
        val user = userService.findUserById(userId) ?: throw UnauthorizedResponse("Cannot find user with requested id")
        body.invoke(user)
    }

}