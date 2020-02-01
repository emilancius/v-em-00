package com.emilancius

enum class PackageType(val code: String) {
    SMALL("S"),
    MEDIUM("M"),
    LARGE("L");

    companion object {
        fun byCode(code: String) = values().find { it.code == code }
    }
}
