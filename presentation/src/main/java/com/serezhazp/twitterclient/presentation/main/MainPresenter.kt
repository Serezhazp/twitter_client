package com.serezhazp.twitterclient.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.serezhazp.twitterclient.domain.TweetToPost
import com.serezhazp.twitterclient.usecases.GetTweets
import com.serezhazp.twitterclient.usecases.IsLoggedIn
import com.serezhazp.twitterclient.usecases.Logout
import com.serezhazp.twitterclient.usecases.PostTweet
import java.io.File
import javax.inject.Inject

@InjectViewState
open class MainPresenter @Inject constructor() : MvpPresenter<MainView>() {

    @Inject
    lateinit var isLoggedInUseCase: IsLoggedIn
    @Inject
    lateinit var getTweetsUseCase: GetTweets
    @Inject
    lateinit var postTweetUseCase: PostTweet
    @Inject
    lateinit var logoutUseCase: Logout

    fun onCreate() {
        checkTwitterSession()
    }

    fun retrieveTweets() {
        getTweetsUseCase { tweets, error ->
            if (error != null) {
                viewState.showError(error.localizedMessage)
            } else {
                viewState.showTweets(tweets)
            }
        }
    }

    fun openCreateTweetScreen() {
        viewState.navigateToCreateTweetScreen()
    }

    fun postTweet(tweetText: String, file: File? = null) {
        viewState.showProgress()

        val tweetToPost = TweetToPost(tweetText, file)
        postTweetUseCase(tweetToPost) { tweet, error ->
            if (tweet != null) {
                viewState.showPostedTweet(tweet)
            }
            viewState.hideProgress()
            if (error != null) {
                viewState.showError(error.localizedMessage)
            }
        }
    }

    fun logout() {
        logoutUseCase()
        viewState.navigateToLoginScreen()
    }

    private fun checkTwitterSession() {
        if (isLoggedInUseCase()) {
            retrieveTweets()
        } else {
            viewState.navigateToLoginScreen()
        }
    }
}