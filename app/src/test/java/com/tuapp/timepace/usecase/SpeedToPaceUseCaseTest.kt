package com.tuapp.timepace.usecase

import com.tuapp.timepace.domain.model.Pace
import com.tuapp.timepace.domain.usecase.SpeedToPaceUseCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SpeedToPaceUseCaseTest {

    private lateinit var useCase: SpeedToPaceUseCase

    @Before
    fun setup() {
        useCase = SpeedToPaceUseCase()
    }

    @Test
    fun `12 kmh da pace 5-00`() {
        val pace = useCase(12.0)
        assertEquals(5, pace.minutesPerKm)
        assertEquals(0, pace.secondsPerKm)
    }

    @Test
    fun `10 kmh da pace 6-00`() {
        val pace = useCase(10.0)
        assertEquals(6, pace.minutesPerKm)
        assertEquals(0, pace.secondsPerKm)
    }

    @Test
    fun `15 kmh da pace 4-00`() {
        val pace = useCase(15.0)
        assertEquals(4, pace.minutesPerKm)
        assertEquals(0, pace.secondsPerKm)
    }

    @Test
    fun `8 kmh da pace 7-30`() {
        // 3600 / 8 = 450 seg = 7 min 30 sec
        val pace = useCase(8.0)
        assertEquals(7, pace.minutesPerKm)
        assertEquals(30, pace.secondsPerKm)
    }

    @Test
    fun `velocidad muy alta 30 kmh da pace 2-00`() {
        val pace = useCase(30.0)
        assertEquals(2, pace.minutesPerKm)
        assertEquals(0, pace.secondsPerKm)
    }

    // ─── Edge cases ───────────────────────────────────────────────────────────

    @Test
    fun `velocidad 0 devuelve Pace ZERO sin excepcion`() {
        assertEquals(Pace.ZERO, useCase(0.0))
    }

    @Test
    fun `velocidad negativa devuelve Pace ZERO sin excepcion`() {
        assertEquals(Pace.ZERO, useCase(-10.0))
    }
}
