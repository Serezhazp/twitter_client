package com.serezhazp.twitterclient.data.actions

import com.serezhazp.twitterclient.domain.Tweet
import com.serezhazp.twitterclient.domain.TweetToPost

interface PostTweetAction {

    fun postTweet(tweet: TweetToPost, function: (tweet: Tweet?, error: Throwable?) -> Unit)
}