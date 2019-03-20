package com.serezhazp.twitterclient.di

import com.serezhazp.twitterclient.data.repositories.SessionDataSource
import com.serezhazp.twitterclient.data.repositories.SessionRepository
import com.serezhazp.twitterclient.data.repositories.TweetsDataSource
import com.serezhazp.twitterclient.data.repositories.TweetsRepository
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