package com.emilancius.discount

import com.emilancius.InputEntry
import com.emilancius.OutputEntry
import com.emilancius.PackageType
import com.emilancius.Pricing
import com.emilancius.Settings.MAX_DISCOUNTS_PER_MONTH
import com.emilancius.extensions.DoubleExtensions.round

/**
 * Discount type, that is applied to every "S" package.
 */
class SPackageDiscount : Discount {

    override fun apply(entry: InputEntry, previousEntries: List<OutputEntry>) =
        if (canApply(entry, previousEntries)) {
            val discount = Pricing.priceBy(entry.carrier, entry.packageType) - Pricing.minPriceByPackageType(PackageType.SMALL)
            val unused = MAX_DISCOUNTS_PER_MONTH - discountsSum(previousEntries)
            (if (discount > unused) unused else discount).round()
        } else {
            .0
        }

    override fun canApply(entry: InputEntry, previousEntries: List<OutputEntry>) =
        entry.packageType == PackageType.SMALL && discountsSum(previousEntries) < MAX_DISCOUNTS_PER_MONTH

    private fun discountsSum(entries: List<OutputEntry>) = entries.sumByDouble { it.discount }
}