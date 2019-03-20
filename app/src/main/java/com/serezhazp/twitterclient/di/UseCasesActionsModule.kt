package com.serezhazp.twitterclient.di

import com.serezhazp.twitterclient.data.actions.*
import com.serezhazp.twitterclient.data.repositories.SessionDataSource
import com.serezhazp.twitterclient.data.repositories.SessionRepository
import com.serezhazp.twitterclient.data.repositories.TweetsDataSource
import com.serezhazp.twitterclient.data.repositories.TweetsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCasesActionsModule {

    @Provides
    @Singleton
    fun provideLogoutAction(sessionDataSource: SessionDataSource): LogoutAction =
        SessionRepository(sessionDataSource)

    @Provides
    @Singleton
    fun provideIsLoggedInAction(sessionDataSource: SessionDataSource): IsLoggedInAction =
        SessionRepository(sessionDataSource)

    @Provides
    @Singleton
    fun provideGetTweetsAction(tweetsDataSource: TweetsDataSource): GetTweetsAction =
        TweetsRepository(tweetsDataSource)

    @Provides
    @Singleton
    fun providePostTweetAction(tweetsDataSource: TweetsDataSource): PostTweetAction =
        TweetsRepository(tweetsDataSource)

    @Provides
    @Singleton
    fun provideUploadMediaAction(tweetsDataSource: TweetsDataSource): UploadMediaAction =
        TweetsRepository(tweetsDataSource)
}