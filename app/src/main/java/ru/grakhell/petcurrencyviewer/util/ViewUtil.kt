package ru.grakhell.petcurrencyviewer.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.hadilq.liveevent.LiveEvent
import ru.grakhell.petcurrencyviewer.SnackBarMessage

class ViewUtil {
    companion object {
        fun pxToDp(px: Float): Float {
            val densityDPI = Resources.getSystem().displayMetrics.densityDpi
            return px / (densityDPI / 160f)
        }

        fun dpToPx(dp: Int): Int {
            val density = Resources.getSystem().displayMetrics.density
            return Math.round(dp*density)
        }

        fun hideKeyboard(activity: Activity) {
            val imm =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
        }
    }
}

fun View.showSnackBarWithAction(
    text:String,
    length: Int,
    actionText:String,
    action: (() -> Unit)
) {
    val bar = Snackbar.make(this, text, length)
        bar.apply {
            setAction(actionText) {
                action.invoke()
                this.dismiss()
            }
        }.show()
}

fun View.showSnackBar(
    text:String,
    length: Int
) {
    Snackbar.make(this, text, length)
        .show()
}

fun View.setupSnackBar(
    owner: LifecycleOwner,
    event: LiveEvent<SnackBarMessage>
) {
    event.observe(owner, Observer {
        if (it.withAction) {
            showSnackBarWithAction(
                it.message,
                it.length,
                context.getString(it.actionText),
                it.action)
        } else {
            showSnackBar(it.message,it.length)
        }
    })
}

fun <T> LiveData<T>.toSingleEvent(): LiveEvent<T> {
    val result = LiveEvent<T>()
    result.addSource(this) {
        result.value = it
    }
    return result
}