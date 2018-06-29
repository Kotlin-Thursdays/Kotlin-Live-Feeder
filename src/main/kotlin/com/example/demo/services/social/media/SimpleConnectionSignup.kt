package com.example.demo.services.social.media

import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionSignUp
import java.util.concurrent.atomic.AtomicLong

class SimpleConnectionSignup: ConnectionSignUp {
    private val userIdSequence = AtomicLong()

    override fun execute(connection: Connection<*>?): String {
        return userIdSequence.incrementAndGet().toString()
    }

}