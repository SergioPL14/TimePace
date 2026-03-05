package com.tuapp.timepace.domain.usecase

import com.tuapp.timepace.domain.model.Pace
import javax.inject.Inject

class PaceToSpeedUseCase @Inject constructor() {
    /** Devuelve velocidad en km/h con 2 decimales */
    operator fun invoke(pace: Pace): Double =
        (pace.speedKmh * 100.0).toInt() / 100.0
}
