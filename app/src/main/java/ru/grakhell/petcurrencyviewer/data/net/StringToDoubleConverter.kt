package ru.grakhell.petcurrencyviewer.data.net

import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

class StringToDoubleConverter: Converter<Double> {

    override fun read(node: InputNode?): Double {
        var float = 0.0
        node?.let {
            val data = node.value
            float = (data.replace(",",".")).toDouble()
        }
        return float
    }

    override fun write(node: OutputNode?, value: Double?) {
        node?.value = value.toString().replace(".",",")
    }
}