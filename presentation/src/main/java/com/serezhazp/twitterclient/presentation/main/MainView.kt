package com.serezhazp.twitterclient.presentation.main

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.serezhazp.twitterclient.domain.Tweet
import com.serezhazp.twitterclient.presentation.common.CommonView

@StateStrategyType(OneExecutionStateStrategy::class)
interface MainView : CommonView {

    fun showTweets(tweets: List<Tweet>)

    fun showPostedTweet(tweet: Tweet)

    fun navigateToCreateTweetScreen()

    fun navigateToLoginScreen()
}