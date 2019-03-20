package com.serezhazp.twitterclient.di

import com.serezhazp.twitterclient.TwitterClientApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ActivityBuilderModule::class, AndroidSupportInjectionModule::class,
        AndroidContextModule::class, RepositoryModule::class,
        SourcesModule::class, UseCasesModule::class,
        NetworkModule::class, TwitterModule::class
    ]
)
interface AppMainComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: TwitterClientApplication): Builder

        fun androidContextModule(androidContextModule: AndroidContextModule): Builder
        fun twitterModule(twitterModule: TwitterModule): Builder
        fun sourcesModule(sourcesModule: SourcesModule): Builder
        fun repositoryModule(repositoryModule: RepositoryModule): Builder
        fun useCasesModule(useCasesModule: UseCasesModule): Builder

        fun build(): AppMainComponent
    }

    fun inject(twitterClientApplication: TwitterClientApplication)
}