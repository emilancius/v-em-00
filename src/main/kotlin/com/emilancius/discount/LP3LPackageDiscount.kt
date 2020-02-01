package com.emilancius.discount

import com.emilancius.*
import com.emilancius.Settings.MAX_DISCOUNTS_PER_MONTH
import com.emilancius.extensions.DoubleExtensions.round

/**
 * Discount type, that is applied to every 3rd "L" package by LA POSTE carrier once a month.
 */
class LP3LPackageDiscount : Discount {

    override fun apply(entry: InputEntry, previousEntries: List<OutputEntry>) =
        if (canApply(entry, previousEntries)) {
            val discount = Pricing.priceBy(entry.carrier, entry.packageType)
            val unused = MAX_DISCOUNTS_PER_MONTH - discountsSum(previousEntries)
            (if (discount > unused) unused else discount).round() // to avoid values like 0.099999999999...
        } else {
            .0
        }

    override fun canApply(entry: InputEntry, previousEntries: List<OutputEntry>) =
        entry.packageType == PackageType.LARGE
                && entry.carrier == Carrier.LA_POSTE
                && previousEntries.count { it.packageType == PackageType.LARGE && it.carrier == Carrier.LA_POSTE } == 2
                && discountsSum(previousEntries) < MAX_DISCOUNTS_PER_MONTH

    private fun discountsSum(entries: List<OutputEntry>) = entries.sumByDouble { it.discount }
}