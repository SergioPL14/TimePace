package com.tuapp.timepace.domain.usecase

import com.tuapp.timepace.domain.model.Distance
import com.tuapp.timepace.domain.model.Pace
import com.tuapp.timepace.domain.model.TimeResult
import javax.inject.Inject

class PaceToTimeUseCase @Inject constructor() {
    operator fun invoke(pace: Pace, distance: Distance): TimeResult {
        if (pace.totalSeconds <= 0 || distance.inKilometers <= 0) return TimeResult.ZERO
        val totalSeconds = (pace.totalSeconds * distance.inKilometers).toInt()
        return TimeResult.fromTotalSeconds(totalSeconds)
    }
}
