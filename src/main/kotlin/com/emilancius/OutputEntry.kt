package com.emilancius

import com.emilancius.extensions.DoubleExtensions.format
import java.time.LocalDate

data class OutputEntry(
    override val date: LocalDate,
    override val packageType: PackageType,
    override val carrier: Carrier,
    val price: Double,
    val discount: Double
) : Entry {

    override fun toString() =
        "$date ${packageType.code} ${carrier.code} ${price.format()} ${if (discount == .0) "-" else discount.format()}"
}
