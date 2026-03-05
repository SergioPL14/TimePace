package com.tuapp.timepace.usecase

import com.tuapp.timepace.domain.model.Distance
import com.tuapp.timepace.domain.model.DistanceUnit
import com.tuapp.timepace.domain.model.Pace
import com.tuapp.timepace.domain.model.TimeResult
import com.tuapp.timepace.domain.usecase.PaceToTimeUseCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PaceToTimeUseCaseTest {

    private lateinit var useCase: PaceToTimeUseCase

    @Before
    fun setup() {
        useCase = PaceToTimeUseCase()
    }

    // ─── Casos reales de running ──────────────────────────────────────────────

    @Test
    fun `pace 5-00 en 10K da 50 minutos exactos`() {
        val result = useCase(Pace(5, 0), Distance(1.0, DistanceUnit.PRESET_10K))
        assertEquals(0, result.hours)
        assertEquals(50, result.minutes)
        assertEquals(0, result.seconds)
    }

    @Test
    fun `pace 5-00 en 5K da 25 minutos exactos`() {
        val result = useCase(Pace(5, 0), Distance(1.0, DistanceUnit.PRESET_5K))
        assertEquals(0, result.hours)
        assertEquals(25, result.minutes)
        assertEquals(0, result.seconds)
    }

    @Test
    fun `pace 4-15 en media maraton da 1h 29m 39s`() {
        // 255 seg/km × 21.0975 km = 5379.86 seg → 1h 29m 39s
        val result = useCase(Pace(4, 15), Distance(1.0, DistanceUnit.PRESET_HALF))
        assertEquals(1, result.hours)
        assertEquals(29, result.minutes)
        assertEquals(39, result.seconds)
    }

    @Test
    fun `pace 4-00 en maraton da 2h 48m 46s`() {
        // 240 seg/km × 42.195 km = 10126.8 seg → 2h 48m 46s
        val result = useCase(Pace(4, 0), Distance(1.0, DistanceUnit.PRESET_FULL))
        assertEquals(2, result.hours)
        assertEquals(48, result.minutes)
        assertEquals(46, result.seconds)
    }

    @Test
    fun `pace 6-00 en 1km da 6 minutos exactos`() {
        val result = useCase(Pace(6, 0), Distance(1.0, DistanceUnit.KILOMETERS))
        assertEquals(0, result.hours)
        assertEquals(6, result.minutes)
        assertEquals(0, result.seconds)
    }

    // ─── Distancia en metros ──────────────────────────────────────────────────

    @Test
    fun `pace 5-00 en 500m da 2 minutos 30 segundos`() {
        val result = useCase(Pace(5, 0), Distance(500.0, DistanceUnit.METERS))
        assertEquals(0, result.hours)
        assertEquals(2, result.minutes)
        assertEquals(30, result.seconds)
    }

    // ─── Edge cases ───────────────────────────────────────────────────────────

    @Test
    fun `pace ZERO devuelve TimeResult ZERO`() {
        val result = useCase(Pace.ZERO, Distance(10.0, DistanceUnit.KILOMETERS))
        assertEquals(TimeResult.ZERO, result)
    }

    @Test
    fun `distance ZERO devuelve TimeResult ZERO`() {
        val result = useCase(Pace(5, 0), Distance.ZERO)
        assertEquals(TimeResult.ZERO, result)
    }

    @Test
    fun `ambos ZERO devuelven TimeResult ZERO sin excepcion`() {
        val result = useCase(Pace.ZERO, Distance.ZERO)
        assertEquals(TimeResult.ZERO, result)
    }
}
