package com.emilancius

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PricingSpec {

    @Test
    fun `returns price by Carrier and PackageType combination`() {
        assertEquals(2.0, Pricing.priceBy(Carrier.MONDIAL_RELAY, PackageType.SMALL))
        assertEquals(3.0, Pricing.priceBy(Carrier.MONDIAL_RELAY, PackageType.MEDIUM))
        assertEquals(4.0, Pricing.priceBy(Carrier.MONDIAL_RELAY, PackageType.LARGE))
        assertEquals(1.5, Pricing.priceBy(Carrier.LA_POSTE, PackageType.SMALL))
        assertEquals(4.9, Pricing.priceBy(Carrier.LA_POSTE, PackageType.MEDIUM))
        assertEquals(6.9, Pricing.priceBy(Carrier.LA_POSTE, PackageType.LARGE))
    }

    @Test
    fun `returns smallest price by PackageType among Pricing setups`() {
        assertEquals(1.5, Pricing.minPriceByPackageType(PackageType.SMALL))
        assertEquals(3.0, Pricing.minPriceByPackageType(PackageType.MEDIUM))
        assertEquals(4.0, Pricing.minPriceByPackageType(PackageType.LARGE))
    }
}
