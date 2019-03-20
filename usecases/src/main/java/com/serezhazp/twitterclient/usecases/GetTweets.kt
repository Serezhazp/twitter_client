package com.serezhazp.twitterclient.usecases

import com.serezhazp.twitterclient.data.actions.GetTweetsAction
import com.serezhazp.twitterclient.domain.Tweet

open class GetTweets(private val getTweetsAction: GetTweetsAction) {

    open operator fun invoke(function: (tweets: List<Tweet>, error: Throwable?) -> Unit) =
        getTweetsAction.getTweets(function)
}