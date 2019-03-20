package com.serezhazp.twitterclient.di

import com.serezhazp.twitterclient.data.repositories.SessionRepository
import com.serezhazp.twitterclient.data.repositories.TweetsRepository
import com.serezhazp.twitterclient.usecases.GetTweets
import com.serezhazp.twitterclient.usecases.IsLoggedIn
import com.serezhazp.twitterclient.usecases.Logout
import com.serezhazp.twitterclient.usecases.PostTweet
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCasesModule {

    @Provides
    @Singleton
    fun provideIsLoggedInUseCase(sessionRepository: SessionRepository): IsLoggedIn =
        IsLoggedIn(sessionRepository)

    @Provides
    @Singleton
    fun provideGetTweetsUseCase(tweetsRepository: TweetsRepository): GetTweets =
        GetTweets(tweetsRepository)

    @Provides
    @Singleton
    fun providePostTweetUseCase(tweetsRepository: TweetsRepository): PostTweet =
        PostTweet(tweetsRepository, tweetsRepository)

    @Provides
    @Singleton
    fun provideLogoutUseCase(sessionRepository: SessionRepository): Logout =
        Logout(sessionRepository)
}