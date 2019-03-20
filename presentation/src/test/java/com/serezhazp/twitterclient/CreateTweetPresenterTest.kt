package com.serezhazp.twitterclient

import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.serezhazp.twitterclient.domain.TweetToPost
import com.serezhazp.twitterclient.presentation.create_tweet.CreateTweetPresenter
import com.serezhazp.twitterclient.presentation.create_tweet.CreateTweetView
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CreateTweetPresenterTest {

    @Mock
    lateinit var createTweetView: CreateTweetView

    @InjectMocks
    lateinit var presenter: CreateTweetPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun attachPhotoTest() {

        presenter.attachPhoto()

        verify(createTweetView, times(1)).takePhoto()
    }

    @Test
    fun attachImageTest() {

        presenter.attachImage()

        verify(createTweetView, times(1)).pickImage()
    }

    @Test
    fun closeScreenTest() {

        presenter.close()

        verify(createTweetView, times(1)).closeScreen()
    }

    @Test
    fun postTweetTextTest() {

        presenter.postTweet(tweetText)

        val tweetToPost = TweetToPost(tweetText)

        verify(createTweetView, times(1)).postTweet(eq(tweetToPost))
    }

    @Test
    fun postTweetEmptyTest() {

        presenter.postTweet("")

        verify(createTweetView, times(1)).showEmptyTweetError()
    }

    companion object {
        const val tweetText = "tweet_text"
    }
}