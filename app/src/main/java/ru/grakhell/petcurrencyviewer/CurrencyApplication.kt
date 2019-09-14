package ru.grakhell.petcurrencyviewer

import android.app.Application
import com.facebook.soloader.SoLoader

class CurrencyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)
    }
}