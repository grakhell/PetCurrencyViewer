package ru.grakhell.petcurrencyviewer.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import ru.grakhell.petcurrencyviewer.ViewModelFactory
import ru.grakhell.petcurrencyviewer.util.setupSnackBar
import ru.grakhell.petcurrencyviewer.viewmodel.CurrenciesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CurrenciesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(applicationContext)
        ).get(CurrenciesViewModel::class.java)

        val callback: ()->Boolean = {
            viewModel.onSwipedToRefresh(true)
            false
        }

        val component = CurrencyRecyclerComponent.create(ComponentContext(this))
            .callback (callback)
            .owner(this)
            .vm(viewModel)
            .build()

        val view =  LithoView.create(this,
            component
            ).apply {
                setupSnackBar(
                this@MainActivity,
                viewModel.snackbarMessage
                )
        }
        setContentView(
            view
        )
        /**
        viewModel.listData.observe(this, Observer {
                view.setComponent(
                    CurrencyActivityComponent.create(ComponentContext(this))
                        .callback (callback)
                        .data(it)
                        .list(viewModel.currencies.value)
                        .build()
                )
        })
        viewModel.currencies.observe(this, Observer {
                view.setComponent(
                    CurrencyActivityComponent.create(ComponentContext(this))
                        .callback (callback)
                        .data(viewModel.listData.value)
                        .list(it)
                        .build()
                )
        })
        **/
        viewModel.onSwipedToRefrashEvent.observe(this, Observer {
            if (it) {
                viewModel.updateData()
            }
        })
    }
}
