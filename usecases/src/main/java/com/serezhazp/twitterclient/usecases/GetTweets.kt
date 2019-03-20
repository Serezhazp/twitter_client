package com.serezhazp.twitterclient.usecases

import com.serezhazp.twitterclient.data.TweetsRepository
import com.serezhazp.twitterclient.domain.Tweet

class GetTweets(private val tweetsRepository: TweetsRepository) {

    operator fun invoke(function: (tweets: List<Tweet>, error: Throwable?) -> Unit) =
        tweetsRepository.getTweets(function)
}