package com.serezhazp.twitterclient.di

import android.content.Context
import android.util.Log
import com.serezhazp.twitterclient.BuildConfig
import com.twitter.sdk.android.core.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class TwitterModule {

    @Provides
    @Singleton
    fun provideTwitter(context: Context, okHttpClient: OkHttpClient): TwitterConfig {
        val config = TwitterConfig.Builder(context)
            .logger(DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(
                TwitterAuthConfig(
                    BuildConfig.CONSUMER_KEY,
                    BuildConfig.CONSUMER_SECRET
                )
            )
            .debug(BuildConfig.DEBUG)
            .build()

        Twitter.initialize(config)

        val activeSession = TwitterCore.getInstance().sessionManager.activeSession
        if (activeSession != null) {
            val twitterApiClient = TwitterApiClient(activeSession, okHttpClient)
            TwitterCore.getInstance().addApiClient(activeSession, twitterApiClient)
        } else {
            val twitterApiClient = TwitterApiClient(okHttpClient)
            TwitterCore.getInstance().addGuestApiClient(twitterApiClient)
        }

        return config
    }
}