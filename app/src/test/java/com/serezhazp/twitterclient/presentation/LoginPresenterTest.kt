package com.serezhazp.twitterclient.presentation

import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.serezhazp.twitterclient.presentation.login.LoginPresenter
import com.serezhazp.twitterclient.presentation.login.LoginView
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LoginPresenterTest {

    @Mock
    lateinit var loginView: LoginView

    @InjectMocks
    lateinit var presenter: LoginPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun loginSuccessfulTest() {

        presenter.twitterLoginSuccessful()

        verify(loginView, times(1)).hideProgress()
        verify(loginView, times(1)).navigateToMainScreen()
    }

    @Test
    fun loginFailureTest() {

        presenter.twitterLoginFail(errorMessage)

        verify(loginView, times(1)).hideProgress()
        verify(loginView, times(1)).showError(eq(errorMessage))
    }

    companion object {
        const val errorMessage = "error"
    }
}