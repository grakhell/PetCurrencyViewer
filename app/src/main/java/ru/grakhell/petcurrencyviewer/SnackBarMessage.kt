package ru.grakhell.petcurrencyviewer

import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

data class SnackBarMessage(
    var message: String,
    var length: Int = Snackbar.LENGTH_SHORT,
    var withAction:Boolean = false,
    @StringRes var actionText: Int = 0,
    var action: (()->Unit) = {}
)