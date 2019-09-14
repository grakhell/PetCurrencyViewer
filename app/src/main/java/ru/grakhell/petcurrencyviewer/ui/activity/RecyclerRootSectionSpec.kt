package ru.grakhell.petcurrencyviewer.ui.activity

import androidx.lifecycle.LifecycleOwner
import com.facebook.litho.StateValue
import com.facebook.litho.annotations.FromEvent
import com.facebook.litho.annotations.OnEvent
import com.facebook.litho.annotations.OnUpdateState
import com.facebook.litho.annotations.Param
import com.facebook.litho.annotations.Prop
import com.facebook.litho.annotations.State
import com.facebook.litho.sections.Children
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.annotations.GroupSectionSpec
import com.facebook.litho.sections.annotations.OnBindService
import com.facebook.litho.sections.annotations.OnCreateChildren
import com.facebook.litho.sections.annotations.OnCreateService
import com.facebook.litho.sections.annotations.OnUnbindService
import ru.grakhell.petcurrencyviewer.viewmodel.CurrenciesViewModel
import ru.grakhell.petcurrencyviewer.vo.Currency
import ru.grakhell.petcurrencyviewer.vo.CurrencyList
import ru.grakhell.petcurrencyviewer.vo.DataUpdatedEvent
import ru.grakhell.petcurrencyviewer.vo.ListUpdatedEvent

@GroupSectionSpec(events = [ListUpdatedEvent::class, DataUpdatedEvent::class])
object RecyclerRootSectionSpec {

    @OnCreateChildren
    fun onCreate(
        c:SectionContext,
        @State data: CurrencyList?,
        @State list: List<Currency>?
    ): Children = Children.create()
        .child(CurrencyGroupSection.create(c)
            .list(list)
            .data(data)
            .build())
        .build()


    @OnCreateService
    fun createService(
        c:SectionContext,
        @Prop viewModel: CurrenciesViewModel,
        @Prop owner: LifecycleOwner
    ):DataLoader {
        return DataLoader(owner, viewModel)
    }

    @OnBindService
    fun bind(
        c:SectionContext,
        service:DataLoader
    ) {
        service.registerListEvent(RecyclerRootSection.onListUpdated(c))
        service.registerDataEvent(RecyclerRootSection.onDataUpdated(c))
        service.startListening()
    }

    @OnUnbindService
    fun unbind(
        c:SectionContext,
        service:DataLoader
    ) {
        service.stopListening()
        service.unregisterListEvent()
        service.unregisterDataEvent()
    }


    @OnEvent(ListUpdatedEvent::class)
    fun onListUpdated(
        c:SectionContext,
        @FromEvent list: List<Currency>?) {
        RecyclerRootSection.updateList(c, list)
    }

    @OnEvent(DataUpdatedEvent::class)
    fun onDataUpdated(
        c:SectionContext,
        @FromEvent data: CurrencyList?) {
        RecyclerRootSection.updateData(c, data)
    }

    @OnUpdateState
    fun updateList(
        list:StateValue<List<Currency>?>,
        @Param newValue: List<Currency>?
    ) {
        list.set(newValue)
    }

    @OnUpdateState
    fun updateData(
        data:StateValue<CurrencyList?>,
        @Param newValue: CurrencyList?
    ) {
        data.set(newValue)
    }
}