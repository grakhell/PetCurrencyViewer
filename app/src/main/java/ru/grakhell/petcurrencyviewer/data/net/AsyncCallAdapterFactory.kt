package ru.grakhell.petcurrencyviewer.data.net

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class AsyncCallAdapterFactory private constructor(): CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Deferred::class.java) return null
        if (returnType !is ParameterizedType) {
            throw IllegalArgumentException("Return type is not parameterized")}

        val responseType = getParameterUpperBound(0, returnType)

        return if (getRawType(responseType) == Response::class.java) {
            if (responseType !is ParameterizedType) throw IllegalArgumentException("Response type is not parameterized")
            ResponseCallAdapter<Any>(getParameterUpperBound(0, responseType))
        } else {
            BodyCallAdapter<Any>(responseType)
        }
    }

    inner class BodyCallAdapter<T>(
        private val responseType: Type
    ):  CallAdapter<T, Deferred<T>> {

        override fun adapt(call: Call<T>): Deferred<T> {
            val async = CompletableDeferred<T>()

            async.invokeOnCompletion {
                if (async.isCancelled) call.cancel()
            }

            call.enqueue( object: Callback<T>{

                override fun onFailure(call: Call<T>, t: Throwable) {
                    async.completeExceptionally(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    try {
                        if (response.isSuccessful){
                            val body = response.body()
                            if ( body != null) async.complete(body) else { throw
                            IllegalArgumentException("Response body is empty or null")}
                        }else{
                            throw HttpException(response)
                        }
                    }catch (e:Exception){
                        async.completeExceptionally(e)
                    }
                }
            })

            return async
        }

        override fun responseType(): Type = responseType
    }

    inner class ResponseCallAdapter<T>(
        private val responseType: Type
    ): CallAdapter<T, Deferred<Response<T>>> {

        override fun adapt(call: Call<T>): Deferred<Response<T>> {

            val async = CompletableDeferred<Response<T>>()

            async.invokeOnCompletion {
               if (async.isCancelled) call.cancel()
            }

            call.enqueue( object: Callback<T>{

                override fun onFailure(call: Call<T>, t: Throwable) {
                    async.completeExceptionally(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    async.complete(response)
                }
            })

            return async
        }

        override fun responseType(): Type = responseType
    }

    companion object {
        @JvmStatic
        @JvmName("create")
        operator fun invoke() = AsyncCallAdapterFactory()
    }
}