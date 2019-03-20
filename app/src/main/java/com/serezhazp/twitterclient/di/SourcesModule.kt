package com.serezhazp.twitterclient.di

import com.serezhazp.twitterclient.data.SessionDataSource
import com.serezhazp.twitterclient.data.TweetsDataSource
import com.serezhazp.twitterclient.framework.TweetsSource
import com.serezhazp.twitterclient.framework.TwitterSessionSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SourcesModule {

    @Provides
    @Singleton
    fun provideLoginDataSource(): SessionDataSource =
        TwitterSessionSource()

    @Provides
    @Singleton
    fun provideTweetsDataSource(): TweetsDataSource =
        TweetsSource()
}