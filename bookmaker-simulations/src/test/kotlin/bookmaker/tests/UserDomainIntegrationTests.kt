package bookmaker.tests

import io.javalin.testtools.JavalinTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import bookmaker.server.user.UserDto
import bookmaker.server.user.UserService.RegistrationRequest
import bookmaker.tests.specification.BookmakerTestSpecification

class UserDomainIntegrationTests : BookmakerTestSpecification() {

    @Test
    fun `should register users`() = JavalinTest.test(application.server) { _, client ->
        (0..100)
            .map {
                val username = fakeDataGenerator.name().username()

                val response = httpClient.post("${client.origin}/api/users/create")
                    .body(
                        RegistrationRequest(
                            username = username,
                            name = fakeDataGenerator.name().firstName(),
                            surname = fakeDataGenerator.name().firstName(),
                            password = fakeDataGenerator.random().hex(16)
                        )
                    )
                    .asObject(UserDto::class.java)

                assertThat(response.status).isEqualTo(200)
                assertThat(response.body.username).isEqualTo(username)
                response.body
            }
            .forEach { user ->
                assertThat(application.userService.findUserById(user.id)?.username).isEqualTo(user.username)
            }
    }

}