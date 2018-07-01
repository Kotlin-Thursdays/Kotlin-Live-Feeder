package com.example.demo.config

import org.apache.catalina.core.ApplicationContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.encrypt.Encryptors
import org.springframework.security.crypto.encrypt.TextEncryptor
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.inject.Inject
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class SecurityConfig: WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var context: ApplicationContext

    @Inject
    private lateinit var dataSource: DataSource

    @Autowired
    @Throws(Exception::class)
    fun registerAuthentication(auth: AuthenticationManagerBuilder) {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, true from Account where username = ?")
                .authoritiesByUsernameQuery("select username, 'ROLE_USER' from Account where username = ?")
                .passwordEncoder(passwordEncoder())
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity?) {
        web?.
                ignoring()?.
                        antMatchers("/**/*.css", "/**/*.png", "/**/*.gif", "/**/*.jpg")
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity?) {
        http?.
                formLogin()?.
                        loginPage("/signin")?.
                        loginProcessingUrl("/signin/authenticate")?.
                        defaultSuccessUrl("/connect")?.
                        failureUrl("/signin?param.error=bad_credentials")?.
                and()?.
                        logout()?.
                            logoutUrl("/signout")?.
                            deleteCookies("JSESSIONID")?.
                and()?.
                        authorizeRequests()?.
                            antMatchers("/", "/webjars/**", "/admin/**", "/favicon.ico", "/resources/**", "/auth/**", "/signin/**", "/signup/**", "/disconnect/facebook")?.
                            permitAll()?.
                        antMatchers("/**")?.authenticated()?.
                and()?.
                        rememberMe()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return NoOpPasswordEncoder.getInstance()
    }

    @Bean
    fun textEncryptor(): TextEncryptor {
        return Encryptors.noOpText()
    }

}