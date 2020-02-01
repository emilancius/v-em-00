package com.emilancius.extensions

import java.math.BigDecimal
import java.math.RoundingMode

object DoubleExtensions {

    fun Double.format(scale: Int = 2): String = "%.${scale}f".format(this)

    fun Double.round(scale: Int = 2): Double = BigDecimal(this).setScale(scale, RoundingMode.HALF_EVEN).toDouble()
}