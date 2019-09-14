package ru.grakhell.petcurrencyviewer.data.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import kotlin.reflect.KClass

class ServiceFactory {
    companion object {

        fun <T:Any> createNetService(uri:String, pattern: KClass<T>)
            :T {

            val client = OkHttpClient()
                .newBuilder()
                .build()
            val netWorker = Retrofit.Builder()
                .baseUrl(uri)
                .addConverterFactory(
                    SimpleXmlConverterFactory.create()
                )
                .addCallAdapterFactory(AsyncCallAdapterFactory.invoke())
                .client(client)
                .build()

            return netWorker.create(pattern.java)
        }
    }
}