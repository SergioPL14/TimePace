package com.tuapp.timepace.domain.model

/**
 * Tiempo total descompuesto en horas, minutos y segundos.
 * Es el resultado de Pace × Distancia.
 */
data class TimeResult(
    val hours: Int,
    val minutes: Int,  // 0..59
    val seconds: Int   // 0..59
) {
    /** Total en segundos — útil para hacer cálculo inverso */
    val totalSeconds: Int
        get() = hours * 3_600 + minutes * 60 + seconds

    override fun toString(): String =
        "%02d:%02d:%02d".format(hours, minutes, seconds)

    companion object {
        val ZERO = TimeResult(0, 0, 0)

        /** Construye un TimeResult a partir de segundos totales */
        fun fromTotalSeconds(seconds: Int): TimeResult =
            TimeResult(
                hours   = seconds / 3_600,
                minutes = (seconds % 3_600) / 60,
                seconds = seconds % 60
            )
    }
}
