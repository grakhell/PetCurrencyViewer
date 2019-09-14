package ru.grakhell.petcurrencyviewer.data.net

class CbrFactory {
    companion object {
        fun getInstance():Cbr = ServiceFactory.createNetService("https://www.cbr.ru", Cbr::class)
    }
}