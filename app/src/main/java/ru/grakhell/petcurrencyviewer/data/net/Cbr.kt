package ru.grakhell.petcurrencyviewer.data.net

import androidx.annotation.Nullable
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.grakhell.petcurrencyviewer.vo.CurrencyList

interface Cbr {
    @GET("/scripts/XML_daily.asp")
    fun getCurrenciesList(
        @Nullable @Query("date_req") date:String? = null
    ): Deferred<Response<CurrencyList>>
}