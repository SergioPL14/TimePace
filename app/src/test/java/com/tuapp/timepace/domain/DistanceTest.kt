package com.tuapp.timepace.domain

import com.tuapp.timepace.domain.model.Distance
import com.tuapp.timepace.domain.model.DistanceUnit
import org.junit.Assert.*
import org.junit.Test

class DistanceTest {

    // ─── inKilometers — conversiones ─────────────────────────────────────────

    @Test
    fun `1 km en kilometers da 1_0 km`() {
        assertEquals(1.0, Distance(1.0, DistanceUnit.KILOMETERS).inKilometers, 0.001)
    }

    @Test
    fun `1000 metros da 1_0 km`() {
        assertEquals(1.0, Distance(1_000.0, DistanceUnit.METERS).inKilometers, 0.001)
    }

    @Test
    fun `500 metros da 0_5 km`() {
        assertEquals(0.5, Distance(500.0, DistanceUnit.METERS).inKilometers, 0.001)
    }

    // ─── Presets ─────────────────────────────────────────────────────────────

    @Test
    fun `preset 5K da 5_0 km`() {
        assertEquals(5.0, Distance(1.0, DistanceUnit.PRESET_5K).inKilometers, 0.001)
    }

    @Test
    fun `preset 10K da 10_0 km`() {
        assertEquals(10.0, Distance(1.0, DistanceUnit.PRESET_10K).inKilometers, 0.001)
    }

    @Test
    fun `preset Half da 21_0975 km`() {
        assertEquals(21.0975, Distance(1.0, DistanceUnit.PRESET_HALF).inKilometers, 0.001)
    }

    @Test
    fun `preset Full da 42_195 km`() {
        assertEquals(42.195, Distance(1.0, DistanceUnit.PRESET_FULL).inKilometers, 0.001)
    }

    // ─── Edge cases ──────────────────────────────────────────────────────────

    @Test
    fun `ZERO distance da 0 km`() {
        assertEquals(0.0, Distance.ZERO.inKilometers, 0.0)
    }

    @Test
    fun `distancia fraccionaria en km es correcta`() {
        assertEquals(3.5, Distance(3.5, DistanceUnit.KILOMETERS).inKilometers, 0.001)
    }

    // ─── DistanceUnit labels ──────────────────────────────────────────────────

    @Test
    fun `labels de unidades son correctos`() {
        assertEquals("km",   DistanceUnit.KILOMETERS.label)
        assertEquals("m",    DistanceUnit.METERS.label)
        assertEquals("5K",   DistanceUnit.PRESET_5K.label)
        assertEquals("10K",  DistanceUnit.PRESET_10K.label)
        assertEquals("Half", DistanceUnit.PRESET_HALF.label)
        assertEquals("Full", DistanceUnit.PRESET_FULL.label)
    }
}
