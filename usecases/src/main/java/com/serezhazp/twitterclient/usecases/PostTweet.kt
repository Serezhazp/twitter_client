package com.serezhazp.twitterclient.usecases

import com.serezhazp.twitterclient.data.TweetsRepository
import com.serezhazp.twitterclient.domain.Tweet
import com.serezhazp.twitterclient.domain.TweetToPost

class PostTweet(private val tweetsRepository: TweetsRepository) {

    operator fun invoke(tweet: TweetToPost, function: (tweet: Tweet?, error: Throwable?) -> Unit) {
        val file = tweet.file
        if (file != null) {
            tweetsRepository.uploadMedia(file) { mediaId, error ->
                if (error != null) {
                    function(null, error)
                } else {
                    tweet.media = mediaId
                    tweetsRepository.postTweet(tweet) { tweetResponse, postTweetError ->
                        if (postTweetError != null) {
                            function(null, postTweetError)
                        } else {
                            function(tweetResponse, null)
                        }
                    }
                }
            }
        } else {
            tweetsRepository.postTweet(tweet) { tweetResponse, error ->
                if (error != null) {
                    function(null, error)
                } else {
                    function(tweetResponse, null)
                }
            }
        }
    }
}