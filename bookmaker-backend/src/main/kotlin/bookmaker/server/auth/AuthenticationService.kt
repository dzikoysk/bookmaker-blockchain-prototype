package bookmaker.server.auth

import com.google.common.hash.Hashing
import bookmaker.server.user.UserDto
import bookmaker.server.user.UserService
import java.nio.charset.StandardCharsets.UTF_8

class AuthenticationService(
    private val userService: UserService
) {

    data class AuthenticationRequest(
        val username: String,
        val password: String
    )

    @Suppress("UnstableApiUsage")
    fun authenticate(authenticationRequest: AuthenticationRequest): UserDto? =
        userService.findUserByName(authenticationRequest.username)
            ?.takeIf { it.password == authenticationRequest.password.toSha256() }
            ?.toDto()

}

@Suppress("UnstableApiUsage")
fun String.toSha256(): String =
    Hashing.sha256().hashString(this, UTF_8).toString()