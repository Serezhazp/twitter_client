package com.serezhazp.twitterclient

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.serezhazp.twitterclient.domain.Tweet
import com.serezhazp.twitterclient.domain.TweetToPost
import com.serezhazp.twitterclient.domain.TwitterUser
import com.serezhazp.twitterclient.presentation.main.MainPresenter
import com.serezhazp.twitterclient.presentation.main.MainView
import com.serezhazp.twitterclient.usecases.GetTweets
import com.serezhazp.twitterclient.usecases.IsLoggedIn
import com.serezhazp.twitterclient.usecases.Logout
import com.serezhazp.twitterclient.usecases.PostTweet
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.io.File

class MainPresenterTest {

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var mainView: MainView

    @Mock
    lateinit var isLoggedIn: IsLoggedIn

    @Mock
    lateinit var logout: Logout

    @Mock
    lateinit var getTweets: GetTweets

    @Mock
    lateinit var postTweet: PostTweet

    @InjectMocks
    lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun logoutTest() {

        presenter.logout()

        verify(mainView, times(1)).navigateToLoginScreen()
    }

    @Test
    fun openTweetCreationScreenTest() {

        presenter.openCreateTweetScreen()

        verify(mainView, times(1)).navigateToCreateTweetScreen()
    }

    @Test
    fun retrieveTweetsSuccessTest() {

        Mockito.`when`(getTweets.invoke(any())).thenAnswer {
            val completion =
                it.getArgument<((tweets: List<Tweet>, exception: Throwable?) -> Unit)>(0)
            completion.invoke(
                tweetsList,
                null
            )
        }

        presenter.retrieveTweets()

        verify(getTweets, times(1)).invoke(any())

        getTweets { _, _ -> }

        verify(mainView, times(1)).showTweets(any())
    }

    @Test
    fun retrieveTweetsFailureTest() {

        Mockito.`when`(getTweets.invoke(any())).thenAnswer {
            val completion =
                it.getArgument<((tweets: List<Tweet>, exception: Throwable?) -> Unit)>(0)
            completion.invoke(
                emptyList(),
                Throwable(errorMessage)
            )
        }

        presenter.retrieveTweets()

        verify(getTweets, times(1)).invoke(any())

        getTweets { _, _ -> }

        verify(mainView, times(1)).showError(eq(errorMessage))
    }

    @Test
    fun postTweetSuccessTest() {

        val fileToUpload = File("file")
        val tweetToPost = TweetToPost(tweetOneText, file = fileToUpload)
        val postedTweet = Tweet(tweetToPost.text, user = twitterUser)

        Mockito.`when`(postTweet.invoke(eq(tweetToPost), any())).thenAnswer {
            val completion =
                it.getArgument<((tweet: Tweet?, exception: Throwable?) -> Unit)>(1)
            completion.invoke(
                postedTweet,
                null
            )
        }

        presenter.postTweet(tweetOneText, fileToUpload)

        verify(postTweet, times(1)).invoke(eq(tweetToPost), any())

        postTweet(tweetToPost) { _, _ -> }

        verify(mainView, times(1)).showPostedTweet(eq(postedTweet))
    }

    @Test
    fun postTweetFailureTest() {

        val fileToUpload = File("file")
        val tweetToPost = TweetToPost(tweetOneText, file = fileToUpload)

        Mockito.`when`(postTweet.invoke(eq(tweetToPost), any())).thenAnswer {
            val completion =
                it.getArgument<((tweet: Tweet?, exception: Throwable?) -> Unit)>(1)
            completion.invoke(
                null,
                Throwable(errorMessage)
            )
        }

        presenter.postTweet(tweetOneText, fileToUpload)

        verify(postTweet, times(1)).invoke(eq(tweetToPost), any())

        postTweet(tweetToPost) { _, _ -> }

        verify(mainView, times(1)).showError(eq(errorMessage))
    }

    @Test
    fun onPresenterCreateFailureTest() {

        Mockito.`when`(isLoggedIn.invoke()).thenReturn(false)

        presenter.onCreate()

        verify(mainView, times(1)).navigateToLoginScreen()
    }

    @Test
    fun onPresenterCreateSuccessfulTest() {

        Mockito.`when`(isLoggedIn.invoke()).thenReturn(true)

        Mockito.`when`(getTweets.invoke(any())).thenAnswer {
            val completion =
                it.getArgument<((tweets: List<Tweet>, exception: Throwable?) -> Unit)>(0)
            completion.invoke(
                tweetsList,
                null
            )
        }

        presenter.onCreate()

        getTweets { _, _ -> }

        verify(mainView, times(1)).showTweets(any())
    }

    companion object {
        private const val twitterUserName = "username"
        private const val twitterUserNickName = "nickname"
        private const val twitterUserAvatar = "avatar.jpg"
        const val tweetOneText = "tweet_one"
        private const val tweetTwoText = "tweet_two"
        const val errorMessage = "error_message"
        val twitterUser = TwitterUser(twitterUserName, twitterUserNickName, twitterUserAvatar)
        private val tweetOne = Tweet(tweetOneText, user = twitterUser)
        private val tweetTwo = Tweet(tweetTwoText, user = twitterUser)
        val tweetsList = listOf(tweetOne, tweetTwo)
    }
}