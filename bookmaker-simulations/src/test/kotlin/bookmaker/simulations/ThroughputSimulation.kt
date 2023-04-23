package bookmaker.simulations

import bookmaker.server.betting.BettingService.CreateBetRequest
import bookmaker.simulations.specification.simulation
import java.time.LocalDateTime

fun main() = simulation {
    // Create a bet owner
    val betOwner = createPlatformManager()

    // Create a bet
    val betId = bettingService.createBet(
        CreateBetRequest(
            ownerId = betOwner.id,
            title = "FNC vs G2",
            description = "Mecz pomiędzy Fnatic and G2 Esports",
            startDate = LocalDateTime.now().minusSeconds(30),
            endDate = LocalDateTime.now().plusSeconds(30),
            bettingOptions = listOf("FNC", "G2")
        )
    )
    logger.info("Stworzono zakład o id $betId")

    val playersFrom0to500000 = (0..500_000).map {
        // Create player (N)
        val player = generatePlayer(it)
        // Add money to player
        addMoney(player, 10.00)
        // Place bet
        placeBet(player, betId, "FNC", 10.00)
        // Return player
        player
    }

    // Create player (N+1) and place bet on the opposite option
    val player5000001 = generatePlayer(5000001)
    addMoney(player5000001, 10.00)
    placeBet(player5000001, betId, "G2", 10.00)

    // Announce the result
    val result = bettingService.announceResult(betId, "G2")
    logger.info("Zakład został zakończony z wynikiem G2")

    // Print results
    logger.info("Finalny kurs: ${result.smartContractResult}")
    logger.info("${player5000001.username} stan konta: ${player5000001.wallet.getBalance()} (obstawiona opcja: G2)")
    logger.info("Stan konta wszystkich innych graczy równy 0: ${playersFrom0to500000.all { it.wallet.getBalance() == 0.0 }}")
}

/*

Result:

00:05:03.600 [main] INFO  Stworzono zakład o id 82784176-e7b1-40ed-bc1f-48b7556550eb
00:05:03.607 [main] INFO  Stworzono gracza player-0 o id ee0dce73-90ca-304a-a70b-249b9709718e
00:05:03.609 [main] INFO  Zasilono portfel gracza player-0 kwotą 10.0 PLN
00:05:03.648 [main] INFO  Gracz player-0 postawił 10.0 PLN na opcję FNC
00:05:03.648 [main] INFO  Stworzono gracza player-1 o id 76c9edca-7e5a-3f86-b6b9-c90bbbb6369d
00:05:03.649 [main] INFO  Zasilono portfel gracza player-1 kwotą 10.0 PLN
00:05:03.649 [main] INFO  Gracz player-1 postawił 10.0 PLN na opcję FNC

Players 2 - 499 999

00:04:42.413 [main] INFO  Stworzono gracza player-500000 o id 80f647d4-c39a-31b1-ae54-661906f0005a
00:04:42.413 [main] INFO  Zasilono portfel gracza player-500000 kwotą 10.0 PLN
00:04:42.413 [main] INFO  Gracz player-500000 postawił 10.0 PLN na opcję FNC
00:04:42.413 [main] INFO  Stworzono gracza player-5000001 o id 073bda50-b706-3e0b-8466-442b86fb8491
00:04:42.413 [main] INFO  Zasilono portfel gracza player-5000001 kwotą 10.0 PLN
00:04:42.413 [main] INFO  Gracz player-5000001 postawił 10.0 PLN na opcję G2
00:04:44.844 [main] INFO  Zakład został zakończony z wynikiem G2
00:04:44.844 [main] INFO  Finalny kurs: 500002.0
00:04:44.844 [main] INFO  player-5000001 stan konta: 5000020.0 (obstawiona opcja: G2)
00:04:44.885 [main] INFO  Stan konta wszystkich innych graczy równy 0: true

Stworzono zakład o id 82784176-e7b1-40ed-bc1f-48b7556550eb
Stworzono gracza player-0 o id ee0dce73-90ca-304a-a70b-249b9709718e
Zasilono portfel gracza player-0 kwotą 10.0 PLN
Gracz player-0 postawił 10.0 PLN na opcję FNC
Stworzono gracza player-1 o id 76c9edca-7e5a-3f86-b6b9-c90bbbb6369d
Zasilono portfel gracza player-1 kwotą 10.0 PLN
Gracz player-1 postawił 10.0 PLN na opcję FNC
--- Players 2 - 499 999
Stworzono gracza player-500000 o id 80f647d4-c39a-31b1-ae54-661906f0005a
Zasilono portfel gracza player-500000 kwotą 10.0 PLN
Gracz player-500000 postawił 10.0 PLN na opcję FNC
Stworzono gracza player-5000001 o id 073bda50-b706-3e0b-8466-442b86fb8491
Zasilono portfel gracza player-5000001 kwotą 10.0 PLN
Gracz player-5000001 postawił 10.0 PLN na opcję G2
Zakład został zakończony z wynikiem G2
Finalny kurs: 500002.0
player-5000001 stan konta: 5000020.0 (obstawiona opcja: G2)
Stan konta wszystkich innych graczy równy 0: true

 */