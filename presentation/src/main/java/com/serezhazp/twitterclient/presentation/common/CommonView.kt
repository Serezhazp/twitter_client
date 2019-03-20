package com.serezhazp.twitterclient.presentation.common

import com.arellomobile.mvp.MvpView

interface CommonView : MvpView {

    fun showError(errorMessage: String)

    fun showProgress()

    fun hideProgress()
}