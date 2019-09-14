package ru.grakhell.petcurrencyviewer.data

import androidx.lifecycle.LiveData

data class Result<T>(
    val data: LiveData<T>,
    val state: LiveData<State>,
    val refresh: (()-> Unit),
    val retry: (() -> Unit)
)