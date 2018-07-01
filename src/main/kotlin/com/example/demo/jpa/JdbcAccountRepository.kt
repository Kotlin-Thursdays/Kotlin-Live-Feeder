package com.example.demo.jpa

import com.example.demo.UserNameAlreadyInUseException
import org.springframework.dao.DuplicateKeyException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import javax.transaction.Transactional

@Repository
class JdbcAccountRepository(private val jdbcTemplate: JdbcTemplate,
                            private val passwordEncoder: PasswordEncoder): AccountRepository  {

    @Transactional
    override fun createAccount(account: Account) {
        try {
            jdbcTemplate
        } catch(e: DuplicateKeyException) {
            throw e
        }
    }

    @Throws(UserNameAlreadyInUseException::class)
    override fun findAccountByUsername(username: String): Account {
        return jdbcTemplate.queryForObject("SELECT username, firstName, lastName where username = ?",
                RowMapper<Account> { resultSet: ResultSet, i: Int ->
                    Account(resultSet.getString(username), null,
                            resultSet.getString("firstname"), resultSet.getString("lastname"))
                }, username)!!
    }

}