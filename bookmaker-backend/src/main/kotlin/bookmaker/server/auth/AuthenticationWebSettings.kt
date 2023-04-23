package bookmaker.server.auth

import bookmaker.server.user.UserService

class AuthenticationWebSettings(
    private val userService: UserService
) {

    fun authenticationFacade(): AuthenticationService =
        AuthenticationService(
            userService = userService
        )

}