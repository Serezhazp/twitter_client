package com.serezhazp.twitterclient.di

import com.serezhazp.twitterclient.data.SessionDataSource
import com.serezhazp.twitterclient.data.SessionRepository
import com.serezhazp.twitterclient.data.TweetsDataSource
import com.serezhazp.twitterclient.data.TweetsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideLoginRepository(sessionDataSource: SessionDataSource): SessionRepository =
        SessionRepository(sessionDataSource)

    @Provides
    @Singleton
    fun provideTweetsRepository(tweetsDataSource: TweetsDataSource): TweetsRepository =
        TweetsRepository(tweetsDataSource)
}