package com.example.meddybuddyassigment.network

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkConfigurationImpl @Inject constructor() : NetworkConfiguration {

    companion object {
        private const val BASE_URL = "http://www.personalityforge.com/"
    }

    override fun getBaseUrl(): String {
        return BASE_URL
    }


}