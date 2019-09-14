package ru.grakhell.petcurrencyviewer

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.grakhell.petcurrencyviewer.data.RepositoryFactory
import ru.grakhell.petcurrencyviewer.viewmodel.CurrenciesViewModel

class ViewModelFactory(
    private val applicationContext:Context
): ViewModelProvider.NewInstanceFactory() {

    @SuppressWarnings("Unchecked")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(CurrenciesViewModel::class.java) -> {
                    CurrenciesViewModel(
                        RepositoryFactory.getListRepository(applicationContext),
                        RepositoryFactory.getCurrenciesRepository(applicationContext)
                    )
                }
                else -> {
                    super.create(modelClass)
                }
            }
        } as T
}