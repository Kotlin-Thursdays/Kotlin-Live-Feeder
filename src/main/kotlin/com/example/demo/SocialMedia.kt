package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.filter.CompositeFilter
import java.security.Principal
import javax.servlet.Filter

@SpringBootApplication
@EnableOAuth2Client
@RestController
class SocialMedia: WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var oauth2ClientContext: OAuth2ClientContext

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**", "/webjars/**", "/error**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter::class.java)
    }

    private fun ssoFilter(): Filter {
        val filter = CompositeFilter()
        val filters = ArrayList<Filter>()
        filters.add(ssoFilter(github(), "/login/github"))
        filters.add(ssoFilter(twitter(), "/"))
        filter.setFilters(filters)
        return filter
    }

    private fun ssoFilter(client: ClientResources, path: String): Filter {
        val filter = OAuth2ClientAuthenticationProcessingFilter(path)
        val template = OAuth2RestTemplate(client.client, oauth2ClientContext)
        filter.setRestTemplate(template)
        val tokenServices = UserInfoTokenServices(
                client.resource.userInfoUri, client.client.clientId)
        tokenServices.setRestTemplate(template)
        filter.setTokenServices(tokenServices)
        return filter
    }

    @Bean
    @ConfigurationProperties("github")
    fun github(): ClientResources {
        return ClientResources()
    }

    @Bean
    @ConfigurationProperties("twitter")
    fun twitter(): ClientResources {
        return ClientResources()
    }


    @Bean
    @ConfigurationProperties("github.client")
    fun githubAuth(): AuthorizationCodeResourceDetails {
        return AuthorizationCodeResourceDetails()
    }

    @Bean
    @ConfigurationProperties("github.resource")
    fun githubResource(): ResourceServerProperties {
        return ResourceServerProperties()
    }

    @Bean
    @ConfigurationProperties("twitter.client")
    fun twitterAuth(): AuthorizationCodeResourceDetails {
        return AuthorizationCodeResourceDetails()
    }

    @Bean
    @ConfigurationProperties("twitter.resource")
    fun twitterREsource(): ResourceServerProperties {
        return ResourceServerProperties()
    }

    @Bean
    fun oauth2ClientFilterRegistration(filter: OAuth2ClientContextFilter): FilterRegistrationBean<Filter> {
        val registration = FilterRegistrationBean<Filter>()
        registration.filter = filter
        registration.order = -100
        return registration
    }

    // protects user info in the endpoint after login
    @RequestMapping("/user", "/me")
    fun user(principal: Principal): Map<String, String> {
        val map = LinkedHashMap<String, String>()
        map["name"] = principal.name
        return map
    }
}