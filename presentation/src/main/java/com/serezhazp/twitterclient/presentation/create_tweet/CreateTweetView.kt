package com.serezhazp.twitterclient.presentation.create_tweet

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.serezhazp.twitterclient.domain.TweetToPost
import com.serezhazp.twitterclient.presentation.common.CommonView

@StateStrategyType(OneExecutionStateStrategy::class)
interface CreateTweetView : CommonView {

    fun takePhoto()

    fun pickImage()

    fun closeScreen()

    fun postTweet(tweet: TweetToPost)

    fun showEmptyTweetError()
}