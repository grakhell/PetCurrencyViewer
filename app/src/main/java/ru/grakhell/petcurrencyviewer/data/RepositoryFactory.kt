package ru.grakhell.petcurrencyviewer.data

import android.content.Context
import ru.grakhell.petcurrencyviewer.data.net.CbrFactory
import ru.grakhell.petcurrencyviewer.data.room.CurrencyDatabase

class RepositoryFactory {
     companion object {
         private var ListInstance:ListRepository? = null
         private var CurrenciesInstance:CurrencyRepository? = null

         @Synchronized
         fun getListRepository(applicationContext: Context):ListRepository {
             return ListInstance?: synchronized(this) {
                 ListInstance?: ListRepositoryImpl(
                     CbrFactory.getInstance(),
                     CurrencyDatabase.getInstance(applicationContext)
                 ).also {
                     ListInstance = it
                 }
             }
         }

         @Synchronized
         fun getCurrenciesRepository(applicationContext: Context):CurrencyRepository {
             return CurrenciesInstance?: synchronized(this){
                 CurrenciesInstance?: CurrencyRepositoryImpl(
                     CbrFactory.getInstance(),
                     CurrencyDatabase.getInstance(applicationContext)
                 ).also {
                     CurrenciesInstance = it
                 }
             }
         }
     }
}