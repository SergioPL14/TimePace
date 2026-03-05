package com.tuapp.timepace.domain.model

/**
 * Pace en minutos y segundos por kilómetro.
 * Toda aritmética de tiempo vive aquí, nunca en UI.
 */
data class Pace(
    val minutesPerKm: Int,
    val secondsPerKm: Int  // 0..59
) {
    /** Representación total en segundos por km */
    val totalSeconds: Int
        get() = minutesPerKm * 60 + secondsPerKm

    /** Velocidad equivalente en km/h */
    val speedKmh: Double
        get() = if (totalSeconds > 0) 3_600.0 / totalSeconds else 0.0

    override fun toString(): String =
        "%d:%02d min/km".format(minutesPerKm, secondsPerKm)

    companion object {
        val ZERO = Pace(0, 0)

        /** Construye un Pace a partir de segundos totales por km */
        fun fromTotalSeconds(seconds: Int): Pace =
            Pace(
                minutesPerKm = seconds / 60,
                secondsPerKm = seconds % 60
            )

        /** Construye un Pace a partir de velocidad en km/h */
        fun fromSpeedKmh(speedKmh: Double): Pace {
            if (speedKmh <= 0.0) return ZERO
            val totalSeconds = (3_600.0 / speedKmh).toInt()
            return fromTotalSeconds(totalSeconds)
        }
    }
}
