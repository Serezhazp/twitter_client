package com.serezhazp.twitterclient.usecases

import com.serezhazp.twitterclient.data.actions.LogoutAction

open class Logout(private val logoutAction: LogoutAction) {

    open operator fun invoke() = logoutAction.logout()
}