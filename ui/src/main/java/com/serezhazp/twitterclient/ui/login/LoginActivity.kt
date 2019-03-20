package com.serezhazp.twitterclient.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.serezhazp.twitterclient.presentation.login.LoginPresenter
import com.serezhazp.twitterclient.presentation.login.LoginView
import com.serezhazp.twitterclient.ui.R
import com.serezhazp.twitterclient.ui.main.MainActivity
import com.serezhazp.twitterclient.ui.xsupport.MvpXActivity
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : MvpXActivity(), LoginView {

    @Inject
    @InjectPresenter
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tw_login_button.setOnClickListener { showProgress() }
        tw_login_button.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>?) {
                presenter.twitterLoginSuccessful()
            }

            override fun failure(exception: TwitterException?) {
                exception?.let {
                    presenter.twitterLoginFail(exception.localizedMessage)
                }
            }
        }
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun navigateToMainScreen() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        tw_login_button.onActivityResult(requestCode, resultCode, data)
    }
}