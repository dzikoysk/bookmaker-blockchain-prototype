package bookmaker.tests.specification

import com.github.javafaker.Faker
import io.javalin.testtools.HttpClient
import kong.unirest.Unirest
import kong.unirest.UnirestInstance
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import bookmaker.server.auth.AuthenticationService.AuthenticationRequest
import bookmaker.server.createBookmakerApplication
import bookmaker.server.user.UserService.RegistrationRequest
import java.util.Locale

abstract class BookmakerTestSpecification {

    val application = createBookmakerApplication()
    val fakeDataGenerator = Faker(Locale("pl"))
    val httpClient: UnirestInstance = Unirest.spawnInstance()

    @AfterEach
    fun cleanup() {
        httpClient.close()
    }

    fun createUser(registrationRequest: RegistrationRequest): Pair<String, String> {
        val userDto = application.userService.register(registrationRequest)
        return userDto.username to registrationRequest.password
    }

    fun HttpClient.authenticateHttpClientAs(username: String, password: String) {
        httpClient.authenticateHttpClientAs(origin, username, password)
    }

}

fun UnirestInstance.authenticateHttpClientAs(origin: String, username: String, password: String) {
    val response = post("${origin}/api/auth/login")
        .body(AuthenticationRequest(username, password))
        .asEmpty()
    assertThat(response.status).isEqualTo(200)
}
