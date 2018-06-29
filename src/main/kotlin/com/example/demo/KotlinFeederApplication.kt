package com.example.demo

import com.samskivert.mustache.Mustache
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.Banner
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.stereotype.Controller
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import javax.servlet.Filter
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails
import org.springframework.web.filter.CompositeFilter


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
