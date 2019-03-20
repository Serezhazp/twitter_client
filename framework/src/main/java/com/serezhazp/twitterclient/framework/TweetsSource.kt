package com.serezhazp.twitterclient.framework

import com.serezhazp.twitterclient.data.TweetsDataSource
import com.serezhazp.twitterclient.domain.Tweet
import com.serezhazp.twitterclient.domain.TweetToPost
import com.serezhazp.twitterclient.domain.TwitterUser
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Media
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File

class TweetsSource : TweetsDataSource {

    private val retrievedTweets = mutableListOf<Tweet>()

    override fun getTweets(function: (tweets: List<Tweet>, error: Throwable?) -> Unit) {
        val apiClient = TwitterCore.getInstance().apiClient

        val call = apiClient.statusesService.homeTimeline(
            TWEETS_COUNT,
            null,
            null,
            null,
            null,
            null,
            null
        )

        call.enqueue(object : Callback<MutableList<com.twitter.sdk.android.core.models.Tweet>>() {
            override fun success(result: Result<MutableList<com.twitter.sdk.android.core.models.Tweet>>) {
                retrievedTweets.clear()

                result.data?.forEach { tweet ->
                    retrievedTweets.add(tweet.mapTweet())
                }

                function(retrievedTweets, null)
            }

            override fun failure(exception: TwitterException?) {
                function(emptyList(), exception)
            }
        })
    }

    override fun postTweet(
        tweet: TweetToPost,
        function: (tweet: Tweet?, error: Throwable?) -> Unit
    ) {
        val apiClient = TwitterCore.getInstance().apiClient

        apiClient.statusesService.update(
            tweet.text,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            tweet.media
        )
            .enqueue(object : Callback<com.twitter.sdk.android.core.models.Tweet>() {
                override fun success(result: Result<com.twitter.sdk.android.core.models.Tweet>) {
                    function(result.data.mapTweet(), null)
                }

                override fun failure(exception: TwitterException?) {
                    function(null, exception)
                }
            })
    }

    override fun uploadMedia(file: File, function: (mediaId: String, error: Throwable?) -> Unit) {
        val apiClient = TwitterCore.getInstance().apiClient

        apiClient.mediaService.upload(
            RequestBody.create(MediaType.parse("image/*"), file),
            null,
            null
        ).enqueue(object : Callback<Media>() {
            override fun success(result: Result<Media>) {
                function(result.data.mediaIdString, null)
            }

            override fun failure(exception: TwitterException?) {
                function("", exception)
            }
        })
    }

    companion object {
        private const val TWEETS_COUNT = 20
    }
}

fun com.twitter.sdk.android.core.models.Tweet.mapTweet(): Tweet {
    val tweeterUser = TwitterUser(
        user.name,
        user.screenName,
        user.profileImageUrlHttps
    )

    val media = if (entities.media.isNotEmpty()) {
        entities.media.first().mediaUrlHttps
    } else {
        ""
    }

    val hashTags = if (entities.hashtags.isNotEmpty()) {
        entities.hashtags.map { hashTag ->
            hashTag.text
        }
    } else {
        emptyList()
    }

    return Tweet(
        text,
        createdAt,
        tweeterUser,
        media,
        hashTags
    )
}