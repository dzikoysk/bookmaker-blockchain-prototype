package bookmaker.server.user

import io.javalin.http.HttpResponseException
import io.javalin.http.HttpStatus.BAD_REQUEST
import bookmaker.server.auth.toSha256
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

class UserService {

    private val users = ConcurrentHashMap<UUID, User>()
    // cache for faster lookup
    private val usersByName = mutableMapOf<String, User>()

    data class RegistrationRequest(
        val username: String,
        val password: String,
        val name: String,
        val surname: String,
        val permissions: List<Permission> = emptyList()
    )

    class UsernameAlreadyExistsError : HttpResponseException(BAD_REQUEST, "Username already taken")

    @Suppress("UnstableApiUsage")
    fun register(registrationRequest: RegistrationRequest): User {
        if (usersByName.containsKey(registrationRequest.username.lowercase())) {
            throw UsernameAlreadyExistsError()
        }

        val user = with (registrationRequest) {
            User(
                id = UUID.nameUUIDFromBytes(username.toByteArray()),
                username = username,
                password = password.toSha256(),
                name = name,
                surname = surname,
                verified = true,
                permissions = permissions,
                wallet = Wallet()
            )
        }

        users[user.id] = user
        usersByName[user.username.lowercase()] = user
        return user
    }

    fun findUserByName(name: String): User? =
        usersByName[name.lowercase()]

    fun findUserById(id: UUID): User? =
        users[id]

}