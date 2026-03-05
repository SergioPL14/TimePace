package com.tuapp.timepace.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.tuapp.timepace.domain.model.Distance
import com.tuapp.timepace.domain.model.DistanceUnit
import com.tuapp.timepace.domain.model.Pace
import com.tuapp.timepace.domain.model.TimeResult
import com.tuapp.timepace.domain.usecase.PaceToSpeedUseCase
import com.tuapp.timepace.domain.usecase.PaceToTimeUseCase
import com.tuapp.timepace.domain.usecase.SpeedToPaceUseCase
import com.tuapp.timepace.domain.usecase.TimeToPaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val paceToTimeUseCase: PaceToTimeUseCase,
    private val timeToPaceUseCase: TimeToPaceUseCase,
    private val speedToPaceUseCase: SpeedToPaceUseCase,
    private val paceToSpeedUseCase: PaceToSpeedUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConverterUiState())
    val uiState: StateFlow<ConverterUiState> = _uiState.asStateFlow()

    // ── Cambio de modo ────────────────────────────────────────────────────────
    fun onModeChange(mode: ConverterMode) {
        _uiState.update {
            it.copy(mode = mode, resultPrimary = "", resultSecondary = "",
                resultLabel = "", hasResult = false, errorMessage = null)
        }
        recalculate()
    }

    // ── Inputs ────────────────────────────────────────────────────────────────
    fun onDistanceInputChange(value: String) {
        _uiState.update { it.copy(distanceInput = value, errorMessage = null) }
        recalculate()
    }

    fun onDistanceUnitChange(unit: DistanceUnit) {
        val newDistanceInput = when (unit) {
            DistanceUnit.PRESET_5K, DistanceUnit.PRESET_10K,
            DistanceUnit.PRESET_HALF, DistanceUnit.PRESET_FULL -> unit.label
            else -> _uiState.value.distanceInput
        }
        _uiState.update { it.copy(selectedUnit = unit, distanceInput = newDistanceInput, errorMessage = null) }
        recalculate()
    }

    fun onPaceMinutesChange(value: String) {
        _uiState.update { it.copy(paceMinutes = value.take(2), errorMessage = null) }
        recalculate()
    }

    fun onPaceSecondsChange(value: String) {
        _uiState.update { it.copy(paceSeconds = value.take(2), errorMessage = null) }
        recalculate()
    }

    fun onTimeHoursChange(value: String) {
        _uiState.update { it.copy(timeHours = value.take(2), errorMessage = null) }
        recalculate()
    }

    fun onTimeMinutesChange(value: String) {
        _uiState.update { it.copy(timeMinutes = value.take(2), errorMessage = null) }
        recalculate()
    }

    fun onTimeSecondsChange(value: String) {
        _uiState.update { it.copy(timeSeconds = value.take(2), errorMessage = null) }
        recalculate()
    }

    fun onSpeedChange(value: String) {
        _uiState.update { it.copy(speedInput = value, errorMessage = null) }
        recalculate()
    }

    // ── Motor de cálculo (se llama después de cada cambio de input) ───────────
    private fun recalculate() {
        when (_uiState.value.mode) {
            ConverterMode.PACE_TO_TIME  -> calculatePaceToTime()
            ConverterMode.TIME_TO_PACE  -> calculateTimeToPace()
            ConverterMode.SPEED_TO_PACE -> calculateSpeedToPace()
            ConverterMode.PACE_TO_SPEED -> calculatePaceToSpeed()
        }
    }

    // También exponemos calculate() públicamente por si se llama desde la UI
    fun calculate() = recalculate()

    // ── Cálculos individuales ─────────────────────────────────────────────────
    private fun calculatePaceToTime() {
        val state = _uiState.value
        val pace = parsePace(state.paceMinutes, state.paceSeconds) ?: return clearResult()
        val distance = parseDistance(state.distanceInput, state.selectedUnit) ?: return clearResult()
        val result: TimeResult = paceToTimeUseCase(pace, distance)
        _uiState.update {
            it.copy(
                resultPrimary   = result.toString(),
                resultSecondary = "%.2f km/h".format(pace.speedKmh),
                resultLabel     = "Tiempo para ${formatDistance(distance)}",
                hasResult       = true,
                errorMessage    = null
            )
        }
    }

    private fun calculateTimeToPace() {
        val state = _uiState.value
        val time = parseTime(state.timeHours, state.timeMinutes, state.timeSeconds) ?: return clearResult()
        val distance = parseDistance(state.distanceInput, state.selectedUnit) ?: return clearResult()
        val result: Pace = timeToPaceUseCase(time, distance)
        _uiState.update {
            it.copy(
                resultPrimary   = result.toString(),
                resultSecondary = "%.2f km/h".format(result.speedKmh),
                resultLabel     = "Pace para ${formatDistance(distance)}",
                hasResult       = true,
                errorMessage    = null
            )
        }
    }

    private fun calculateSpeedToPace() {
        val state = _uiState.value
        val speed = state.speedInput.toDoubleOrNull()
        if (speed == null || speed <= 0.0) return clearResult()
        val result: Pace = speedToPaceUseCase(speed)
        _uiState.update {
            it.copy(
                resultPrimary   = result.toString(),
                resultSecondary = "%.2f km/h".format(speed),
                resultLabel     = "Pace equivalente",
                hasResult       = true,
                errorMessage    = null
            )
        }
    }

    private fun calculatePaceToSpeed() {
        val state = _uiState.value
        val pace = parsePace(state.paceMinutes, state.paceSeconds) ?: return clearResult()
        val speed: Double = paceToSpeedUseCase(pace)
        _uiState.update {
            it.copy(
                resultPrimary   = "%.2f km/h".format(speed),
                resultSecondary = pace.toString(),
                resultLabel     = "Velocidad equivalente",
                hasResult       = true,
                errorMessage    = null
            )
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
    private fun clearResult() {
        _uiState.update { it.copy(resultPrimary = "", resultSecondary = "", hasResult = false, errorMessage = null) }
    }

    private fun parsePace(minutesStr: String, secondsStr: String): Pace? {
        val minutes = minutesStr.toIntOrNull() ?: return null
        val seconds = secondsStr.toIntOrNull() ?: 0
        if (minutes < 0 || seconds !in 0..59) return null
        if (minutes == 0 && seconds == 0) return null
        return Pace(minutes, seconds)
    }

    private fun parseTime(hoursStr: String, minutesStr: String, secondsStr: String): TimeResult? {
        val hours   = hoursStr.toIntOrNull() ?: 0
        val minutes = minutesStr.toIntOrNull() ?: 0
        val seconds = secondsStr.toIntOrNull() ?: 0
        if (minutes !in 0..59 || seconds !in 0..59) return null
        if (hours == 0 && minutes == 0 && seconds == 0) return null
        return TimeResult(hours, minutes, seconds)
    }

    private fun parseDistance(input: String, unit: DistanceUnit): Distance? =
        when (unit) {
            DistanceUnit.PRESET_5K, DistanceUnit.PRESET_10K,
            DistanceUnit.PRESET_HALF, DistanceUnit.PRESET_FULL -> Distance(1.0, unit)
            else -> input.toDoubleOrNull()?.takeIf { it > 0.0 }?.let { Distance(it, unit) }
        }

    private fun formatDistance(distance: Distance): String = when (distance.unit) {
        DistanceUnit.PRESET_5K   -> "5K"
        DistanceUnit.PRESET_10K  -> "10K"
        DistanceUnit.PRESET_HALF -> "Media Maratón"
        DistanceUnit.PRESET_FULL -> "Maratón"
        else -> "%.2f %s".format(distance.value, distance.unit.label)
    }

    fun reset() {
        _uiState.update { ConverterUiState(mode = it.mode) }
    }
}