package com.serezhazp.twitterclient.framework

import com.serezhazp.twitterclient.data.SessionDataSource
import com.twitter.sdk.android.core.TwitterCore

class TwitterSessionSource : SessionDataSource {

    override fun isLoggedIn(): Boolean {
        val session = TwitterCore.getInstance().sessionManager.activeSession
        return (session != null)
    }

    override fun logout() {
        TwitterCore.getInstance().sessionManager.clearActiveSession()
    }
}