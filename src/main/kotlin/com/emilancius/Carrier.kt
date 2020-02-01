package com.emilancius

enum class Carrier(val code: String) {
    MONDIAL_RELAY("MR"),
    LA_POSTE("LP");

    companion object {
        fun byCode(code: String) = values().find { it.code == code }
    }
}
