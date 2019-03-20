package com.serezhazp.twitterclient.presentation.create_tweet

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.serezhazp.twitterclient.domain.TweetToPost
import java.io.File
import javax.inject.Inject

@InjectViewState
class CreateTweetPresenter @Inject constructor() : MvpPresenter<CreateTweetView>() {

    private var photo: File? = null

    fun postTweet(text: String) {
        val tweetToPost = TweetToPost(text, photo)

        if (!text.isEmpty() || photo != null) {
            viewState.postTweet(tweetToPost)
        } else {
            viewState.showEmptyTweetError()
        }
    }

    fun setPhotoFile(file: File?) {
        photo = file
    }

    fun attachPhoto() {
        viewState.takePhoto()
    }

    fun attachImage() {
        viewState.pickImage()
    }

    fun close() {
        viewState.closeScreen()
    }
}