package com.example.demo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests(@Autowired val restTemplate : TestRestTemplate) {

    @BeforeAll
    fun setup() {
        println(">> Setup")
    }

    /* use real sentences between backticks instead of camel-case
     * to provide expressive test function names
     */
    @Test
    fun `Assert application page title, content and status code`() {
        val entity = restTemplate.getForEntity<String>("/")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("<h1 class='app-title'> Kotlin Stream </h1>")
    }

    @Test
    fun `Assert page title, content and status`() {
        println(">> Assert card page title, content, and status code")
        val entity = restTemplate.getForEntity<String>("/card/2")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("Reactor Aluminium has landed",
                "<a href=\"https://projectreactor.io/\">https://projectreactor.io/</a>")
    }

    @AfterAll
    fun teardown() {
        println(">> Tear down")
    }

}