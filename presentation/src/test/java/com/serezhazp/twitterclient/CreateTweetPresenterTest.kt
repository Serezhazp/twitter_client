package com.serezhazp.twitterclient

import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.serezhazp.twitterclient.domain.TweetToPost
import com.serezhazp.twitterclient.presentation.create_tweet.CreateTweetPresenter
import com.serezhazp.twitterclient.presentation.create_tweet.CreateTweetView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CreateTweetPresenterTest {

    @Mock
    lateinit var createTweetView: CreateTweetView

    lateinit var presenter: CreateTweetPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun attachPhotoTest() {

        presenter = CreateTweetPresenter()
        presenter.attachView(createTweetView)

        presenter.attachPhoto()

        verify(createTweetView, times(1)).takePhoto()
    }

    @Test
    fun attachImageTest() {

        presenter = CreateTweetPresenter()
        presenter.attachView(createTweetView)

        presenter.attachImage()

        verify(createTweetView, times(1)).pickImage()
    }

    @Test
    fun closeScreenTest() {

        presenter = CreateTweetPresenter()
        presenter.attachView(createTweetView)

        presenter.close()

        verify(createTweetView, times(1)).closeScreen()
    }

    @Test
    fun postTweetTextTest() {

        presenter = CreateTweetPresenter()
        presenter.attachView(createTweetView)

        presenter.postTweet(tweetText)

        val tweetToPost = TweetToPost(tweetText)

        verify(createTweetView, times(1)).postTweet(eq(tweetToPost))
    }

    @Test
    fun postTweetEmptyTest() {

        presenter = CreateTweetPresenter()
        presenter.attachView(createTweetView)

        presenter.postTweet("")

        verify(createTweetView, times(1)).showEmptyTweetError()
    }

    companion object {
        const val tweetText = "tweet_text"
    }
}