package com.tuapp.timepace.domain

import com.tuapp.timepace.domain.model.Pace
import org.junit.Assert.*
import org.junit.Test

class PaceTest {

    // ─── fromTotalSeconds ────────────────────────────────────────────────────

    @Test
    fun `fromTotalSeconds 255 segundos da 4 min 15 sec`() {
        val pace = Pace.fromTotalSeconds(255)
        assertEquals(4, pace.minutesPerKm)
        assertEquals(15, pace.secondsPerKm)
    }

    @Test
    fun `fromTotalSeconds 300 segundos da 5 min 00 sec`() {
        val pace = Pace.fromTotalSeconds(300)
        assertEquals(5, pace.minutesPerKm)
        assertEquals(0, pace.secondsPerKm)
    }

    @Test
    fun `fromTotalSeconds 360 segundos da 6 min 00 sec`() {
        val pace = Pace.fromTotalSeconds(360)
        assertEquals(6, pace.minutesPerKm)
        assertEquals(0, pace.secondsPerKm)
    }

    @Test
    fun `fromTotalSeconds 0 da pace ZERO`() {
        val pace = Pace.fromTotalSeconds(0)
        assertEquals(Pace.ZERO, pace)
    }

    // ─── totalSeconds ────────────────────────────────────────────────────────

    @Test
    fun `totalSeconds de 4 min 15 sec es 255`() {
        assertEquals(255, Pace(4, 15).totalSeconds)
    }

    @Test
    fun `totalSeconds de 5 min 00 sec es 300`() {
        assertEquals(300, Pace(5, 0).totalSeconds)
    }

    @Test
    fun `totalSeconds de ZERO es 0`() {
        assertEquals(0, Pace.ZERO.totalSeconds)
    }

    // ─── speedKmh ────────────────────────────────────────────────────────────

    @Test
    fun `speedKmh de pace 5-00 es 12 kmh`() {
        assertEquals(12.0, Pace(5, 0).speedKmh, 0.01)
    }

    @Test
    fun `speedKmh de pace 4-15 es aproximadamente 14_11 kmh`() {
        assertEquals(14.11, Pace(4, 15).speedKmh, 0.01)
    }

    @Test
    fun `speedKmh de pace 6-00 es 10 kmh`() {
        assertEquals(10.0, Pace(6, 0).speedKmh, 0.01)
    }

    @Test
    fun `speedKmh de ZERO no lanza excepcion y devuelve 0`() {
        assertEquals(0.0, Pace.ZERO.speedKmh, 0.0)
    }

    // ─── fromSpeedKmh ────────────────────────────────────────────────────────

    @Test
    fun `fromSpeedKmh 12 kmh da pace 5-00`() {
        val pace = Pace.fromSpeedKmh(12.0)
        assertEquals(5, pace.minutesPerKm)
        assertEquals(0, pace.secondsPerKm)
    }

    @Test
    fun `fromSpeedKmh 10 kmh da pace 6-00`() {
        val pace = Pace.fromSpeedKmh(10.0)
        assertEquals(6, pace.minutesPerKm)
        assertEquals(0, pace.secondsPerKm)
    }

    @Test
    fun `fromSpeedKmh 0 devuelve ZERO sin excepcion`() {
        assertEquals(Pace.ZERO, Pace.fromSpeedKmh(0.0))
    }

    @Test
    fun `fromSpeedKmh negativo devuelve ZERO sin excepcion`() {
        assertEquals(Pace.ZERO, Pace.fromSpeedKmh(-5.0))
    }

    // ─── toString ────────────────────────────────────────────────────────────

    @Test
    fun `toString de 4 min 5 sec formatea segundos con cero a la izquierda`() {
        assertEquals("4:05 min/km", Pace(4, 5).toString())
    }

    @Test
    fun `toString de 5 min 00 sec es correcto`() {
        assertEquals("5:00 min/km", Pace(5, 0).toString())
    }
}
