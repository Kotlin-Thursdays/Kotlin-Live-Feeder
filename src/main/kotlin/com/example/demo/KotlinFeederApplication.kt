package com.example.demo

import com.example.demo.jpa.Card
import com.example.demo.jpa.CardRepository
import com.example.demo.jpa.User
import com.example.demo.jpa.UserRepository
import com.samskivert.mustache.Mustache
import org.springframework.boot.Banner
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.stereotype.Controller


/*
 * In Spring, the objects that form the backbone of applications are managed
 * with beans. A "bean" is an object that is instantiated, assembled, and otherwise
 * managed by an IoC container.  Beans, and the dependencies among them, are
 * reflected in the configuration metadata used by a container.
 */

/*
 * WebSecurityConfigurer uses the annotation to configure the
 * security filter chain that carries the OAuth2 authentication
 * processor
 */

@Controller
@SpringBootApplication
@EnableOAuth2Sso
class KotlinFeederApplication: WebSecurityConfigurerAdapter() {

    @Bean
    fun mustacheCompilier(loader: Mustache.TemplateLoader?) =
            Mustache.compiler().escapeHTML(false).withLoader(loader)

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.build()
    }

    @Bean
    fun databaseInitializer(userRepository: UserRepository,
                            cardRepository: CardRepository) = CommandLineRunner {
        val smaldini = User("smaldini", "Stéphane", "Maldini")
        userRepository.save(smaldini)

        cardRepository.save(Card(
                "Reactor Bismuth is out",
                "Lorem ipsum",
                "dolor **sit** amet https://projectreactor.io/",
                smaldini,
                1
        ))
        cardRepository.save(Card(
                "Reactor Aluminium has landed",
                "Lorem ipsum",
                "dolor **sit** amet https://projectreactor.io/",
                smaldini,
                2
        ))
    }
}

fun main(args: Array<String>) {
    runApplication<KotlinFeederApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}
