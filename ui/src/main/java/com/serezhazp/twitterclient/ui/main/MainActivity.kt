package com.serezhazp.twitterclient.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.serezhazp.twitterclient.domain.Tweet
import com.serezhazp.twitterclient.presentation.main.MainPresenter
import com.serezhazp.twitterclient.presentation.main.MainView
import com.serezhazp.twitterclient.ui.FILE_TO_POST_KEY
import com.serezhazp.twitterclient.ui.R
import com.serezhazp.twitterclient.ui.TWEET_TO_POST_KEY
import com.serezhazp.twitterclient.ui.create_tweet.CreateTweetActivity
import com.serezhazp.twitterclient.ui.create_tweet.CreateTweetActivity.Companion.CREATE_TWEET_REQUEST_CODE
import com.serezhazp.twitterclient.ui.login.LoginActivity
import com.serezhazp.twitterclient.ui.main.adapter.FeedAdapter
import com.serezhazp.twitterclient.ui.xsupport.MvpXActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import javax.inject.Inject

class MainActivity : MvpXActivity(), MainView {

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    private lateinit var adapter: FeedAdapter

    @ProvidePresenter
    fun providePresenter(): MainPresenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)

        tweets_list.itemAnimator = null
        tweets_list.layoutManager = layoutManager

        adapter = FeedAdapter()
        tweets_list.adapter = adapter

        presenter.onCreate()

        swipe_refresh.setOnRefreshListener {
            presenter.retrieveTweets()
        }

        fab.setOnClickListener {
            presenter.openCreateTweetScreen()
        }
    }

    override fun showTweets(tweets: List<Tweet>) {
        adapter.setData(tweets)
        swipe_refresh.isRefreshing = false
    }

    override fun navigateToCreateTweetScreen() {
        val intent = Intent(this, CreateTweetActivity::class.java)
        startActivityForResult(intent, CREATE_TWEET_REQUEST_CODE)
    }

    override fun navigateToLoginScreen() {
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun showError(errorMessage: String) {
        swipe_refresh.isRefreshing = false
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun showPostedTweet(tweet: Tweet) {
        Log.d("MAIN_ACTIVITY", "showPostedTweet")
        adapter.setLastTweet(tweet)
        tweets_list.smoothScrollToPosition(0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_logout -> {
                showLogoutDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CREATE_TWEET_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val tweet = data?.extras?.getString(TWEET_TO_POST_KEY)
            val image = data?.extras?.getSerializable(FILE_TO_POST_KEY)

            if (tweet != null && image != null) {
                val imageFile = image as File
                presenter.postTweet(tweet, imageFile)
            } else if (tweet != null) {
                presenter.postTweet(tweet)
            }
        }
    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder
            .setTitle(R.string.logout_dialog_title)
            .setMessage(R.string.logout_dialog_text)
            .setPositiveButton(R.string.logout_dialog_yes) { dialog, _ ->
                presenter.logout()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.logout_dialog_cancel) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

}