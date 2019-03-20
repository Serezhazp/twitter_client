package com.serezhazp.twitterclient.usecases

import com.serezhazp.twitterclient.data.actions.PostTweetAction
import com.serezhazp.twitterclient.data.actions.UploadMediaAction
import com.serezhazp.twitterclient.domain.Tweet
import com.serezhazp.twitterclient.domain.TweetToPost

open class PostTweet(private val postTweetAction: PostTweetAction, private var uploadMediaAction: UploadMediaAction) {

    open operator fun invoke(tweet: TweetToPost, function: (tweet: Tweet?, error: Throwable?) -> Unit) {
        val file = tweet.file
        if (file != null) {
            uploadMediaAction.uploadMedia(file) { mediaId, error ->
                if (error != null) {
                    function(null, error)
                } else {
                    tweet.media = mediaId
                    postTweetAction.postTweet(tweet) { tweetResponse, postTweetError ->
                        if (postTweetError != null) {
                            function(null, postTweetError)
                        } else {
                            function(tweetResponse, null)
                        }
                    }
                }
            }
        } else {
            postTweetAction.postTweet(tweet) { tweetResponse, error ->
                if (error != null) {
                    function(null, error)
                } else {
                    function(tweetResponse, null)
                }
            }
        }
    }
}