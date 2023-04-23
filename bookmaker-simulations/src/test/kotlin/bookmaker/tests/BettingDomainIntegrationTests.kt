package bookmaker.tests

import io.javalin.testtools.JavalinTest
import io.javalin.testtools.TestConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import bookmaker.server.user.Permission.BET
import bookmaker.server.user.Permission.CREATE_BET
import bookmaker.server.betting.BettingEndpoints.CreateBetRequestDto
import bookmaker.server.betting.BettingEndpoints.PlaceBetDto
import bookmaker.server.user.Transfer
import bookmaker.server.user.UserService.RegistrationRequest
import bookmaker.tests.specification.BookmakerTestSpecification
import java.time.LocalDateTime
import java.util.UUID

class BettingDomainIntegrationTests : BookmakerTestSpecification() {

    @Test
    fun `should create and place bet`() = JavalinTest.test(application.server, TestConfig(captureLogs = false)) { _, client ->
        val (managerUsername, managerPassword) = createUser(
            RegistrationRequest(
                username = "manager",
                password = "manager-password",
                name = "Ludwik",
                surname = "Narbutt",
                permissions = listOf(CREATE_BET)
            )
        )

        client.authenticateHttpClientAs(
            username = managerUsername,
            password = managerPassword
        )

        val createResponse = httpClient.post("${client.origin}/api/bets/create")
            .body(
                CreateBetRequestDto(
                    title = "Testowy zakład",
                    description = "Zakład rozpoczyna się 31.01.2023 o godzinie 12 i kończy tego samego dnia o godzinie 20:00",
                    startDate = LocalDateTime.of(2023, 1, 31, 12, 0),
                    endDate = LocalDateTime.of(2023, 1, 31, 20, 0),
                    bettingOptions = listOf("FNC", "G2")
                )
            )
            .asString()

        assertThat(createResponse.status).isEqualTo(200)
        val betId = assertDoesNotThrow { UUID.fromString(createResponse.body) }

        val (clientUsername, clientPassword) = createUser(
            RegistrationRequest(
                username = "client",
                password = "client-password",
                name = "Simon",
                surname = "Yazgara",
                permissions = listOf(BET)
            )
        )

        client.authenticateHttpClientAs(
            username = clientUsername,
            password = clientPassword
        )
        application.userService.findUserByName("client")!!.wallet.history.add(Transfer(LocalDateTime.now(), "Testowy depozyt", 100.00))

        val betResponse = httpClient.post("${client.origin}/api/bets/place")
            .body(
                PlaceBetDto(
                    betId = betId,
                    bettingOption = "FNC",
                    value = 10.00
                )
            )
            .asString()

        println("Block hash: ${betResponse.body}")
        assertThat(betResponse.body).isNotNull
        assertThat(betResponse.status).isEqualTo(200)
    }

}