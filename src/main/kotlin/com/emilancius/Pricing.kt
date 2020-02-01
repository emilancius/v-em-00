package com.emilancius

import com.emilancius.exception.PricingException

enum class Pricing(
    val carrier: Carrier = Carrier.MONDIAL_RELAY,
    val packageType: PackageType = PackageType.SMALL,
    val price: Double
) {
    MONDIAL_RELAY_SMALL(price = 2.0),
    MONDIAL_RELAY_MEDIUM(packageType = PackageType.MEDIUM, price = 3.0),
    MONDIAL_RELAY_LARGE(packageType = PackageType.LARGE, price = 4.0),

    LA_POSTE_SMALL(carrier = Carrier.LA_POSTE, price = 1.5),
    LA_POSTE_MEDIUM(carrier = Carrier.LA_POSTE, packageType = PackageType.MEDIUM, price = 4.9),
    LA_POSTE_LARGE(carrier = Carrier.LA_POSTE, packageType = PackageType.LARGE, price = 6.9);

    companion object {
        fun priceBy(carrier: Carrier, packageType: PackageType) = values()
            .find { it.carrier == carrier && it.packageType == packageType }
            ?.price
            ?: throw PricingException("Price is not set up for [carrier=$carrier, packageType=$packageType] ")

        fun minPriceByPackageType(packageType: PackageType) = values()
            .filter { it.packageType == packageType }
            .map { it.price }
            .min()
            ?: throw PricingException("Price is not set up for [packageType=$packageType]")
    }
}
