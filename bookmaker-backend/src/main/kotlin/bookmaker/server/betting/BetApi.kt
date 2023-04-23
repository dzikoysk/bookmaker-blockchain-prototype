package bookmaker.server.betting

import java.util.UUID

data class Bet(
    val id: UUID,
    val ownerId: UUID,
    val title: String,
    val description: String
)