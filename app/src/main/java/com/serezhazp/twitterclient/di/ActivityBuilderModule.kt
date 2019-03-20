package com.serezhazp.twitterclient.di

import com.serezhazp.twitterclient.ui.create_tweet.CreateTweetActivity
import com.serezhazp.twitterclient.ui.login.LoginActivity
import com.serezhazp.twitterclient.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    internal abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    internal abstract fun bindMainAcitvity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun bindCreateTweetActivity(): CreateTweetActivity
}