package ru.grakhell.petcurrencyviewer.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.grakhell.petcurrencyviewer.data.net.Cbr
import ru.grakhell.petcurrencyviewer.data.room.CurrencyDatabase
import ru.grakhell.petcurrencyviewer.vo.CurrencyList
import java.lang.Exception

class ListRepositoryImpl(
    private val net:Cbr,
    private val db:CurrencyDatabase
): ListRepository {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun addListData(list: CurrencyList): LiveData<State> {
        val state = MutableLiveData<State>()
        scope.launch {
            state.postValue(State.working())
            try {
                db.listDao().insertListData(list)
                state.postValue(State.finished())
            } catch(ex:Exception) {
                state.postValue(State.error(ex))
            }
        }
        return state
    }

    override fun updateListData(list: CurrencyList): LiveData<State> {
        val state = MutableLiveData<State>()
        scope.launch {
            state.postValue(State.working())
            try {
                db.listDao().updateListData(list)
                state.postValue(State.finished())
            } catch(ex:Exception) {
                state.postValue(State.error(ex))
            }
        }
        return state
    }

    override fun deleteListData(list: CurrencyList): LiveData<State> {
        val state = MutableLiveData<State>()
        scope.launch {
            state.postValue(State.working())
            try {
                db.listDao().deleteListData(list)
                state.postValue(State.finished())
            } catch(ex:Exception) {
                state.postValue(State.error(ex))
            }
        }
        return state
    }

    override fun clearListData(): LiveData<State> {
        val state = MutableLiveData<State>()
        scope.launch {
            state.postValue(State.working())
            try {
                db.listDao().clearListData()
                state.postValue(State.finished())
            } catch(ex:Exception) {
                state.postValue(State.error(ex))
            }
        }
        return state
    }

    override fun getListData(): Result<CurrencyList> {
        var list:LiveData<CurrencyList>
        val state = MutableLiveData<State>()
        list = getListDataInner(state)
        return Result(
            list,
            state,
            {list = getListDataInner(state)},
            {list = getListDataInner(state)}
        )
    }

    private fun getListDataInner(
        state:MutableLiveData<State>
    ):LiveData<CurrencyList> {
        state.postValue(State.working())
        return try {
            val list = MediatorLiveData<CurrencyList>()
            list.addSource(db.listDao().getListData()) {
                list.value = it
            }
            getListDataAsync(state)
            list
        } catch(ex:Exception) {
            state.postValue(State.error(ex))
            MutableLiveData()
        }
    }

    private fun getListDataAsync(
        state:MutableLiveData<State>
    ) {
        scope.launch {
            val source = net.getCurrenciesList().await()
            if (source.isSuccessful) {
                source.body()?.let {
                    db.listDao().clearListData()
                    db.currencyDao().clearCurrencies()
                    db.listDao().insertListData(it)
                    db.currencyDao().insertCurrencies(it.currencies)
                }
            }
            state.postValue(State.finished())
        }

    }
}