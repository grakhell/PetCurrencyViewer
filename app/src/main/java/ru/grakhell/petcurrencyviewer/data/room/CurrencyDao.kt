package ru.grakhell.petcurrencyviewer.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import ru.grakhell.petcurrencyviewer.vo.Currency

@Dao
abstract class CurrencyDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun _insertCurrencies(vararg currency: Currency)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun _updateCurrencies(vararg currencies: Currency)

    @Delete
    protected abstract fun _deleteCurrencies(vararg currency: Currency)

    @Transaction
    open fun insertCurrencies(currency: List<Currency>) {
        currency.forEach {
            _insertCurrencies(it)
        }
    }

    @Transaction
    open fun updateCurrencies(currency: List<Currency>) {
        _updateCurrencies(*currency.toTypedArray())
    }

    @Transaction
    open fun deleteCurrencies(currency: List<Currency>) {
        _deleteCurrencies(*currency.toTypedArray())
    }

    @Query("DELETE FROM currency")
    abstract fun clearCurrencies()

    @Query("SELECT * FROM currency")
    abstract fun getCurrenciesList():LiveData<List<Currency>>

}