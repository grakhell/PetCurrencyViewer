package ru.grakhell.petcurrencyviewer.data

import androidx.lifecycle.LiveData
import ru.grakhell.petcurrencyviewer.vo.Currency

interface CurrencyRepository {

    fun addCurrencies(currencies:List<Currency>):LiveData<State>

    fun updateCurrencies(currencies:List<Currency>):LiveData<State>

    fun deleteCurrencies(currencies:List<Currency>):LiveData<State>

    fun clearCurrencies():LiveData<State>

    fun getCurrenciesList():Result<List<Currency>>

}