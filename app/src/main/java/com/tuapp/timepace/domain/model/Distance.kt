package com.tuapp.timepace.domain.model

enum class DistanceUnit(val label: String, val inMeters: Double) {
    METERS("m", 1.0),
    KILOMETERS("km", 1_000.0),
    PRESET_5K("5K", 5_000.0),
    PRESET_10K("10K", 10_000.0),
    PRESET_HALF("Half", 21_097.5),
    PRESET_FULL("Full", 42_195.0)
}

data class Distance(
    val value: Double,
    val unit: DistanceUnit
) {
    val inKilometers: Double
        get() = (value * unit.inMeters) / 1_000.0

    companion object {
        val ZERO = Distance(0.0, DistanceUnit.KILOMETERS)
    }
}
