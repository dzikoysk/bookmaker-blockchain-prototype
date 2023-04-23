@file:Suppress("RedundantSamConstructor")

package bookmaker.simulations

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

    simulation(
        SimulationContext(
            logging = false,
            userService = bookmakerApplication.userService,
            bettingService = bookmakerApplication.bettingService
        )
    ) {
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
            Runnable {
                logger.info("$operations operacji na sekundkę")
                operations = 0
            },
            1, // initialDelay in seconds
            1, // delay in seconds
            SECONDS // time unit
        )

        // Place 100M bets
        repeat(Int.MAX_VALUE) { id ->
            // Create a player and place a bet
            val player1 = generatePlayer(id)
            addMoney(player1, 10.00)
            placeBet(player1, betId, "G2", 10.00)
            operations += 1
        }
    }
}

/*

Results:

02:36:18.241 [pool-1-thread-1] INFO  Performance 230866 operations per second
02:36:19.246 [pool-1-thread-1] INFO  Performance 255278 operations per second
02:36:20.259 [pool-1-thread-1] INFO  Performance 224388 operations per second
02:36:21.266 [pool-1-thread-1] INFO  Performance 247823 operations per second
02:36:22.274 [pool-1-thread-1] INFO  Performance 241162 operations per second
02:36:23.286 [pool-1-thread-1] INFO  Performance 246309 operations per second
02:36:24.294 [pool-1-thread-1] INFO  Performance 197203 operations per second
02:36:25.302 [pool-1-thread-1] INFO  Performance 256848 operations per second
02:36:26.304 [pool-1-thread-1] INFO  Performance 248892 operations per second
02:36:27.314 [pool-1-thread-1] INFO  Performance 236264 operations per second
02:36:28.322 [pool-1-thread-1] INFO  Performance 245659 operations per second
02:36:29.334 [pool-1-thread-1] INFO  Performance 231174 operations per second
02:36:30.346 [pool-1-thread-1] INFO  Performance 156496 operations per second
02:36:31.356 [pool-1-thread-1] INFO  Performance 154099 operations per second
02:36:32.370 [pool-1-thread-1] INFO  Performance 218399 operations per second
02:36:33.384 [pool-1-thread-1] INFO  Performance 261929 operations per second
02:36:34.397 [pool-1-thread-1] INFO  Performance 229974 operations per second
02:36:35.410 [pool-1-thread-1] INFO  Performance 230415 operations per second
02:36:36.424 [pool-1-thread-1] INFO  Performance 189875 operations per second
02:36:37.427 [pool-1-thread-1] INFO  Performance 195064 operations per second
02:36:38.442 [pool-1-thread-1] INFO  Performance 293813 operations per second
02:36:39.454 [pool-1-thread-1] INFO  Performance 278348 operations per second
02:36:40.563 [pool-1-thread-1] INFO  Performance 233170 operations per second
02:36:41.571 [pool-1-thread-1] INFO  Performance 231571 operations per second
02:36:42.585 [pool-1-thread-1] INFO  Performance 230301 operations per second
02:36:43.601 [pool-1-thread-1] INFO  Performance 248047 operations per second
02:36:44.612 [pool-1-thread-1] INFO  Performance 150112 operations per second
02:36:46.239 [pool-1-thread-1] INFO  Performance 222775 operations per second
02:36:47.642 [pool-1-thread-1] INFO  Performance 247436 operations per second
02:36:48.652 [pool-1-thread-1] INFO  Performance 230614 operations per second

 */