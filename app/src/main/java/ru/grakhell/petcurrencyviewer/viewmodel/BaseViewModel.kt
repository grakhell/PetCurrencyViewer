package ru.grakhell.petcurrencyviewer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ru.grakhell.petcurrencyviewer.R
import ru.grakhell.petcurrencyviewer.SnackBarMessage
import ru.grakhell.petcurrencyviewer.data.State
import ru.grakhell.petcurrencyviewer.data.Status
import ru.grakhell.petcurrencyviewer.util.toSingleEvent

abstract class BaseViewModel:ViewModel() {

    private val viewModelJob = SupervisorJob()
    protected val modelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _snackbarMessage = MutableLiveData<SnackBarMessage>()
    protected val _state = MediatorLiveData<State>()

    protected var retry: () -> Unit = {}

    val snackbarMessage = _snackbarMessage.toSingleEvent()
    val state: LiveData<State> = _state

    protected fun setState(stat: LiveData<State>, action: () -> Unit) {
        _state.addSource(stat) {
            if (it.status == Status.FAILED) {
                retry = action
                showSnackBarMessage(
                    SnackBarMessage(
                        it.msg?:"",
                        Snackbar.LENGTH_LONG,
                        true,
                        R.string.error_action
                    ) {retry.invoke()}
                )
            }
            _state.value = it
        }
    }

    protected fun showSnackBarMessage(message: SnackBarMessage) {
        _snackbarMessage.value = message
    }
}