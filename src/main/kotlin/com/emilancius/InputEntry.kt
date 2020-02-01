package com.emilancius

import com.emilancius.discount.Discount
import java.time.LocalDate

data class InputEntry(
    override val date: LocalDate,
    override val packageType: PackageType,
    override val carrier: Carrier
) : Entry {

    companion object {
        fun create(line: String): InputEntry {
            val parameters = line.split(" ")
            return InputEntry(
                LocalDate.parse(parameters[0]),
                PackageType.byCode(parameters[1])!!,
                Carrier.byCode(parameters[2])!!
            )
        }
    }

    fun process(discounts: Array<Discount>, previousEntries: List<OutputEntry>): OutputEntry {
        val discount = discounts.map { it.apply(this, previousEntries) }.sumByDouble { it }
        return OutputEntry(
            date,
            packageType,
            carrier,
            price = Pricing.priceBy(carrier, packageType) - discount,
            discount = discount
        )
    }
}
