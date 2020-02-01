package com.emilancius

import com.emilancius.discount.LP3LPackageDiscount
import com.emilancius.discount.SPackageDiscount
import com.emilancius.exception.EmptyArgumentsException
import java.io.File
import java.time.LocalDate
import java.util.*

fun main(arguments: Array<String>) {
    checkArgumentsExists(arguments)
    checkInputExists(arguments)

    val output = TreeMap<Int, OutputEntry>()
    val res = TreeMap<Int, String>()
    val discounts = arrayOf(SPackageDiscount(), LP3LPackageDiscount())

    File(arguments[0]).useLines {
        it.forEachIndexed { index, line ->
            if (!checkInputLine(line)) {
                res[index] = "$line Ignored"
                return@forEachIndexed
            }

            val inputEntry = InputEntry.create(line)
            val outputEntry = inputEntry.process(
                discounts,
                output.values.filter { e -> e.date.monthValue == inputEntry.date.monthValue }.sortedBy { e -> e.date }
            )
            output[index] = outputEntry
            res[index] = outputEntry.toString()
        }
    }

    res.values.forEach { println(it) }
}


private fun checkArgumentsExists(arguments: Array<String>) {
    if (arguments.isEmpty()) {
        throw EmptyArgumentsException("no arguments passed")
    }
}

private fun checkInputExists(arguments: Array<String>) {
    val path = arguments[0]

    if (!File(path).exists()) {
        throw IllegalArgumentException("input at \"$path\" does not exist")
    }
}

private fun checkInputLine(line: String): Boolean {
    val parameters = line.split(" ")

    if (parameters.size != 3 || !checkInputLineFormat(line)) {
        return false
    }

    try {
        LocalDate.parse(parameters[0])
    } catch (e: Exception) {
        return false
    }
    PackageType.byCode(parameters[1]) ?: return false
    Carrier.byCode(parameters[2]) ?: return false

    return true
}

private fun checkInputLineFormat(line: String) = line.matches(Regex("(\\d{4})-(\\d{2})-(\\d{2}) (\\S+) (\\S+)"))
