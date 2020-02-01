package com.emilancius.extensions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import com.emilancius.extensions.DoubleExtensions.format
import com.emilancius.extensions.DoubleExtensions.round

class DoubleExtensionsSpec {

    @Test
    fun `formats double value by scale provided`() {
        assertEquals("1.000", 1.0.format(3))
        assertEquals("1.65", 1.65413.format())
        assertEquals("8.90357", 8.903568.format(5))
    }

    @Test
    fun `rounds up double value by provided scale`() {
        assertEquals(1.50, 1.503.round())
        assertEquals(4.5467, 4.5466723284.round(4))
    }
}