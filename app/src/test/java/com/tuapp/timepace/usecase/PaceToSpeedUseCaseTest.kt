package com.tuapp.timepace.usecase

import com.tuapp.timepace.domain.model.Pace
import com.tuapp.timepace.domain.usecase.PaceToSpeedUseCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PaceToSpeedUseCaseTest {

    private lateinit var useCase: PaceToSpeedUseCase

    @Before
    fun setup() {
        useCase = PaceToSpeedUseCase()
    }

    @Test
    fun `pace 5-00 da 12_00 kmh`() {
        assertEquals(12.0, useCase(Pace(5, 0)), 0.01)
    }

    @Test
    fun `pace 6-00 da 10_00 kmh`() {
        assertEquals(10.0, useCase(Pace(6, 0)), 0.01)
    }

    @Test
    fun `pace 4-00 da 15_00 kmh`() {
        assertEquals(15.0, useCase(Pace(4, 0)), 0.01)
    }

    @Test
    fun `pace 7-30 da 8_00 kmh`() {
        assertEquals(8.0, useCase(Pace(7, 30)), 0.01)
    }

    @Test
    fun `resultado tiene 2 decimales como maximo`() {
        val speed = useCase(Pace(4, 15)) // 14.117... → truncado a 14.11
        // Verificamos que no hay más de 2 decimales significativos
        val formatted = "%.2f".format(speed)
        assertEquals(speed, formatted.toDouble(), 0.001)
    }

    // ─── Simetría con SpeedToPace ─────────────────────────────────────────────

    @Test
    fun `PaceToSpeed es inverso aproximado de SpeedToPace`() {
        val originalSpeed = 11.5
        val pace = com.tuapp.timepace.domain.usecase.SpeedToPaceUseCase()(originalSpeed)
        val recoveredSpeed = useCase(pace)
        assertEquals(originalSpeed, recoveredSpeed, 0.1) // tolerancia por redondeo entero
    }

    // ─── Edge cases ───────────────────────────────────────────────────────────

    @Test
    fun `pace ZERO devuelve 0_0 sin excepcion`() {
        assertEquals(0.0, useCase(Pace.ZERO), 0.0)
    }
}
