package bookmaker.server.user

import bookmaker.blockchain.PLN
import java.time.LocalDateTime
import java.util.LinkedList
import java.util.UUID

data class User(
    val id: UUID,
    val username: String,
    val password: String,
    val name: String,
    val surname: String,
    val verified: Boolean,
    val permissions: List<Permission>,
    val wallet: Wallet
) {

    fun toDto(): UserDto =
        UserDto(
            id = id,
            username = username,
            name = name,
            surname = surname,
            verified = verified,
            permissions = permissions,
            wallet = wallet.getBalance()
        )

}

data class UserDto(
    val id: UUID,
    val username: String,
    val name: String,
    val surname: String,
    val verified: Boolean,
    val permissions: List<Permission>,
    val wallet: PLN
)

enum class Permission {
    ADMIN,
    HELP_DESK,
    CREATE_BET,
    BET
}

data class Wallet(
    val history: MutableList<Transfer> = LinkedList()
) {
    fun getBalance(): PLN = history.sumOf { it.value }
}

data class Transfer(
    val date: LocalDateTime,
    val title: String,
    val value: PLN
)
