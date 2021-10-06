package com.example.meddybuddyassigment.di.modules


import com.example.meddybuddyassigment.network.NetworkConfiguration
import com.example.meddybuddyassigment.network.NetworkConfigurationImpl
import com.example.meddybuddyassigment.network.NetworkProvider
import com.example.meddybuddyassigment.network.NetworkProviderImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface NetworkModule {

    @Binds
    @Singleton
    fun bindNetworkProvider(networkProvider: NetworkProviderImpl): NetworkProvider

    @Binds
    @Singleton
    fun bindNetworkConfiguration(networkConfiguration: NetworkConfigurationImpl): NetworkConfiguration
}