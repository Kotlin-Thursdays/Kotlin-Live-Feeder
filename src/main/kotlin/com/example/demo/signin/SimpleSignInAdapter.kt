package com.example.demo.signin

import org.springframework.security.web.WebAttributes
import org.springframework.security.web.savedrequest.RequestCache
import org.springframework.security.web.savedrequest.SavedRequest
import org.springframework.social.connect.Connection
import org.springframework.social.connect.web.SignInAdapter
import org.springframework.web.context.request.NativeWebRequest
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

class SimpleSignInAdapter: SignInAdapter {

    private lateinit var requestCache: RequestCache

    override fun signIn(userId: String, connection: Connection<*>, request: NativeWebRequest): String {
        SignInUtils().signin(userId)
        return extractOriginalUrl(request)
    }

    private fun extractOriginalUrl(request: NativeWebRequest): String {
        val nativeReq: HttpServletRequest = request.getNativeRequest(HttpServletRequest::class.java)!!
        val nativeRes: HttpServletResponse = request.getNativeResponse(HttpServletResponse::class.java)!!
        val saved: SavedRequest = requestCache.getRequest(nativeReq, nativeRes)
        requestCache.removeRequest(nativeReq, nativeRes)
        removeAuthenticationAttributes(nativeReq.getSession(false))
        return saved.redirectUrl
    }

    private fun removeAuthenticationAttributes(session: HttpSession) {
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)
    }
}