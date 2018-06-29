package com.example.demo.services.social.media

import com.example.demo.jpa.User

// Stores the currently sign-in connection in a local thread
class SecurityContext {

    val currentUser = ThreadLocal<User>()

    fun getCurrentUser(): User {
        return currentUser.get() ?: throw IllegalStateException("No user is currently signed in")
    }

    fun setCurrentUser(user: User) {
        currentUser.set(user)
    }

    fun userSignedIn(): Boolean {
        return currentUser.get() != null
    }

    fun remove() {
        currentUser.remove()
    }

}