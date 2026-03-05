package com.tuapp.timepace.domain.usecase

import com.tuapp.timepace.domain.model.Pace
import javax.inject.Inject

class SpeedToPaceUseCase @Inject constructor() {
    operator fun invoke(speedKmh: Double): Pace =
        Pace.fromSpeedKmh(speedKmh)
}
