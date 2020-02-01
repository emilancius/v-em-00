package com.emilancius

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CarrierSpec {

    @Test
    fun `returns Carrier by it's code`() {
        assertEquals(Carrier.MONDIAL_RELAY, Carrier.byCode("MR"))
        assertEquals(Carrier.LA_POSTE, Carrier.byCode("LP"))
    }

    @Test
    fun `returns null in case there are no supported Carrier, that is represented by the code`() {
        assertEquals(null, Carrier.byCode("DHL"))
    }
}