package com.serezhazp.twitterclient

import android.app.Activity
import android.app.Application
import android.app.Service
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.RegisterMoxyReflectorPackages
import com.serezhazp.twitterclient.di.*
import com.twitter.sdk.android.core.TwitterConfig
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

@RegisterMoxyReflectorPackages("com.serezhazp.twitterclient.presentation", "com.serezhazp.twitterclient.ui")
class TwitterClientApplication : Application(), HasActivityInjector, HasSupportFragmentInjector,
    HasServiceInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var serviceInjector: DispatchingAndroidInjector<Service>
    @Inject
    lateinit var twitter: TwitterConfig

    override fun activityInjector(): AndroidInjector<Activity> = androidInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    override fun serviceInjector(): AndroidInjector<Service> = serviceInjector

    override fun onCreate() {
        super.onCreate()

        initDagger()
    }

    private fun initDagger() {
        DaggerAppMainComponent.builder()
            .application(this)
            .androidContextModule(AndroidContextModule(this))
            .sourcesModule(SourcesModule())
            .repositoryModule(RepositoryModule())
            .useCasesModule(UseCasesModule())
            .build()
            .inject(this)
    }
}
