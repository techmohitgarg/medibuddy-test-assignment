package com.example.meddybuddyassigment.di.modules

import com.example.meddybuddyassigment.app.MyApplication
import com.example.meddybuddyassigment.chat.remote.ChatApiServices
import com.example.meddybuddyassigment.chat.repository.Repository
import com.example.meddybuddyassigment.chat.repository.RepositoryImp
import com.example.meddybuddyassigment.errorProvider.ErrorProvider
import com.example.meddybuddyassigment.errorProvider.ErrorProviderImpl
import com.example.meddybuddyassigment.network.NetworkConfiguration
import com.example.meddybuddyassigment.network.NetworkProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.example.meddybuddyassigment.chat.local.LocalSource
import com.example.meddybuddyassigment.chat.remote.ChatDataSource
import com.example.meddybuddyassigment.chat.remote.ChatDataSourceImp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: MyApplication) {
    @Singleton
    @Provides
    fun providesGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideErrorProvider(): ErrorProvider {
        return ErrorProviderImpl(application.applicationContext)
    }

    @Singleton
    @Provides
    fun providesChatApiService(
        networkProvider: NetworkProvider,
        networkConfiguration: NetworkConfiguration
    ): ChatApiServices {
        return networkProvider.getApiInstance(
            ChatApiServices::class.java,
            networkConfiguration.getBaseUrl()
        )
    }

    @Singleton
    @Provides
    fun providesChatDataSource(
        chatApiServices: ChatApiServices
    ): ChatDataSource {
        return ChatDataSourceImp(chatApiServices)
    }

    @Singleton
    @Provides
    fun providesChatRepository(
        chatDataSource: ChatDataSource,
        chatLocalSource: LocalSource
    ): Repository {
        return RepositoryImp(chatDataSource, chatLocalSource)
    }


}