package com.example.demo.services.social.media

import org.springframework.context.annotation.*
import org.springframework.core.env.Environment
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.encrypt.Encryptors
import org.springframework.security.crypto.encrypt.TextEncryptor
import javax.inject.Inject
import org.springframework.social.twitter.connect.TwitterConnectionFactory
import org.springframework.social.connect.support.ConnectionFactoryRegistry
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.github.connect.GitHubConnectionFactory
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository
import org.springframework.social.connect.UsersConnectionRepository
import javax.sql.DataSource
import org.springframework.social.connect.ConnectionRepository
import org.springframework.social.connect.web.ConnectController

@Configuration
@EnableSocial
class SocialConfig: SocialConfigurer {

    @Inject
    private val environment: Environment? = null

    @Bean
    fun connectionFactoryLocator(): ConnectionFactoryLocator {
        val registry = ConnectionFactoryRegistry()

        registry.addConnectionFactory(GitHubConnectionFactory(
                environment!!.getProperty("github.clientId"),
                environment!!.getProperty("github.clientSecret")))

        registry.addConnectionFactory(TwitterConnectionFactory(
                environment!!.getProperty("twitter.clientId"),
                environment!!.getProperty("twitter.clientSecret")))

        return registry
    }

    @Bean
    @Scope(value="request", proxyMode= ScopedProxyMode.INTERFACES)
    fun connectionRepository(): ConnectionRepository {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication ?: throw IllegalStateException("Unable to get a ConnectionRepository: no user signed in")
        return usersConnectionRepository().createConnectionRepository(authentication.name)
}

    @Bean
    fun usersConnectionRepository(): UsersConnectionRepository {
        return JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator(),
                textEncryptor)
    }

    @Inject
    private val dataSource: DataSource? = null

    @Inject
    private val textEncryptor: TextEncryptor? = null

    @Configuration
    @Profile("prod")
    internal class Prod {

        @Inject
        private val environment: Environment? = null

        @Bean
        fun textEncryptor(): TextEncryptor {
            return Encryptors.queryableText(environment!!.getProperty("security.encryptPassword"),
                    environment.getProperty("security.encryptSalt"))
        }

    }

    @Bean
    fun connectController(): ConnectController {
        return ConnectController(connectionFactoryLocator(),
                connectionRepository())
    }

}