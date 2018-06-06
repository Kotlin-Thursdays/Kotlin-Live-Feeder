package com.example.demo

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.web.bind.annotation.*
import java.security.Principal

/* ------------------------ Spring Web Flux ---------------------------
 * @RestController is a composed annotation through AOP, composed of a
 * @Controller for routing and a @ResponseBody that renders an HTML template.
 *
 * @RequestMapping are HTTP methods that may include
 *  - @GetMapping
 *  - @PostMapping
 *  - @PutMapping
 *  - @DeleteMapping
 *  - @PatchMapping
 */

@RestController
@RequestMapping("/api/card")
class CardController(private val repository: CardRepository,
                        private val markdownConverter: MarkdownConverter) {

    @GetMapping("/")
    fun findAll() {
        repository.findAllByOrderByAddedAtDesc()
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Long, @RequestParam converter: String?) {
        when (converter) {
            "markdown" -> repository.findById(id).map {
                it.copy(
                        headline = markdownConverter.invoke(it.headline),
                        content = markdownConverter.invoke(it.content)
                )
            }
            null -> repository.findById(id)
            else -> throw IllegalArgumentException("Only markdown converter is supported.")
        }
    }

}

@RestController
@RequestMapping("/api/user")
class UserController(private val repository: UserRepository) {

    @GetMapping("/")
    fun findAll() {
        repository.findAll()
    }

    @GetMapping("/{login}")
    fun findOne(@PathVariable login: String) {
        repository.findById(login)
    }
}