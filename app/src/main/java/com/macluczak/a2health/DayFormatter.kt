package com.macluczak.a2health

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter

class DayFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float, axis: AxisBase?): String =
        when (Math.round(value)) {
            1 -> "Mon"
            2 -> "Tue"
            3 -> "Wen"
            4 -> "Thu"
            5 -> "Fri"
            6 -> "Sat"
            7 -> "Sun"
            else -> ""
        }
}