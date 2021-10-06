package com.example.meddybuddyassigment.network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkProviderImpl @Inject constructor(
    private val networkConfiguration: NetworkConfiguration,
    private val gson: Gson
) : NetworkProvider {

    private val apiCache: MutableMap<String, Any> = mutableMapOf()
    private var okHttpClient: OkHttpClient? = null
    private var baseUrlRetrofit: Retrofit? = null

    private fun <T> buildApi(apiInterfaceClass: Class<T>, baseUrl: String): T {
        return buildRetrofitApi(baseUrl)
            .create(apiInterfaceClass)
    }

    private fun getOkHttpClient(): OkHttpClient {
        if (okHttpClient == null) {
            val builder = OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectTimeout(
                    30,
                    TimeUnit.SECONDS
                )
                .readTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)

            val logging = HttpLoggingInterceptor()

            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)

            okHttpClient = builder.build()
        }
        return okHttpClient!!
    }

    private fun buildRetrofitApi(baseUrl: String): Retrofit {
        if (baseUrl == networkConfiguration.getBaseUrl() && baseUrlRetrofit != null) {
            return baseUrlRetrofit!!
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .also {
                if (baseUrl == networkConfiguration.getBaseUrl()) {
                    baseUrlRetrofit = it
                }
            }
    }

    override fun <T : Any> getApiInstance(apiClass: Class<T>, baseUrl: String): T {
        var instance = apiCache[apiClass.name]
        if (instance == null) {
            instance = buildApi(apiClass, baseUrl)
            apiCache[apiClass.name] = instance
        }

        return instance as T
    }

}