package com.tuapp.timepace.usecase

import com.tuapp.timepace.domain.model.Distance
import com.tuapp.timepace.domain.model.DistanceUnit
import com.tuapp.timepace.domain.model.Pace
import com.tuapp.timepace.domain.model.TimeResult
import com.tuapp.timepace.domain.usecase.TimeToPaceUseCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TimeToPaceUseCaseTest {

    private lateinit var useCase: TimeToPaceUseCase

    @Before
    fun setup() {
        useCase = TimeToPaceUseCase()
    }

    // ─── Casos reales de running ──────────────────────────────────────────────

    @Test
    fun `50 minutos en 10K da pace 5-00`() {
        val result = useCase(TimeResult(0, 50, 0), Distance(1.0, DistanceUnit.PRESET_10K))
        assertEquals(5, result.minutesPerKm)
        assertEquals(0, result.secondsPerKm)
    }

    @Test
    fun `25 minutos en 5K da pace 5-00`() {
        val result = useCase(TimeResult(0, 25, 0), Distance(1.0, DistanceUnit.PRESET_5K))
        assertEquals(5, result.minutesPerKm)
        assertEquals(0, result.secondsPerKm)
    }

    @Test
    fun `3h30m en maraton da pace aproximado 4-58`() {
        // 12600 seg / 42.195 km = 298.6 seg/km ≈ 4:58
        val result = useCase(TimeResult(3, 30, 0), Distance(1.0, DistanceUnit.PRESET_FULL))
        assertEquals(4, result.minutesPerKm)
        assertEquals(58, result.secondsPerKm)
    }

    @Test
    fun `1h45m en media maraton da pace aproximado 4-58`() {
        // 6300 seg / 21.0975 km = 298.6 seg/km ≈ 4:58
        val result = useCase(TimeResult(1, 45, 0), Distance(1.0, DistanceUnit.PRESET_HALF))
        assertEquals(4, result.minutesPerKm)
        assertEquals(58, result.secondsPerKm)
    }

    @Test
    fun `6 minutos en 1km da pace 6-00`() {
        val result = useCase(TimeResult(0, 6, 0), Distance(1.0, DistanceUnit.KILOMETERS))
        assertEquals(6, result.minutesPerKm)
        assertEquals(0, result.secondsPerKm)
    }

    // ─── Inverso de PaceToTime ───────────────────────────────────────────────

    @Test
    fun `TimeToPace es inverso aproximado de PaceToTime para 5K`() {
        val originalPace = Pace(5, 30)
        val distance = Distance(1.0, DistanceUnit.PRESET_5K)
        val time = com.tuapp.timepace.domain.usecase.PaceToTimeUseCase()(originalPace, distance)
        val recoveredPace = useCase(time, distance)

        // Tolerancia de ±1 segundo por redondeo de enteros
        val diff = Math.abs(originalPace.totalSeconds - recoveredPace.totalSeconds)
        assertTrue("Diferencia de $diff seg es demasiado grande", diff <= 1)
    }

    // ─── Edge cases ───────────────────────────────────────────────────────────

    @Test
    fun `time ZERO devuelve Pace ZERO`() {
        val result = useCase(TimeResult.ZERO, Distance(10.0, DistanceUnit.KILOMETERS))
        assertEquals(Pace.ZERO, result)
    }

    @Test
    fun `distance ZERO devuelve Pace ZERO sin excepcion`() {
        val result = useCase(TimeResult(0, 50, 0), Distance.ZERO)
        assertEquals(Pace.ZERO, result)
    }
}
