package com.tuapp.timepace.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun TimeInput(
    hours: String,
    minutes: String,
    seconds: String,
    onHoursChange: (String) -> Unit,
    onMinutesChange: (String) -> Unit,
    onSecondsChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SectionLabel(text = "Tiempo")

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Horas
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NumericField(
                    value = hours,
                    onValueChange = onHoursChange,
                    placeholder = "0",
                    maxLength = 2,
                    imeAction = ImeAction.Next,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(4.dp))
                FieldLabel(text = "horas")
            }

            TimeSeparator(modifier = Modifier.padding(bottom = 18.dp))

            // Minutos
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NumericField(
                    value = minutes,
                    onValueChange = onMinutesChange,
                    placeholder = "00",
                    maxLength = 2,
                    imeAction = ImeAction.Next,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(4.dp))
                FieldLabel(text = "min")
            }

            TimeSeparator(modifier = Modifier.padding(bottom = 18.dp))

            // Segundos
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NumericField(
                    value = seconds,
                    onValueChange = onSecondsChange,
                    placeholder = "00",
                    maxLength = 2,
                    imeAction = ImeAction.Done,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(4.dp))
                FieldLabel(text = "seg")
            }
        }
    }
}
