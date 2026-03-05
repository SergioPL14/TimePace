package com.tuapp.timepace.viewmodel

import com.tuapp.timepace.domain.model.DistanceUnit
import com.tuapp.timepace.domain.usecase.PaceToSpeedUseCase
import com.tuapp.timepace.domain.usecase.PaceToTimeUseCase
import com.tuapp.timepace.domain.usecase.SpeedToPaceUseCase
import com.tuapp.timepace.domain.usecase.TimeToPaceUseCase
import com.tuapp.timepace.presentation.viewmodel.ConverterMode
import com.tuapp.timepace.presentation.viewmodel.ConverterViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ConverterViewModelTest {

    private lateinit var viewModel: ConverterViewModel

    @Before
    fun setup() {
        // Instanciamos sin Hilt — los use cases no tienen dependencias externas
        viewModel = ConverterViewModel(
            paceToTimeUseCase  = PaceToTimeUseCase(),
            timeToPaceUseCase  = TimeToPaceUseCase(),
            speedToPaceUseCase = SpeedToPaceUseCase(),
            paceToSpeedUseCase = PaceToSpeedUseCase()
        )
    }

    // ─── Estado inicial ───────────────────────────────────────────────────────

    @Test
    fun `estado inicial es PACE_TO_TIME con resultados vacios`() {
        val state = viewModel.uiState.value
        assertEquals(ConverterMode.PACE_TO_TIME, state.mode)
        assertEquals("", state.resultPrimary)
        assertEquals("", state.resultSecondary)
        assertNull(state.errorMessage)
    }

    // ─── Cambio de modo ───────────────────────────────────────────────────────

    @Test
    fun `cambiar modo limpia los resultados`() {
        // Primero generamos un resultado
        viewModel.onDistanceUnitChange(DistanceUnit.PRESET_10K)
        viewModel.onPaceMinutesChange("5")
        viewModel.onPaceSecondsChange("0")
        viewModel.calculate()
        assertNotEquals("", viewModel.uiState.value.resultPrimary)

        // Cambiamos de modo
        viewModel.onModeChange(ConverterMode.TIME_TO_PACE)

        val state = viewModel.uiState.value
        assertEquals(ConverterMode.TIME_TO_PACE, state.mode)
        assertEquals("", state.resultPrimary)
        assertEquals("", state.resultSecondary)
    }

    // ─── PACE → TIME ─────────────────────────────────────────────────────────

    @Test
    fun `pace 5-00 en 10K da 00-50-00`() {
        viewModel.onDistanceUnitChange(DistanceUnit.PRESET_10K)
        viewModel.onPaceMinutesChange("5")
        viewModel.onPaceSecondsChange("0")
        viewModel.calculate()

        assertEquals("00:50:00", viewModel.uiState.value.resultPrimary)
    }

    @Test
    fun `pace 5-00 en 5K da 00-25-00`() {
        viewModel.onDistanceUnitChange(DistanceUnit.PRESET_5K)
        viewModel.onPaceMinutesChange("5")
        viewModel.onPaceSecondsChange("0")
        viewModel.calculate()

        assertEquals("00:25:00", viewModel.uiState.value.resultPrimary)
    }

    @Test
    fun `pace to time muestra velocidad en resultSecondary`() {
        viewModel.onDistanceUnitChange(DistanceUnit.PRESET_10K)
        viewModel.onPaceMinutesChange("5")
        viewModel.onPaceSecondsChange("0")
        viewModel.calculate()

        assertEquals("12.00 km/h", viewModel.uiState.value.resultSecondary)
    }

    @Test
    fun `pace to time sin distancia no muestra resultado`() {
        viewModel.onPaceMinutesChange("5")
        viewModel.onPaceSecondsChange("0")
        viewModel.calculate()
        // Sin distancia seleccionada (o input vacío) -> resultado vacío

        assertEquals("", viewModel.uiState.value.resultPrimary)
    }

    @Test
    fun `pace to time con solo minutos y sin segundos funciona`() {
        // Los segundos son opcionales (default 0)
        viewModel.onDistanceUnitChange(DistanceUnit.PRESET_5K)
        viewModel.onPaceMinutesChange("6")
        // No llamamos onPaceSecondsChange
        viewModel.calculate()

        assertEquals("00:30:00", viewModel.uiState.value.resultPrimary)
    }

    // ─── TIME → PACE ──────────────────────────────────────────────────────────

    @Test
    fun `50 minutos en 10K da pace 5-00`() {
        viewModel.onModeChange(ConverterMode.TIME_TO_PACE)
        viewModel.onDistanceUnitChange(DistanceUnit.PRESET_10K)
        viewModel.onTimeHoursChange("0")
        viewModel.onTimeMinutesChange("50")
        viewModel.onTimeSecondsChange("0")
        viewModel.calculate()

        assertEquals("5:00 min/km", viewModel.uiState.value.resultPrimary)
    }

    @Test
    fun `time to pace muestra velocidad en resultSecondary`() {
        viewModel.onModeChange(ConverterMode.TIME_TO_PACE)
        viewModel.onDistanceUnitChange(DistanceUnit.PRESET_10K)
        viewModel.onTimeHoursChange("0")
        viewModel.onTimeMinutesChange("50")
        viewModel.onTimeSecondsChange("0")
        viewModel.calculate()

        assertEquals("12.00 km/h", viewModel.uiState.value.resultSecondary)
    }

    @Test
    fun `time to pace sin distancia no muestra resultado`() {
        viewModel.onModeChange(ConverterMode.TIME_TO_PACE)
        viewModel.onTimeHoursChange("1")
        viewModel.onTimeMinutesChange("30")
        viewModel.calculate()

        assertEquals("", viewModel.uiState.value.resultPrimary)
    }

    // ─── SPEED → PACE ─────────────────────────────────────────────────────────

    @Test
    fun `speed 12 kmh da pace 5-00`() {
        viewModel.onModeChange(ConverterMode.SPEED_TO_PACE)
        viewModel.onSpeedChange("12")
        viewModel.calculate()

        assertEquals("5:00 min/km", viewModel.uiState.value.resultPrimary)
    }

    @Test
    fun `speed 10 kmh da pace 6-00`() {
        viewModel.onModeChange(ConverterMode.SPEED_TO_PACE)
        viewModel.onSpeedChange("10")
        viewModel.calculate()

        assertEquals("6:00 min/km", viewModel.uiState.value.resultPrimary)
    }

    @Test
    fun `speed input no numerico no muestra resultado`() {
        viewModel.onModeChange(ConverterMode.SPEED_TO_PACE)
        viewModel.onSpeedChange("abc")
        viewModel.calculate()

        assertEquals("", viewModel.uiState.value.resultPrimary)
    }

    @Test
    fun `speed vacio no muestra resultado`() {
        viewModel.onModeChange(ConverterMode.SPEED_TO_PACE)
        viewModel.onSpeedChange("")
        viewModel.calculate()

        assertEquals("", viewModel.uiState.value.resultPrimary)
    }

    // ─── PACE → SPEED ─────────────────────────────────────────────────────────

    @Test
    fun `pace 5-00 da 12_00 kmh`() {
        viewModel.onModeChange(ConverterMode.PACE_TO_SPEED)
        viewModel.onPaceMinutesChange("5")
        viewModel.onPaceSecondsChange("0")
        viewModel.calculate()

        assertEquals("12.00 km/h", viewModel.uiState.value.resultPrimary)
    }

    @Test
    fun `pace 6-00 da 10_00 kmh`() {
        viewModel.onModeChange(ConverterMode.PACE_TO_SPEED)
        viewModel.onPaceMinutesChange("6")
        viewModel.onPaceSecondsChange("0")
        viewModel.calculate()

        assertEquals("10.00 km/h", viewModel.uiState.value.resultPrimary)
    }

    @Test
    fun `pace to speed muestra pace en resultSecondary`() {
        viewModel.onModeChange(ConverterMode.PACE_TO_SPEED)
        viewModel.onPaceMinutesChange("5")
        viewModel.onPaceSecondsChange("0")
        viewModel.calculate()

        assertEquals("5:00 min/km", viewModel.uiState.value.resultSecondary)
    }

    @Test
    fun `pace sin minutos no muestra resultado`() {
        viewModel.onModeChange(ConverterMode.PACE_TO_SPEED)
        viewModel.onPaceSecondsChange("30")
        viewModel.calculate()
        // Sin minutos → resultado vacío

        assertEquals("", viewModel.uiState.value.resultPrimary)
    }

    // ─── Preset de distancia ──────────────────────────────────────────────────

    @Test
    fun `seleccionar preset actualiza distanceInput con label del preset`() {
        viewModel.onDistanceUnitChange(DistanceUnit.PRESET_HALF)
        assertEquals("Half", viewModel.uiState.value.distanceInput)
    }

    @Test
    fun `cambiar a unidad libre conserva el input numerico anterior`() {
        viewModel.onDistanceInputChange("15")
        viewModel.onDistanceUnitChange(DistanceUnit.KILOMETERS)
        assertEquals("15", viewModel.uiState.value.distanceInput)
    }

    // ─── Recálculo en tiempo real ─────────────────────────────────────────────

    @Test
    fun `resultado se actualiza al cambiar distancia sin reintroducir pace`() {
        viewModel.onPaceMinutesChange("5")
        viewModel.onPaceSecondsChange("0")
        viewModel.onDistanceUnitChange(DistanceUnit.PRESET_5K)
        viewModel.calculate()

        assertEquals("00:25:00", viewModel.uiState.value.resultPrimary)

        viewModel.onDistanceUnitChange(DistanceUnit.PRESET_10K)
        viewModel.calculate()

        assertEquals("00:50:00", viewModel.uiState.value.resultPrimary)
    }

    @Test
    fun `resultado se limpia al borrar el pace`() {
        viewModel.onDistanceUnitChange(DistanceUnit.PRESET_10K)
        viewModel.onPaceMinutesChange("5")
        viewModel.onPaceSecondsChange("0")
        viewModel.calculate()
        assertNotEquals("", viewModel.uiState.value.resultPrimary)

        viewModel.onPaceMinutesChange("") // Borra los minutos
        viewModel.calculate()

        assertEquals("", viewModel.uiState.value.resultPrimary)
    }
}
