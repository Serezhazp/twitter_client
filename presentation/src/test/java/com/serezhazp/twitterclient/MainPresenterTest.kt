package com.serezhazp.twitterclient

import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.serezhazp.twitterclient.presentation.main.MainPresenter
import com.serezhazp.twitterclient.presentation.main.MainView
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class MainPresenterTest {

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var mainView: MainView

    @InjectMocks
    lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun openTweetCreationScreenTest() {
        doNothing().`when`(mainView).navigateToCreateTweetScreen()

        presenter.openCreateTweetScreen()

        verify(mainView, times(1)).navigateToCreateTweetScreen()
    }
}