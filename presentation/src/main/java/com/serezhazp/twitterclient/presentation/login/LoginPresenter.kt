package com.serezhazp.twitterclient.presentation.login

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import javax.inject.Inject

@InjectViewState
open class LoginPresenter @Inject constructor() : MvpPresenter<LoginView>() {

    fun twitterLoginSuccessful() {
        viewState.hideProgress()
        viewState.navigateToMainScreen()
    }

    fun twitterLoginFail(errorMessage: String) {
        viewState.hideProgress()
        viewState.showError(errorMessage)
    }
}