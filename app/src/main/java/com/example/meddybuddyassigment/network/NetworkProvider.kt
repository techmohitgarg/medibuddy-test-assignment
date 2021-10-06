package com.example.meddybuddyassigment.network

interface NetworkProvider {

    /**
     * Returns an instance of the Retrofit api as defined by [apiClass]
     *
     * Implementation should return a cached instance if available, else create, cache and return a new instance
     *
     * @param apiClass The class of the retrofit api interface
     * @param baseUrl The base url for this api
     */
    fun <T : Any> getApiInstance(apiClass: Class<T>, baseUrl: String): T
}