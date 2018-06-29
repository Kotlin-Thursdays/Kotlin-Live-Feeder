package com.example.demo.services.social.media

import org.springframework.web.util.CookieGenerator
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UserCookieGenerator {

    private val userCookieGenerator = CookieGenerator()

    init {
        userCookieGenerator.cookieName = "quickstart_user"
    }

    fun addCookie(userId: String, response: HttpServletResponse) {
        userCookieGenerator.addCookie(response, userId)
    }

    fun removeCookie(response: HttpServletResponse) {
        userCookieGenerator.addCookie(response, "")
    }

    fun readCookieValue(request: HttpServletRequest): String {
        var result = ""
        val cookies: Array<Cookie> = request.cookies

        cookies.forEach {
            if (it.name == userCookieGenerator.cookieName) {
                result = it.value
            }

        }
        return result
    }
}