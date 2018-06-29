package com.example.demo.services.social.media

import com.example.demo.jpa.User
import org.springframework.social.connect.web.SignInAdapter
import org.springframework.web.context.request.NativeWebRequest
import javax.servlet.http.HttpServletResponse


// Signs the user in by setting the currentUser property with SecurityContext
class SimpleSignInAdapter : SignInAdapter {

    private lateinit var userCookieGenerator: UserCookieGenerator

    override fun signIn(userId: String?, connection: org.springframework.social.connect.Connection<*>?, request: NativeWebRequest?): String {
        SecurityContext().setCurrentUser(User(userId!!))
        val requestCheck = request!!.getNativeResponse(HttpServletResponse::class.java)
        if (requestCheck != null) {
            userCookieGenerator.addCookie(userId, requestCheck)
        }
        return ""
    }

}