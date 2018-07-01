package com.example.demo

import org.springframework.social.RateLimitExceededException
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

class UserNameAlreadyInUseException: Exception() {

    fun UserNameAlreadyInUseException(username: String): String {
        return "The username '$username'is already in use."
    }

}

@ControllerAdvice
class ExceptionHandlingControllerAdvice {

    @ExceptionHandler(RateLimitExceededException::class)
    fun rateLimitExceeded(e: RateLimitExceededException, model: Model): String {
        model["providerId"] = e.providerId
        return "ratelimit"
    }

}