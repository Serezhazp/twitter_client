package com.serezhazp.twitterclient.data

import com.serezhazp.twitterclient.domain.Tweet
import com.serezhazp.twitterclient.domain.TweetToPost
import java.io.File

open class TweetsRepository(private val tweetsDataSource: TweetsDataSource) {

    open fun getTweets(function: (tweets: List<Tweet>, error: Throwable?) -> Unit) =
        tweetsDataSource.getTweets(function)

    open fun postTweet(tweet: TweetToPost, function: (tweet: Tweet?, error: Throwable?) -> Unit) =
        tweetsDataSource.postTweet(tweet, function)

    open fun uploadMedia(file: File, function: (mediaId: String, error: Throwable?) -> Unit) =
        tweetsDataSource.uploadMedia(file, function)
}

interface TweetsDataSource {

    fun getTweets(function: (tweets: List<Tweet>, error: Throwable?) -> Unit)

    fun postTweet(tweet: TweetToPost, function: (tweet: Tweet?, error: Throwable?) -> Unit)

    fun uploadMedia(file: File, function: (mediaId: String, error: Throwable?) -> Unit)
}