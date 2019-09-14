package ru.grakhell.petcurrencyviewer.ui.activity

import com.facebook.litho.annotations.FromEvent
import com.facebook.litho.annotations.OnEvent
import com.facebook.litho.annotations.Prop
import com.facebook.litho.sections.Children
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.annotations.GroupSectionSpec
import com.facebook.litho.sections.annotations.OnCreateChildren
import com.facebook.litho.sections.common.DataDiffSection
import com.facebook.litho.sections.common.OnCheckIsSameContentEvent
import com.facebook.litho.sections.common.OnCheckIsSameItemEvent
import com.facebook.litho.sections.common.RenderEvent
import com.facebook.litho.sections.common.SingleComponentSection
import com.facebook.litho.widget.ComponentRenderInfo
import com.facebook.litho.widget.RenderInfo
import ru.grakhell.petcurrencyviewer.vo.Currency
import ru.grakhell.petcurrencyviewer.vo.CurrencyList


@GroupSectionSpec
object CurrencyGroupSectionSpec {

    @OnCreateChildren
    fun create(
        c:SectionContext,
        @Prop data:CurrencyList?,
        @Prop list:List<Currency>?
    ):Children {
        return Children.create()
            .child(SingleComponentSection.create(c)
                .component(CurrencySubtitleComponent.create(c)
                    .item(data)
                    .build()))
            .child(DataDiffSection.create<Currency>(c)
                .data(list)
                .renderEventHandler(CurrencyGroupSection.onRenderList(c))
                .onCheckIsSameItemEventHandler(CurrencyGroupSection.isItemsAreSame(c))
                .onCheckIsSameContentEventHandler(CurrencyGroupSection.isItemsContentAreSame(c))
                .build()
            )
            .build()
    }

    @OnEvent(RenderEvent::class)
    fun onRenderList(
        c: SectionContext,
        @FromEvent model:Currency
    ): RenderInfo =
        ComponentRenderInfo.create()
            .component(CurrencyItemComponent.create(c)
                .item(model)
                .build()
            ).build()

    @OnEvent(OnCheckIsSameItemEvent::class)
    fun isItemsAreSame(
        c: SectionContext,
        @FromEvent previousItem: Currency,
        @FromEvent nextItem: Currency
    ):Boolean = previousItem === nextItem

    @OnEvent(OnCheckIsSameContentEvent::class)
    fun isItemsContentAreSame(
        c: SectionContext,
        @FromEvent previousItem: Currency?,
        @FromEvent nextItem: Currency?
    ):Boolean =
        if (previousItem == null) {
            nextItem == null
        } else {
            previousItem.numCode == nextItem?.numCode
        }
}