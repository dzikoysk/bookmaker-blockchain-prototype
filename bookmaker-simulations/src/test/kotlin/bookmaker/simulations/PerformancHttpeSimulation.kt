package bookmaker.simulations

import kong.unirest.Unirest
import bookmaker.server.betting.BettingEndpoints.PlaceBetDto
import bookmaker.server.betting.BettingService.CreateBetRequest
import bookmaker.server.createBookmakerApplication
import bookmaker.simulations.specification.SimulationContext
import bookmaker.simulations.specification.simulation
import java.time.LocalDateTime
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit.SECONDS

fun main() {
    val bookmakerApplication = createBookmakerApplication(authenticationEnabled = false)
    bookmakerApplication.server.start(8080)

    simulation(SimulationContext(logging = false, userService = bookmakerApplication.userService, bettingService = bookmakerApplication.bettingService)) {
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

        // Metrics
        var operations = 0
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(
            {
                logger.info("Performance $operations operations per second")
                operations = 0
            },
            1,
            1,
            SECONDS
        )

        val executor = Executors.newFixedThreadPool(8)

        // Place 100M bets
        repeat(100_000_000) { id ->
            // Create a player and place a bet
            val player1 = generatePlayer(id)
            addMoney(player1, 10.00)

            executor.execute {
                val betResponse = Unirest.post("http://localhost:8080/api/bets/place")
                    .body(
                        PlaceBetDto(
                            userId = player1.id,
                            betId = betId,
                            bettingOption = "G2",
                            value = 10.00
                        )
                    )
                    .asString()
                require(betResponse.status == 200)
                operations += 1
            }
        }
    }
}

/*

Results:

00:00:36.830 [pool-1-thread-1] INFO  Performance 44121 operations per second
00:00:37.844 [pool-1-thread-1] INFO  Performance 51749 operations per second
00:00:38.854 [pool-1-thread-1] INFO  Performance 47332 operations per second
00:00:39.866 [pool-1-thread-1] INFO  Performance 54408 operations per second
00:00:40.878 [pool-1-thread-1] INFO  Performance 41442 operations per second
00:00:41.892 [pool-1-thread-1] INFO  Performance 48801 operations per second
00:00:42.900 [pool-1-thread-1] INFO  Performance 51870 operations per second
00:00:43.914 [pool-1-thread-1] INFO  Performance 47616 operations per second
00:00:44.929 [pool-1-thread-1] INFO  Performance 43598 operations per second
00:00:45.938 [pool-1-thread-1] INFO  Performance 55559 operations per second
00:00:46.945 [pool-1-thread-1] INFO  Performance 43132 operations per second
00:00:47.972 [pool-1-thread-1] INFO  Performance 55260 operations per second
00:00:49.064 [pool-1-thread-1] INFO  Performance 60959 operations per second
00:00:50.119 [pool-1-thread-1] INFO  Performance 50546 operations per second
00:00:51.128 [pool-1-thread-1] INFO  Performance 44931 operations per second
00:00:52.134 [pool-1-thread-1] INFO  Performance 45275 operations per second
00:00:53.144 [pool-1-thread-1] INFO  Performance 47695 operations per second
00:00:54.211 [pool-1-thread-1] INFO  Performance 31124 operations per second
00:00:55.221 [pool-1-thread-1] INFO  Performance 44774 operations per second
00:00:56.316 [pool-1-thread-1] INFO  Performance 50773 operations per second
00:00:57.324 [pool-1-thread-1] INFO  Performance 46328 operations per second
00:00:58.334 [pool-1-thread-1] INFO  Performance 44464 operations per second
00:00:59.344 [pool-1-thread-1] INFO  Performance 42610 operations per second
00:01:00.349 [pool-1-thread-1] INFO  Performance 43440 operations per second
00:01:01.360 [pool-1-thread-1] INFO  Performance 41476 operations per second
00:01:02.368 [pool-1-thread-1] INFO  Performance 41333 operations per second
00:01:03.422 [pool-1-thread-1] INFO  Performance 21682 operations per second
00:01:04.458 [pool-1-thread-1] INFO  Performance 44801 operations per second
00:01:05.468 [pool-1-thread-1] INFO  Performance 46540 operations per second
00:01:06.490 [pool-1-thread-1] INFO  Performance 33747 operations per second

 */