package com.example.demo.services.social

import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionRepository
import org.springframework.social.twitter.api.Twitter
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import java.security.Principal
import javax.inject.Inject

@Controller
class TwitterProfileController @Inject constructor(var connectionRepository: ConnectionRepository){

    @GetMapping("/twitter")
    fun home(currentUser: Principal, model: Model): String {
        val connection: Connection<Twitter> = connectionRepository.findPrimaryConnection(Twitter::class.java)
        if (connection == null) {
            return "redirect:connect/twitter"
        }
        model["profile"] = connection.api.userOperations().userProfile
        return "twitter/profile"
    }

}