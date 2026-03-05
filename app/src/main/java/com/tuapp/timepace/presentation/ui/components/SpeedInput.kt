package com.tuapp.timepace.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun SpeedInput(
    speed: String,
    onSpeedChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SectionLabel(text = "Velocidad")

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NumericField(
                value = speed,
                onValueChange = onSpeedChange,
                placeholder = "0",
                maxLength = 5,
                imeAction = ImeAction.Done,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            UnitBadge(text = "km/h")
        }
    }
}
