package com.serezhazp.twitterclient.data.repositories

import com.serezhazp.twitterclient.data.actions.IsLoggedInAction
import com.serezhazp.twitterclient.data.actions.LogoutAction

open class SessionRepository(private val sessionDataSource: SessionDataSource): LogoutAction,
    IsLoggedInAction {

    override fun isLoggedIn(): Boolean = sessionDataSource.isLoggedIn()

    override fun logout() = sessionDataSource.logout()
}

interface SessionDataSource {

    fun isLoggedIn(): Boolean

    fun logout()
}