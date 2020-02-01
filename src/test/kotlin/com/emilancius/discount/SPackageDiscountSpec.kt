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

class SPackageDiscountSpec {

    @Test
    fun `returns "true" in case entry's package is of type "S" & current month's discounts does not reach it's limit`() {
        val discount = SPackageDiscount()
        val previousEntries = listOf(
            OutputEntry(LocalDate.parse("2020-01-01"), PackageType.SMALL, Carrier.MONDIAL_RELAY, 1.5, 0.5),
            OutputEntry(LocalDate.parse("2020-01-03"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-04"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-07"), PackageType.LARGE, Carrier.LA_POSTE, .0, 6.9),
            OutputEntry(LocalDate.parse("2020-01-07"), PackageType.SMALL, Carrier.LA_POSTE, 1.5, 0.5),
            OutputEntry(LocalDate.parse("2020-01-09"), PackageType.SMALL, Carrier.MONDIAL_RELAY, 1.5, 0.5),
            OutputEntry(LocalDate.parse("2020-01-10"), PackageType.SMALL, Carrier.LA_POSTE, 1.5, 0.5)
        )

        assertTrue(
            discount.canApply(
                InputEntry(LocalDate.parse("2020-01-13"), PackageType.SMALL, Carrier.MONDIAL_RELAY),
                previousEntries
            )
        )
    }

    @Test
    fun `returns "false" in case entry's package is not of type "S" or current month's discounts reach it's limit`() {
        val discount = SPackageDiscount()
        val previousEntries = listOf(
            OutputEntry(LocalDate.parse("2020-01-03"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-04"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-07"), PackageType.LARGE, Carrier.LA_POSTE, .0, 6.9),
            OutputEntry(LocalDate.parse("2020-01-10"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-11"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-13"), PackageType.LARGE, Carrier.LA_POSTE, .0, 6.9)
        )

        assertFalse(
            discount.canApply(
                InputEntry(LocalDate.parse("2020-01-13"), PackageType.LARGE, Carrier.LA_POSTE),
                emptyList()
            )
        )

        assertFalse(
            discount.canApply(
                InputEntry(LocalDate.parse("2020-01-14"), PackageType.SMALL, Carrier.LA_POSTE),
                previousEntries
            )
        )
    }

    @Test
    fun `calculates discount, in case discount can be applied`() {
        val discount = SPackageDiscount()
        val previousEntries = listOf(
            OutputEntry(LocalDate.parse("2020-01-03"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-04"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-07"), PackageType.LARGE, Carrier.LA_POSTE, .0, 6.9),
            OutputEntry(LocalDate.parse("2020-01-07"), PackageType.SMALL, Carrier.MONDIAL_RELAY, 1.5, 0.5),
            OutputEntry(LocalDate.parse("2020-01-08"), PackageType.SMALL, Carrier.MONDIAL_RELAY, 1.5, 0.5),
            OutputEntry(LocalDate.parse("2020-01-08"), PackageType.SMALL, Carrier.MONDIAL_RELAY, 1.5, 0.5),
            OutputEntry(LocalDate.parse("2020-01-10"), PackageType.SMALL, Carrier.MONDIAL_RELAY, 1.5, 0.5),
            OutputEntry(LocalDate.parse("2020-01-13"), PackageType.SMALL, Carrier.MONDIAL_RELAY, 1.5, 0.5),
            OutputEntry(LocalDate.parse("2020-01-13"), PackageType.SMALL, Carrier.MONDIAL_RELAY, 1.5, 0.5)
        )

        assertEquals(
            0.1,
            discount.apply(
                InputEntry(LocalDate.parse("2020-01-16"), PackageType.SMALL, Carrier.MONDIAL_RELAY),
                previousEntries
            )
        )

        assertEquals(
            .0,
            discount.apply(
                InputEntry(LocalDate.parse("2020-01-16"), PackageType.SMALL, Carrier.LA_POSTE),
                previousEntries
            )
        )

        assertEquals(
            0.5,
            discount.apply(
                InputEntry(LocalDate.parse("2020-01-01"), PackageType.SMALL, Carrier.MONDIAL_RELAY),
                emptyList()
            )
        )
    }

    @Test
    fun `returns 0, in case discount cannot be applied`() {
        val discount = SPackageDiscount()
        val previousEntries = listOf(
            OutputEntry(LocalDate.parse("2020-01-03"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-04"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-07"), PackageType.LARGE, Carrier.LA_POSTE, .0, 6.9),
            OutputEntry(LocalDate.parse("2020-01-10"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-11"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-13"), PackageType.LARGE, Carrier.LA_POSTE, .0, 6.9)
        )

        assertEquals(
            .0,
            discount.apply(
                InputEntry(LocalDate.parse("2020-01-01"), PackageType.LARGE, Carrier.LA_POSTE),
                emptyList()
            )
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