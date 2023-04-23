package bookmaker.server.betting

import bookmaker.server.betting.BetRepository.CreateBetInDatabase
import java.util.UUID

interface BetRepository {

    data class CreateBetInDatabase(
        val ownerId: UUID,
        val title: String,
        val description: String,
    )

    fun createBet(createBetRequest: CreateBetInDatabase): Bet

    fun findBetById(id: UUID): Bet?

    fun findAll(): List<Bet>

}

internal class InMemoryBetRepository : BetRepository {

    private val bets = mutableMapOf<UUID, Bet>()

    override fun createBet(createBetRequest: CreateBetInDatabase): Bet {
        val bet = with (createBetRequest) {
            Bet(
                id = UUID.randomUUID(),
                ownerId = ownerId,
                title = title,
                description = description
            )
        }

        bets[bet.id] = bet
        return bet
    }

    override fun findBetById(id: UUID): Bet? =
        bets[id]

    override fun findAll(): List<Bet> =
        bets.values.toList()

}