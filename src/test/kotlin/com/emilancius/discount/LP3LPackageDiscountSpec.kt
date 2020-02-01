package com.emilancius.discount

import com.emilancius.Carrier
import com.emilancius.InputEntry
import com.emilancius.OutputEntry
import com.emilancius.PackageType
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LP3LPackageDiscountSpec {

    @Test
    fun `returns "true" in case entry's package is 3rd "L" package, carried by "LA_POSTE" current month & current month's discounts does not reach it's limit`() {
        val discount = LP3LPackageDiscount()
        val previousEntries = listOf(
            OutputEntry(LocalDate.parse("2020-01-03"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-04"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0)
        )

        assertTrue(
            discount.canApply(
                InputEntry(LocalDate.parse("2020-01-13"), PackageType.LARGE, Carrier.LA_POSTE),
                previousEntries
            )
        )
    }

    @Test
    fun `returns "false" in case entry's package is not 3rd "L" package, carried by "LA_POSTE" or current month's discounts reach it's limit`() {
        val discount = LP3LPackageDiscount()
        var previousEntries = listOf(
            OutputEntry(LocalDate.parse("2020-01-03"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-04"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0)
        )

        assertFalse(
            discount.canApply(
                InputEntry(LocalDate.parse("2020-01-13"), PackageType.LARGE, Carrier.MONDIAL_RELAY),
                previousEntries
            )
        )

        assertFalse(
            discount.canApply(
                InputEntry(LocalDate.parse("2020-01-13"), PackageType.SMALL, Carrier.LA_POSTE),
                previousEntries
            )
        )

        previousEntries = previousEntries + listOf(
            OutputEntry(LocalDate.parse("2020-01-04"), PackageType.LARGE, Carrier.LA_POSTE, .0, 6.9),
            OutputEntry(LocalDate.parse("2020-01-05"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-05"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-07"), PackageType.LARGE, Carrier.LA_POSTE, .0, 6.9),
            OutputEntry(LocalDate.parse("2020-01-07"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-07"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0)
        )

        assertFalse(
            discount.canApply(
                InputEntry(LocalDate.parse("2020-01-14"), PackageType.LARGE, Carrier.LA_POSTE),
                previousEntries
            )
        )
    }

    @Test
    fun `calculates discount, in case discount can be applied`() {
        val discount = LP3LPackageDiscount()
        var previousEntries = listOf(
            OutputEntry(LocalDate.parse("2020-01-03"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-04"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0)
        )

        assertEquals(
            6.9,
            discount.apply(
                InputEntry(LocalDate.parse("2020-01-16"), PackageType.LARGE, Carrier.LA_POSTE),
                previousEntries
            )
        )

        previousEntries = previousEntries + listOf(
            OutputEntry(LocalDate.parse("2020-01-04"), PackageType.SMALL, Carrier.MONDIAL_RELAY, 1.5, .5),
            OutputEntry(LocalDate.parse("2020-01-04"), PackageType.SMALL, Carrier.MONDIAL_RELAY, 1.5, .5),
            OutputEntry(LocalDate.parse("2020-01-04"), PackageType.SMALL, Carrier.MONDIAL_RELAY, 1.5, .5),
            OutputEntry(LocalDate.parse("2020-01-04"), PackageType.SMALL, Carrier.MONDIAL_RELAY, 1.5, .5),
            OutputEntry(LocalDate.parse("2020-01-04"), PackageType.SMALL, Carrier.MONDIAL_RELAY, 1.5, .5),
            OutputEntry(LocalDate.parse("2020-01-04"), PackageType.SMALL, Carrier.MONDIAL_RELAY, 1.5, .5),
            OutputEntry(LocalDate.parse("2020-01-04"), PackageType.SMALL, Carrier.MONDIAL_RELAY, 1.5, .5)
        )

        assertEquals(
            6.5,
            discount.apply(
                InputEntry(LocalDate.parse("2020-01-08"), PackageType.LARGE, Carrier.LA_POSTE),
                previousEntries
            )
        )
    }

    @Test
    fun `returns 0, in case discount cannot be applied`() {
        val discount = LP3LPackageDiscount()
        var previousEntries = listOf(
            OutputEntry(LocalDate.parse("2020-01-03"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-04"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0)
        )

        assertEquals(
            .0,
            discount.apply(
                InputEntry(LocalDate.parse("2020-01-01"), PackageType.LARGE, Carrier.MONDIAL_RELAY),
                previousEntries
            )
        )
        assertEquals(
            .0,
            discount.apply(
                InputEntry(LocalDate.parse("2020-01-14"), PackageType.SMALL, Carrier.LA_POSTE),
                previousEntries
            )
        )
        assertEquals(
            .0,
            discount.apply(
                InputEntry(LocalDate.parse("2020-01-14"), PackageType.LARGE, Carrier.LA_POSTE),
                emptyList()
            )
        )

        previousEntries = previousEntries + listOf(
            OutputEntry(LocalDate.parse("2020-01-04"), PackageType.LARGE, Carrier.LA_POSTE, .0, 6.9),
            OutputEntry(LocalDate.parse("2020-01-05"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-05"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-07"), PackageType.LARGE, Carrier.LA_POSTE, .0, 6.9),
            OutputEntry(LocalDate.parse("2020-01-07"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-07"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0)
        )

        assertEquals(
            .0,
            discount.apply(
                InputEntry(LocalDate.parse("2020-01-14"), PackageType.SMALL, Carrier.LA_POSTE),
                previousEntries
            )
        )
    }
}