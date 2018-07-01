package com.example.demo.services.social

import org.springframework.social.ExpiredAuthorizationException
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class TwitterRevokedToken {

    @RequestMapping("/twitter/revoked")
    fun simulateExpiredToken() {
        throw ExpiredAuthorizationException("twitter")
    }

}