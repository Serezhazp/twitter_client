package com.serezhazp.twitterclient.di

import com.serezhazp.twitterclient.data.repositories.SessionDataSource
import com.serezhazp.twitterclient.data.repositories.TweetsDataSource
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