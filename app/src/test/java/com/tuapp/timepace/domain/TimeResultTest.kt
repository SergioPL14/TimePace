package com.tuapp.timepace.domain

import com.tuapp.timepace.domain.model.TimeResult
import org.junit.Assert.*
import org.junit.Test

class TimeResultTest {

    // ─── fromTotalSeconds ────────────────────────────────────────────────────

    @Test
    fun `fromTotalSeconds 3600 da 1h 0m 0s`() {
        val t = TimeResult.fromTotalSeconds(3_600)
        assertEquals(1, t.hours)
        assertEquals(0, t.minutes)
        assertEquals(0, t.seconds)
    }

    @Test
    fun `fromTotalSeconds 3661 da 1h 1m 1s`() {
        val t = TimeResult.fromTotalSeconds(3_661)
        assertEquals(1, t.hours)
        assertEquals(1, t.minutes)
        assertEquals(1, t.seconds)
    }

    @Test
    fun `fromTotalSeconds 5400 da 1h 30m 0s`() {
        val t = TimeResult.fromTotalSeconds(5_400)
        assertEquals(1, t.hours)
        assertEquals(30, t.minutes)
        assertEquals(0, t.seconds)
    }

    @Test
    fun `fromTotalSeconds 0 da ZERO`() {
        assertEquals(TimeResult.ZERO, TimeResult.fromTotalSeconds(0))
    }

    @Test
    fun `fromTotalSeconds 59 da 0h 0m 59s`() {
        val t = TimeResult.fromTotalSeconds(59)
        assertEquals(0, t.hours)
        assertEquals(0, t.minutes)
        assertEquals(59, t.seconds)
    }

    // ─── totalSeconds (ida y vuelta) ─────────────────────────────────────────

    @Test
    fun `totalSeconds de 1h 29m 39s es 5379`() {
        assertEquals(5_379, TimeResult(1, 29, 39).totalSeconds)
    }

    @Test
    fun `fromTotalSeconds es inverso de totalSeconds`() {
        val original = TimeResult(2, 15, 43)
        val roundTrip = TimeResult.fromTotalSeconds(original.totalSeconds)
        assertEquals(original, roundTrip)
    }

    @Test
    fun `totalSeconds de ZERO es 0`() {
        assertEquals(0, TimeResult.ZERO.totalSeconds)
    }

    // ─── toString ────────────────────────────────────────────────────────────

    @Test
    fun `toString formatea con ceros a la izquierda`() {
        assertEquals("01:05:09", TimeResult(1, 5, 9).toString())
    }

    @Test
    fun `toString de ZERO es 00-00-00`() {
        assertEquals("00:00:00", TimeResult.ZERO.toString())
    }

    @Test
    fun `toString de tiempo mayor de 10h es correcto`() {
        assertEquals("12:30:00", TimeResult(12, 30, 0).toString())
    }
}
