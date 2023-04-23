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

    // Create players
    val player1 = generatePlayer(1)
    val player2 = generatePlayer(2)
    val player3 = generatePlayer(3)

    // Add money to players
    addMoney(player1, 1100.00)
    addMoney(player2, 500.00)
    addMoney(player3, 100.00)

    // Place bets
    placeBet(player1, betId, "FNC", 1100.00)
    placeBet(player2, betId, "G2", 500.00)
    placeBet(player3, betId, "G2", 100.00)

    // Announce the result
    val result = bettingService.announceResult(betId, "G2")
    logger.info("Zakład został zakończony z wynikiem G2")
    logger.info("Finalny kurs: ${result.smartContractResult}")
    logger.info("${player1.username} stan konta: ${player1.wallet.getBalance()} (obstawiona opcja: FNC)")
    logger.info("${player2.username} stan konta: ${player2.wallet.getBalance()} (obstawiona opcja: G2)")
    logger.info("${player3.username} stan konta: ${player3.wallet.getBalance()} (obstawiona opcja: G2)")
}

/*

Result:

00:04:10.184 [main] INFO  Stworzono zakład o id 5085afc1-0ee6-479e-b1a7-f9fc72cbcce8
00:04:10.188 [main] INFO  Stworzono gracza player-1 o id 76c9edca-7e5a-3f86-b6b9-c90bbbb6369d
00:04:10.189 [main] INFO  Stworzono gracza player-2 o id 64b5b441-7d73-3142-9707-75e1cc4141d9
00:04:10.189 [main] INFO  Stworzono gracza player-3 o id f79dcab6-61ea-3b66-9565-585089d9855c
00:04:10.190 [main] INFO  Zasilono portfel gracza player-1 kwotą 1100.0 PLN
00:04:10.191 [main] INFO  Zasilono portfel gracza player-2 kwotą 500.0 PLN
00:04:10.191 [main] INFO  Zasilono portfel gracza player-3 kwotą 100.0 PLN
00:04:10.238 [main] INFO  Gracz player-1 postawił 1100.0 PLN na opcję FNC
00:04:10.238 [main] INFO  Gracz player-2 postawił 500.0 PLN na opcję G2
00:04:10.239 [main] INFO  Gracz player-3 postawił 100.0 PLN na opcję G2
00:04:10.340 [main] INFO  Zakład został zakończony z wynikiem G2
00:04:10.340 [main] INFO  Finalny kurs: 2.833333333333333
00:04:10.340 [main] INFO  player-1 stan konta: 0.0 (obstawiona opcja: FNC)
00:04:10.340 [main] INFO  player-2 stan konta: 1416.6666666666665 (obstawiona opcja: G2)
00:04:10.340 [main] INFO  player-3 stan konta: 283.3333333333333 (obstawiona opcja: G2)

 */