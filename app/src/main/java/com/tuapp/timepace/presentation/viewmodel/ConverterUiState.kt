package com.tuapp.timepace.presentation.viewmodel

import com.tuapp.timepace.domain.model.DistanceUnit

/**
 * Estado inmutable que la UI observa.
 * El ViewModel es la única fuente de verdad.
 */
data class ConverterUiState(
    val mode: ConverterMode = ConverterMode.PACE_TO_TIME,

    // --- Inputs compartidos ---
    val distanceInput: String = "",
    val selectedUnit: DistanceUnit = DistanceUnit.KILOMETERS,

    // --- Inputs Pace → Time / Time → Pace ---
    val paceMinutes: String = "",
    val paceSeconds: String = "",

    // --- Inputs Time → Pace ---
    val timeHours: String = "",
    val timeMinutes: String = "",
    val timeSeconds: String = "",

    // --- Inputs Speed ↔ Pace ---
    val speedInput: String = "",

    // --- Resultados (siempre Strings listos para mostrar) ---
    val resultPrimary: String = "",    // Línea grande (ej: "00:45:30")
    val resultSecondary: String = "", // Línea pequeña (ej: "4:33 min/km")
    val resultLabel: String = "",     // Etiqueta del resultado

    val hasResult: Boolean = false,
    val errorMessage: String? = null
)

enum class ConverterMode(val label: String) {
    PACE_TO_TIME("Pace → Time"),
    TIME_TO_PACE("Time → Pace"),
    SPEED_TO_PACE("Speed → Pace"),
    PACE_TO_SPEED("Pace → Speed")
}
