package com.serezhazp.twitterclient.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class AndroidContextModule(private var application: Application) {

    @Provides
    @Singleton
    open fun provideContext(): Context = application.applicationContext

    @Provides
    @Singleton
    open fun provideApplication() = application
}