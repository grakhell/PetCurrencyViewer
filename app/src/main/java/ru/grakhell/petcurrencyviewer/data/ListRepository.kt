package ru.grakhell.petcurrencyviewer.data

import androidx.lifecycle.LiveData
import ru.grakhell.petcurrencyviewer.vo.CurrencyList

interface ListRepository {

    fun addListData(list: CurrencyList):LiveData<State>

    fun updateListData(list: CurrencyList):LiveData<State>

    fun deleteListData(list: CurrencyList):LiveData<State>

    fun clearListData():LiveData<State>

    fun getListData():Result<CurrencyList>
}