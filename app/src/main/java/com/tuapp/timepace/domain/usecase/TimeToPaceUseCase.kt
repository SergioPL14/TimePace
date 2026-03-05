package com.tuapp.timepace.domain.usecase

import com.tuapp.timepace.domain.model.Distance
import com.tuapp.timepace.domain.model.Pace
import com.tuapp.timepace.domain.model.TimeResult
import javax.inject.Inject

class TimeToPaceUseCase @Inject constructor() {
    operator fun invoke(time: TimeResult, distance: Distance): Pace {
        if (time.totalSeconds <= 0 || distance.inKilometers <= 0) return Pace.ZERO
        val secondsPerKm = (time.totalSeconds / distance.inKilometers).toInt()
        return Pace.fromTotalSeconds(secondsPerKm)
    }
}
