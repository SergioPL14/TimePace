package com.tuapp.timepace.presentation.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tuapp.timepace.presentation.ui.components.*
import com.tuapp.timepace.presentation.viewmodel.ConverterMode
import com.tuapp.timepace.presentation.viewmodel.ConverterViewModel

@Composable
fun ConverterScreen(
    viewModel: ConverterViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        Spacer(Modifier.height(12.dp))

        // Header
        Column {
            Text(
                text = "TIME",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 4.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.35f)
            )
            Text(
                text = "& PACE",
                fontSize = 32.sp,
                fontWeight = FontWeight.Thin,
                letterSpacing = 2.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // Mode selector
        ModeSelector(
            selectedMode = state.mode,
            onModeSelected = viewModel::onModeChange
        )

        // Result card
        ResultCard(
            primary = state.resultPrimary,
            secondary = state.resultSecondary
        )

        // Inputs según el modo activo
        when (state.mode) {
            ConverterMode.PACE_TO_TIME -> {
                PaceInput(
                    minutes = state.paceMinutes,
                    seconds = state.paceSeconds,
                    onMinutesChange = viewModel::onPaceMinutesChange,
                    onSecondsChange = viewModel::onPaceSecondsChange
                )
                DistanceInput(
                    distanceValue = state.distanceInput,
                    selectedUnit = state.selectedUnit,
                    onValueChange = viewModel::onDistanceInputChange,
                    onUnitChange = viewModel::onDistanceUnitChange
                )
            }

            ConverterMode.TIME_TO_PACE -> {
                TimeInput(
                    hours = state.timeHours,
                    minutes = state.timeMinutes,
                    seconds = state.timeSeconds,
                    onHoursChange = viewModel::onTimeHoursChange,
                    onMinutesChange = viewModel::onTimeMinutesChange,
                    onSecondsChange = viewModel::onTimeSecondsChange
                )
                DistanceInput(
                    distanceValue = state.distanceInput,
                    selectedUnit = state.selectedUnit,
                    onValueChange = viewModel::onDistanceInputChange,
                    onUnitChange = viewModel::onDistanceUnitChange
                )
            }

            ConverterMode.SPEED_TO_PACE -> {
                SpeedInput(
                    speed = state.speedInput,
                    onSpeedChange = viewModel::onSpeedChange
                )
            }

            ConverterMode.PACE_TO_SPEED -> {
                PaceInput(
                    minutes = state.paceMinutes,
                    seconds = state.paceSeconds,
                    onMinutesChange = viewModel::onPaceMinutesChange,
                    onSecondsChange = viewModel::onPaceSecondsChange
                )
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}
