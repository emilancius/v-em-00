package com.emilancius

import java.time.LocalDate

interface Entry {
    val date: LocalDate
    val packageType: PackageType
    val carrier: Carrier
}
