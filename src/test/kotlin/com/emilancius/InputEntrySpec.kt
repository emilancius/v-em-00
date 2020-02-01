package com.emilancius

import com.emilancius.discount.LP3LPackageDiscount
import com.emilancius.discount.SPackageDiscount
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class InputEntrySpec {

    @Test
    fun `applies discounts if possible and produces OutputEntry`() {
        assertEquals(
            OutputEntry(LocalDate.parse("2020-01-01"), PackageType.SMALL, Carrier.MONDIAL_RELAY, 1.5, .5),
            InputEntry(LocalDate.parse("2020-01-01"), PackageType.SMALL, Carrier.MONDIAL_RELAY)
                .process(arrayOf(SPackageDiscount()), emptyList())
        )

        val previousEntries = listOf(
            OutputEntry(LocalDate.parse("2020-01-01"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0),
            OutputEntry(LocalDate.parse("2020-01-01"), PackageType.LARGE, Carrier.LA_POSTE, 6.9, .0)
        )

        assertEquals(
            OutputEntry(LocalDate.parse("2020-01-03"), PackageType.LARGE, Carrier.LA_POSTE, .0, 6.9),
            InputEntry(LocalDate.parse("2020-01-03"), PackageType.LARGE, Carrier.LA_POSTE)
                .process(arrayOf(SPackageDiscount(), LP3LPackageDiscount()), previousEntries)
        )
    }

    @Test
    fun `creates InputEntry from String line of correct format`() {
        assertEquals(
            InputEntry(LocalDate.parse("2020-01-01"), PackageType.LARGE, Carrier.LA_POSTE),
            InputEntry.create("2020-01-01 L LP")
        )
        assertEquals(
            InputEntry(LocalDate.parse("2020-01-03"), PackageType.SMALL, Carrier.MONDIAL_RELAY),
            InputEntry.create("2020-01-03 S MR")
        )
    }
}
