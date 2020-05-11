package com.heidelberg.kotlin

import com.heidelberg.kotlin.model.User
import com.heidelberg.kotlin.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.client.ClientHttpRequestInterceptor

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KotlinApplicationTests(@Autowired private val userRepository: UserRepository,
                             @Autowired private val restTemplate: TestRestTemplate) {
    private val logger: Logger = LoggerFactory.getLogger(KotlinApplicationTests::class.java)

    init {
        val interceptor = ClientHttpRequestInterceptor { request, body, execution ->
            request.headers.accept = listOf(MediaType.APPLICATION_JSON)
            execution.execute(request, body)
        }
        restTemplate.restTemplate.interceptors = listOf(interceptor)
    }

    @Test
    fun `Context loads`() {
    }

    @Test
    fun `Method count returns a integer number greater or equal zero`() {
        assertThat(userRepository.count()).isGreaterThanOrEqualTo(0L)
    }

    @Test
    fun `Create user, read and delete it`() {
        val id = userRepository.createUser(User(firstName = "Test", lastName = "Test", id = null))
        var user = userRepository.find(id)
        assertThat(user).isNotNull
        assertThat(user?.firstName).isEqualTo("Test")
        userRepository.deleteUser(id)
        user = userRepository.find(id)
        assertThat(user).isNull()
    }

    @Test
    fun `Read count using REST interface`() {
        val entity = restTemplate.getForEntity<Long>("/users/count")
        assertThat(entity.hasBody()).isTrue()
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        logger.info("""Count: ${entity.body}""")
        assertThat(entity.body!!).isGreaterThanOrEqualTo(0L);
    }

    @Test
    fun `Read list of users using REST interface`() {
        val entity = restTemplate.getForEntity<Array<User>>("/users")
        assertThat(entity.hasBody()).isTrue()
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        for (user in entity.body!!) {
            logger.info("""User: $user""")
        }
    }
}
