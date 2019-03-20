package com.serezhazp.twitterclient

import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.serezhazp.twitterclient.data.repositories.SessionDataSource
import com.serezhazp.twitterclient.data.repositories.SessionRepository
import com.serezhazp.twitterclient.data.repositories.TweetsDataSource
import com.serezhazp.twitterclient.data.repositories.TweetsRepository
import com.serezhazp.twitterclient.domain.Tweet
import com.serezhazp.twitterclient.domain.TweetToPost
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.io.File

class RepositoriesTests {

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var sessionDataSource: SessionDataSource

    @Mock
    lateinit var tweetsDataSource: TweetsDataSource

    @Test
    fun sessionDataSourceIsLoggedInTest() {

        Mockito.`when`(sessionDataSource.isLoggedIn()).thenReturn(true)

        val sessionRepository =
            SessionRepository(sessionDataSource)
        sessionRepository.isLoggedIn()

        verify(sessionDataSource, times(1)).isLoggedIn()
    }

    @Test
    fun sessionDataSourceLogoutTest() {

        doNothing().`when`(sessionDataSource).logout()

        val sessionRepository =
            SessionRepository(sessionDataSource)
        sessionRepository.logout()

        verify(sessionDataSource, times(1)).logout()
    }

    @Test
    fun tweetsDataSourcePostTweetTest() {

        val tweetToPost = TweetToPost(tweetText)

        val func = { _: Tweet?, _: Throwable? -> Unit }

        doNothing().`when`(tweetsDataSource).postTweet(tweetToPost, func)

        val tweetsRepository =
            TweetsRepository(tweetsDataSource)
        tweetsRepository.postTweet(tweetToPost, func)

        verify(tweetsDataSource, times(1)).postTweet(tweetToPost, func)
    }

    @Test
    fun tweetsDataSourceGetTweetsTest() {

        val func = { _: List<Tweet>, _: Throwable? -> Unit }

        doNothing().`when`(tweetsDataSource).getTweets(eq(func))

        val tweetsRepository =
            TweetsRepository(tweetsDataSource)
        tweetsRepository.getTweets(func)

        verify(tweetsDataSource, times(1)).getTweets(func)
    }

    @Test
    fun tweetsDataSourceUploadMediaTest() {

        val fileToUpload = File("file")

        val func = { _: String, _: Throwable? -> Unit }

        doNothing().`when`(tweetsDataSource).uploadMedia(fileToUpload, func)

        val tweetsRepository =
            TweetsRepository(tweetsDataSource)
        tweetsRepository.uploadMedia(fileToUpload, func)

        verify(tweetsDataSource, times(1)).uploadMedia(fileToUpload, func)
    }

    companion object {
        const val tweetText = "tweet"
    }
}