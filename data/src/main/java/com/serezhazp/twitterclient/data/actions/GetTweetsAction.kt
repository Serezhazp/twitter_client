package com.serezhazp.twitterclient.data.actions

import com.serezhazp.twitterclient.domain.Tweet

interface GetTweetsAction {

    fun getTweets(function: (tweets: List<Tweet>, error: Throwable?) -> Unit)
}