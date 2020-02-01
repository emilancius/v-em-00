package com.emilancius.discount

import com.emilancius.InputEntry
import com.emilancius.OutputEntry

interface Discount {

    fun apply(entry: InputEntry, previousEntries: List<OutputEntry>): Double

    fun canApply(entry: InputEntry, previousEntries: List<OutputEntry>): Boolean
}
