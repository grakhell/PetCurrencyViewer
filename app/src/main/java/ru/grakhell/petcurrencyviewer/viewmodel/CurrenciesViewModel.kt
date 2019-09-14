package ru.grakhell.petcurrencyviewer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import ru.grakhell.petcurrencyviewer.data.CurrencyRepository
import ru.grakhell.petcurrencyviewer.data.ListRepository
import ru.grakhell.petcurrencyviewer.util.toSingleEvent
import ru.grakhell.petcurrencyviewer.vo.Currency
import ru.grakhell.petcurrencyviewer.vo.CurrencyList

class CurrenciesViewModel(
    private val listRepo:ListRepository,
    private val currencyRepo:CurrencyRepository
):BaseViewModel() {


    private val _init = MutableLiveData<Boolean>()

    private val _listData = Transformations.switchMap(_init) {
        getData()
    }
    private val _currencies = Transformations.switchMap(_init) {
        getCurrenciesData()
    }
    private val _onSwipedToRefreshEvent = MutableLiveData<Boolean>()

    val listData:LiveData<CurrencyList> = _listData
    val currencies:LiveData<List<Currency>> = _currencies

    val onSwipedToRefrashEvent:LiveData<Boolean> = _onSwipedToRefreshEvent.toSingleEvent()

    init {
        updateData()
    }

    private fun getData(): LiveData<CurrencyList> {
        val source = listRepo.getListData()
        setState(source.state, source.retry)
        return source.data
    }

    private fun getCurrenciesData(): LiveData<List<Currency>> {
        val source = currencyRepo.getCurrenciesList()
        setState(source.state, source.retry)
        return source.data
    }

    fun updateData() {
        _init.value = true
    }

    fun onSwipedToRefresh(isSwiped:Boolean = true) {
        _onSwipedToRefreshEvent.value = isSwiped
    }
}