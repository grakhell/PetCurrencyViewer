package ru.grakhell.petcurrencyviewer.ui.activity

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.facebook.litho.EventHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.grakhell.petcurrencyviewer.viewmodel.CurrenciesViewModel
import ru.grakhell.petcurrencyviewer.vo.Currency
import ru.grakhell.petcurrencyviewer.vo.CurrencyList
import ru.grakhell.petcurrencyviewer.vo.DataUpdatedEvent
import ru.grakhell.petcurrencyviewer.vo.ListUpdatedEvent

class DataLoader(
    private val owner: LifecycleOwner,
    private val viewModel:CurrenciesViewModel
) {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    private var listUpdateEventHandler:EventHandler<ListUpdatedEvent>? = null
    private var dataUpdateEventHandler:EventHandler<DataUpdatedEvent>? = null

    private val listObserver:Observer<List<Currency>> = Observer{
        val list = ListUpdatedEvent().apply {
            list = it
        }
        listUpdateEventHandler?.dispatchEvent(list)
    }
    private val dataObserver:Observer<CurrencyList> = Observer {
        val data = DataUpdatedEvent().apply {
            data = it
        }
        dataUpdateEventHandler?.dispatchEvent(data)
    }


    fun registerDataEvent(
        dataHandler:EventHandler<DataUpdatedEvent>
    ) {
        dataUpdateEventHandler = dataHandler
    }

    fun unregisterDataEvent() {
        dataUpdateEventHandler = null
    }

    fun registerListEvent(
        listHandler:EventHandler<ListUpdatedEvent>
    ) {
        listUpdateEventHandler = listHandler
    }

    fun unregisterListEvent() {
        listUpdateEventHandler = null
    }

    fun startListening() {
        scope.launch {
            viewModel.currencies.observe(owner, listObserver)
            viewModel.listData.observe(owner, dataObserver)
        }
    }

    fun stopListening() {
        scope.launch {
            viewModel.currencies.removeObserver(listObserver)
            viewModel.listData.removeObserver(dataObserver)
        }
    }

}