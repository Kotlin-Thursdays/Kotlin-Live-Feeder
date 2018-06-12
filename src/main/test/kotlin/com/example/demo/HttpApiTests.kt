package com.example.demo

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.sun.glass.ui.Application
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

/* TODO: 1) Write notes on Mockito, differences between server versus servlet
 * TODO: 2) Use draw.io to draw a basic application. Split into 2 parts
 * TODO: 3) Cut off project for this week at card creation
 * TODO: 4) Part 2: websockets, API grabbing
 */

/*
@ExtendWith(SpringExtension::class)
class HttpApiTests(@Autowired val mockMvc: MockMvc) {

    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var cardRepository: CardRepository

    @MockBean
    private lateinit var markdownConverter: MarkdownConverter

    @Test
    fun `List cards`() {
        val juergen = User("springjuergen", "Juergen", "Hoeller")
        val spring5Card = Card("Spring Framework 5.0 goes GA", "Dear Spring community ...", "Lorem ipsum", juergen, 1)
        val spring43Card = Card("Spring Framework 4.3 goes GA", "Dear Spring community ...", "Lorem ipsum", juergen, 2)
        whenever(cardRepository.findAllByOrderByAddedAtDesc()).thenReturn(listOf(spring5Card, spring43Card))
        whenever(markdownConverter.invoke(any())).thenAnswer { it.arguments[0] }
        mockMvc.perform(get("/api/card/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("\$.[0].author.login").value(juergen.login))
                .andExpect(jsonPath("\$.[0].id").value(spring5Card.id!!))
                .andExpect(jsonPath("\$.[1].author.login").value(juergen.login))
                .andExpect(jsonPath("\$.[1].id").value(spring43Card.id!!))
    }

    @Test
    fun `List users`() {
        val juergen = User("springjuergen", "Juergen", "Hoeller")
        val smaldini = User("smaldini", "Stéphane", "Maldini")
        whenever(userRepository.findAll()).thenReturn(listOf(juergen, smaldini))
        mockMvc.perform(get("/api/user/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("\$.[0].login").value(juergen.login))
                .andExpect(jsonPath("\$.[1].login").value(smaldini.login))
    }

}*/