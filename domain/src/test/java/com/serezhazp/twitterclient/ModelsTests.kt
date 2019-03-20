package com.serezhazp.twitterclient

import com.serezhazp.twitterclient.domain.Tweet
import com.serezhazp.twitterclient.domain.TweetToPost
import com.serezhazp.twitterclient.domain.TwitterUser
import org.junit.Assert.*
import org.junit.Test
import java.io.File


class ModelsTests {

    companion object {
        const val tweetText = "tweet_text"
        const val tweetCreatedAt = "12/05/18"
        const val twitterUserName = "username"
        const val twitterUserNickName = "nickname"
        const val twitterUserAvatar = "avatar.jpg"
        const val tweetMediaLink = "media_link"
        const val tweetHashTagOne = "#tag_one"
        const val tweetHashTagTwo = "#tag_two"
        val tweetHashTags = listOf(tweetHashTagOne, tweetHashTagTwo)

        val tweetFile = File("")
    }

    @Test
    fun isTwitterUserCorrect() {
        val twitterUser = TwitterUser(twitterUserName, twitterUserNickName, twitterUserAvatar)

        assertEquals(twitterUserName, twitterUser.userName)
        assertEquals(twitterUserNickName, twitterUser.nickName)
        assertEquals(twitterUserAvatar, twitterUser.avatar)
    }

    @Test
    fun isFilledTweetCorrect() {

        val twitterUser = TwitterUser(twitterUserName, twitterUserNickName, twitterUserAvatar)

        val tweet = Tweet(
            tweetText,
            tweetCreatedAt,
            twitterUser,
            tweetMediaLink,
            tweetHashTags
        )

        assertEquals(tweetText, tweet.text)
        assertEquals(tweetCreatedAt, tweet.createdAt)
        assertNotNull(tweet.user)
        assertEquals(tweetMediaLink, tweet.media)
        assertEquals(tweetHashTags.size, tweet.hashTags?.size)
        assertNotNull(tweet.hashTags)
        assertEquals(tweetHashTagOne, tweet.hashTags?.get(0))
        assertEquals(tweetHashTagTwo, tweet.hashTags?.get(1))
    }

    @Test
    fun isEmptyTweetCorrect() {
        val twitterUser = TwitterUser(twitterUserName, twitterUserNickName, twitterUserAvatar)

        val tweet = Tweet(tweetText, user = twitterUser)

        assertEquals(tweetText, tweet.text)
        assertNotNull(tweet.user)
        assertEquals("", tweet.createdAt)
        assertNull(tweet.media)
        assertNotNull(tweet.hashTags)
        assertEquals(0, tweet.hashTags?.size)
    }

    @Test
    fun isFilledTweetToPostCorrect() {
        val tweetToPost = TweetToPost(tweetText, tweetFile, tweetMediaLink)

        assertEquals(tweetText, tweetToPost.text)
        assertNotNull(tweetToPost.file)
        assertEquals(tweetMediaLink, tweetToPost.media)
    }

    @Test
    fun isEmptyTweetToPostCorrect() {
        val tweetToPost = TweetToPost(tweetText)

        assertEquals(tweetText, tweetToPost.text)
        assertNull(tweetToPost.file)
        assertEquals("", tweetToPost.media)
    }
}