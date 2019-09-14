package ru.grakhell.petcurrencyviewer.ui.activity

import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.Row
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.Card
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaEdge
import ru.grakhell.petcurrencyviewer.vo.Currency

@LayoutSpec
object CurrencyItemComponentSpec {

    @OnCreateLayout
    fun onCreateLayout(
        c:ComponentContext,
        @Prop item:Currency?
    ):Component {
        val column = Column.create(c)
            .paddingDip(YogaEdge.ALL, 8f)
            .child(
                Row.create(c)
                    .child(
                        Text.create(c)
                            .text(item?.charCode)
                            .textSizeSp(16f)
                            .paddingDip(YogaEdge.HORIZONTAL,4f)
                            .build()
                    )
                    .child(
                        Text.create(c)
                            .text(item?.value)
                            .textSizeSp(16f)
                            .paddingDip(YogaEdge.HORIZONTAL,4f)
                            .build())
                    .paddingDip(YogaEdge.VERTICAL, 2f)
                    .build()
            )
            .child(
                Row.create(c)
                    .child(
                        Text.create(c)
                            .text(item?.nominal.toString())
                            .textSizeSp(12f)
                            .paddingDip(YogaEdge.HORIZONTAL,4f)
                            .build())
                    .child(
                        Text.create(c)
                            .text(item?.name)
                            .textSizeSp(12f)
                            .paddingDip(YogaEdge.HORIZONTAL,4f)
                            .build()
                    )
                    .paddingDip(YogaEdge.VERTICAL, 2f)
                    .build()
            )
            .build()
        return Card.create(c)
            .content(column)
            .cornerRadiusDip(6f)
            .elevationDip(2f)
            .marginDip(YogaEdge.ALL, 1f)
            .build()
    }
}