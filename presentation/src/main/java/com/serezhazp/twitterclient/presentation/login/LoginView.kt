package com.serezhazp.twitterclient.presentation.login

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.serezhazp.twitterclient.presentation.common.CommonView

@StateStrategyType(OneExecutionStateStrategy::class)
interface LoginView : CommonView {

    fun navigateToMainScreen()
}