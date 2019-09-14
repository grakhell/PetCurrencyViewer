package ru.grakhell.petcurrencyviewer.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.grakhell.petcurrencyviewer.vo.Currency
import ru.grakhell.petcurrencyviewer.vo.CurrencyList

@Database(
    entities = [Currency::class, CurrencyList::class],
    version = 2,
    exportSchema = false
)
abstract class CurrencyDatabase:RoomDatabase() {
    abstract fun currencyDao():CurrencyDao
    abstract fun listDao():ListDao

    companion object {
        @Volatile private var INSTANCE:CurrencyDatabase? = null

        @Synchronized fun getInstance(context: Context): CurrencyDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?: Room.databaseBuilder(
                    context,
                    CurrencyDatabase::class.java,
                    "repo.db")
                    .fallbackToDestructiveMigration()
                    .build().also {
                        INSTANCE = it
                    }
            }
        }
    }
}