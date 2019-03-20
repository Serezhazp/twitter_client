package com.serezhazp.twitterclient.usecases

import com.serezhazp.twitterclient.data.actions.IsLoggedInAction

open class IsLoggedIn(private val isLoggedInAction: IsLoggedInAction) {

    open operator fun invoke(): Boolean = isLoggedInAction.isLoggedIn()
}