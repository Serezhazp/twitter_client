package com.serezhazp.twitterclient.data.repositories

import com.serezhazp.twitterclient.data.actions.GetTweetsAction
import com.serezhazp.twitterclient.data.actions.PostTweetAction
import com.serezhazp.twitterclient.data.actions.UploadMediaAction
import com.serezhazp.twitterclient.domain.Tweet
import com.serezhazp.twitterclient.domain.TweetToPost
import java.io.File

open class TweetsRepository(private val tweetsDataSource: TweetsDataSource): GetTweetsAction, PostTweetAction, UploadMediaAction {

    override fun getTweets(function: (tweets: List<Tweet>, error: Throwable?) -> Unit) =
        tweetsDataSource.getTweets(function)

    override fun postTweet(tweet: TweetToPost, function: (tweet: Tweet?, error: Throwable?) -> Unit) =
        tweetsDataSource.postTweet(tweet, function)

    override fun uploadMedia(file: File, function: (mediaId: String, error: Throwable?) -> Unit) =
        tweetsDataSource.uploadMedia(file, function)
}

interface TweetsDataSource {

    fun getTweets(function: (tweets: List<Tweet>, error: Throwable?) -> Unit)

    fun postTweet(tweet: TweetToPost, function: (tweet: Tweet?, error: Throwable?) -> Unit)

    fun uploadMedia(file: File, function: (mediaId: String, error: Throwable?) -> Unit)
}