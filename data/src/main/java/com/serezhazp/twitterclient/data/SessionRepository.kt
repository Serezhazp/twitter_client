package com.serezhazp.twitterclient.data

open class SessionRepository(private val sessionDataSource: SessionDataSource) {

    open fun isLoggedIn(): Boolean = sessionDataSource.isLoggedIn()

    open fun logout() = sessionDataSource.logout()
}

interface SessionDataSource {

    fun isLoggedIn(): Boolean

    fun logout()
}