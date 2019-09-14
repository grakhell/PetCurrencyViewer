package ru.grakhell.petcurrencyviewer.ui.activity

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.OnEvent
import com.facebook.litho.annotations.Prop
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.widget.ListRecyclerConfiguration
import com.facebook.litho.sections.widget.RecyclerCollectionComponent
import com.facebook.litho.sections.widget.RecyclerConfiguration
import com.facebook.litho.widget.PTRRefreshEvent
import ru.grakhell.petcurrencyviewer.viewmodel.CurrenciesViewModel

@LayoutSpec
object CurrencyRecyclerComponentSpec {

    @OnCreateLayout
    fun onCreate(
        context: ComponentContext,
        @Prop owner: LifecycleOwner,
        @Prop vm: CurrenciesViewModel
    ): Component {
        val config: RecyclerConfiguration = ListRecyclerConfiguration.create()
            .orientation(LinearLayoutManager.VERTICAL)
            .reverseLayout(false)
            .build()
        return RecyclerCollectionComponent
            .create(context)
            .recyclerConfiguration(config)
            .pTRRefreshEventHandler(CurrencyRecyclerComponent.onPull(context))
            .section(
                RecyclerRootSection.create(SectionContext(context))
                    .owner(owner)
                    .viewModel(vm)
                    .build()
            )
            .canMeasureRecycler(true)
            .build()
    }

    @OnEvent(PTRRefreshEvent::class)
    fun onPull(c:ComponentContext, @Prop callback: ()-> Boolean):Boolean {
        return callback.invoke()
    }
}