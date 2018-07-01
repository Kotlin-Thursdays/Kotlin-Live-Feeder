package com.example.demo.signin

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

class SignInUtils {
    // automatic sign in
    fun signin(userId: String) {
        SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken(userId, null, null)
    }
}