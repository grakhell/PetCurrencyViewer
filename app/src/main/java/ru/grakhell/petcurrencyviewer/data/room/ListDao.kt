package ru.grakhell.petcurrencyviewer.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.grakhell.petcurrencyviewer.vo.CurrencyList

@Dao
abstract class ListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertListData(vararg data: CurrencyList)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateListData(vararg data: CurrencyList)

    @Delete
    abstract fun deleteListData(vararg data: CurrencyList)

    @Query("DELETE FROM currencies")
    abstract fun clearListData()

    @Query("SELECT * FROM currencies")
    abstract fun getListData(): LiveData<CurrencyList>
}