package ru.grakhell.petcurrencyviewer.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.grakhell.petcurrencyviewer.data.net.Cbr
import ru.grakhell.petcurrencyviewer.data.room.CurrencyDatabase
import ru.grakhell.petcurrencyviewer.vo.Currency
import java.lang.Exception

class CurrencyRepositoryImpl(
    private val net: Cbr,
    private val db: CurrencyDatabase
):CurrencyRepository {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun addCurrencies(currencies: List<Currency>): LiveData<State> {
        val state = MutableLiveData<State>()
        scope.launch {
            state.postValue(State.working())
            try {
                db.currencyDao().insertCurrencies(currencies)
                state.postValue(State.finished())
            } catch(ex: Exception) {
                state.postValue(State.error(ex))
            }
        }
        return state
    }

    override fun updateCurrencies(currencies: List<Currency>): LiveData<State> {
        val state = MutableLiveData<State>()
        scope.launch {
            state.postValue(State.working())
            try {
                db.currencyDao().updateCurrencies(currencies)
                state.postValue(State.finished())
            } catch(ex: Exception) {
                state.postValue(State.error(ex))
            }
        }
        return state
    }

    override fun deleteCurrencies(currencies: List<Currency>): LiveData<State> {
        val state = MutableLiveData<State>()
        scope.launch {
            state.postValue(State.working())
            try {
                db.currencyDao().deleteCurrencies(currencies)
                state.postValue(State.finished())
            } catch(ex: Exception) {
                state.postValue(State.error(ex))
            }
        }
        return state
    }

    override fun clearCurrencies(): LiveData<State> {
        val state = MutableLiveData<State>()
        scope.launch {
            state.postValue(State.working())
            try {
                db.currencyDao().clearCurrencies()
                state.postValue(State.finished())
            } catch(ex: Exception) {
                state.postValue(State.error(ex))
            }
        }
        return state
    }

    override fun getCurrenciesList():Result<List<Currency>> {
        var list:LiveData<List<Currency>>
        val state = MutableLiveData<State>()
        list = getCurrenciesListInner(state)
        return Result(
            list,
            state,
            {list = getCurrenciesListInner(state)},
            {list = getCurrenciesListInner(state)}
        )
    }

    private fun getCurrenciesListInner(
        state:MutableLiveData<State>
    ):LiveData<List<Currency>>{
        state.postValue(State.working())
        return try {
            val list = db.currencyDao().getCurrenciesList()
            state.postValue(State.finished())
            list
        } catch(ex:Exception) {
            state.postValue(State.error(ex))
            MutableLiveData()
        }
    }

}