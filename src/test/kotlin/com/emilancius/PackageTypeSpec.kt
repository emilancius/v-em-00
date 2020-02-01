package com.emilancius

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PackageTypeSpec {

    @Test
    fun `returns PackageType by it's code`() {
        assertEquals(PackageType.SMALL, PackageType.byCode("S"))
        assertEquals(PackageType.MEDIUM, PackageType.byCode("M"))
        assertEquals(PackageType.LARGE, PackageType.byCode("L"))
    }

    @Test
    fun `returns null in case there are no supported PackageType, that is represented by the code`() {
        assertEquals(null, PackageType.byCode("XL"))
    }
}
