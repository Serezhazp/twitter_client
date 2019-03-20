package com.serezhazp.twitterclient

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.serezhazp.twitterclient.data.actions.*
import com.serezhazp.twitterclient.domain.Tweet
import com.serezhazp.twitterclient.domain.TweetToPost
import com.serezhazp.twitterclient.domain.TwitterUser
import com.serezhazp.twitterclient.usecases.GetTweets
import com.serezhazp.twitterclient.usecases.IsLoggedIn
import com.serezhazp.twitterclient.usecases.Logout
import com.serezhazp.twitterclient.usecases.PostTweet
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.io.File

open class UseCasesTests {

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var logoutAction: LogoutAction

    @Mock
    lateinit var isLoggedInAction: IsLoggedInAction

    @Mock
    lateinit var getTweetsAction: GetTweetsAction

    @Mock
    lateinit var postTweetAction: PostTweetAction

    @Mock
    lateinit var uploadMediaAction: UploadMediaAction

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun isLoggedInUseCaseTest() {

        val isLoggedInUseCase = IsLoggedIn(isLoggedInAction)
        isLoggedInUseCase()

        verify(isLoggedInAction, times(1)).isLoggedIn()
    }

    @Test
    fun logoutUseCaseTest() {

        val logoutUseCase = Logout(logoutAction)
        logoutUseCase()

        verify(logoutAction, times(1)).logout()
    }

    @Test
    fun postTextTweetSuccessUseCaseTest() {

        val tweetToPost = TweetToPost(tweetOneText)

        Mockito.`when`(postTweetAction.postTweet(eq(tweetToPost), any())).thenAnswer {
            val completion = it.getArgument<((tweet: Tweet?, exception: Throwable?) -> Unit)>(1)
            completion.invoke(
                Tweet(tweetToPost.text, user = twitterUser),
                null
            )
        }

        val postTweetUseCase = PostTweet(postTweetAction, uploadMediaAction)
        postTweetUseCase(tweetToPost) { tweet, error ->
            assertNull(error)
            assertNotNull(tweet)
            assertEquals(tweetToPost.text, tweet?.text)
        }
    }

    @Test
    fun postTweetSuccessUseCaseTest() {

        val fileToUpload = File("file")
        val tweetToPost = TweetToPost(tweetOneText, file = fileToUpload)

        Mockito.`when`(postTweetAction.postTweet(eq(tweetToPost), any())).thenAnswer {
            val completion = it.getArgument<((tweet: Tweet?, exception: Throwable?) -> Unit)>(1)
            completion.invoke(
                Tweet(tweetToPost.text, user = twitterUser),
                null
            )
        }

        val postTweetUseCase = PostTweet(postTweetAction, uploadMediaAction)
        postTweetUseCase(tweetToPost) { tweet, error ->
            assertNull(error)
            assertNotNull(tweet)
            assertEquals(tweetToPost.text, tweet?.text)
        }
    }

    @Test
    fun postTweetFailureUseCaseTest() {

        val tweetToPost = TweetToPost(tweetOneText)

        Mockito.`when`(postTweetAction.postTweet(eq(tweetToPost), any())).thenAnswer {
            val completion = it.getArgument<((tweet: Tweet?, exception: Throwable?) -> Unit)>(1)
            completion.invoke(
                null,
                Throwable(errorMessage)
            )
        }

        val postTweetUseCase = PostTweet(postTweetAction, uploadMediaAction)
        postTweetUseCase(tweetToPost) { tweet, error ->
            assertNull(tweet)
            assertNotNull(error)
            assertEquals(errorMessage, error?.message)
        }
    }

    @Test
    fun getTweetsSuccessUseCaseTest() {

        Mockito.`when`(getTweetsAction.getTweets(any())).thenAnswer {
            val completion =
                it.getArgument<((tweets: List<Tweet>, exception: Throwable?) -> Unit)>(0)
            completion.invoke(
                tweetsList,
                null
            )
        }

        val getTweetsUseCase = GetTweets(getTweetsAction)
        getTweetsUseCase { tweets, error ->
            assertNotNull(tweets)
            assertEquals(tweetsList.size, tweets.size)
            assertEquals(tweetsList[0].text, tweets[0].text)
            assertEquals(tweetsList[1].text, tweets[1].text)
            assertNull(error)
        }
    }

    @Test
    fun getTweetsFailureUseCaseTest() {

        Mockito.`when`(getTweetsAction.getTweets(any())).thenAnswer {
            val completion =
                it.getArgument<((tweets: List<Tweet>, exception: Throwable?) -> Unit)>(0)
            completion.invoke(
                emptyList(),
                Throwable(errorMessage)
            )
        }

        val getTweetsUseCase = GetTweets(getTweetsAction)
        getTweetsUseCase { tweets, error ->
            assertNotNull(tweets)
            assertEquals(0, tweets.size)
            assertNotNull(error)
            assertEquals(errorMessage, error?.message)
        }
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