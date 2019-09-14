package ru.grakhell.petcurrencyviewer.ui.activity

import android.graphics.Color
import com.facebook.litho.Border
import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.Row
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaEdge
import ru.grakhell.petcurrencyviewer.vo.CurrencyList

@LayoutSpec
object CurrencySubtitleComponentSpec {

    @OnCreateLayout
    fun onCreate(
        context:ComponentContext,
        @Prop item: CurrencyList?
    ):Component {
        return Column.create(context)
            .child(
                Row.create(context)
                    .child(
                        Text.create(context)
                            .text(item?.name?:"")
                            .textSizeSp(16f)
                            .paddingDip(YogaEdge.ALL, 4f)
                            .build()
                    )
                    .child(
                        Text.create(context)
                            .text(item?.date?:"")
                            .textSizeSp(16f)
                            .paddingDip(YogaEdge.ALL, 4f)
                            .build()
                    )
                    .border(Border.create(context)
                        .widthDip(YogaEdge.BOTTOM, 1f)
                        .color(YogaEdge.BOTTOM, Color.DKGRAY)
                        .build())
                    .build()
            )
            .build()
    }
}