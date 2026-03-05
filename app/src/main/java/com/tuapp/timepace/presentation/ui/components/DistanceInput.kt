package com.tuapp.timepace.presentation.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuapp.timepace.domain.model.DistanceUnit

private val ALL_UNITS = listOf(
    DistanceUnit.KILOMETERS,
    DistanceUnit.METERS,
    DistanceUnit.PRESET_5K,
    DistanceUnit.PRESET_10K,
    DistanceUnit.PRESET_HALF,
    DistanceUnit.PRESET_FULL
)

private val PRESET_UNITS = setOf(
    DistanceUnit.PRESET_5K,
    DistanceUnit.PRESET_10K,
    DistanceUnit.PRESET_HALF,
    DistanceUnit.PRESET_FULL
)

@Composable
fun DistanceInput(
    distanceValue: String,
    selectedUnit: DistanceUnit,
    onValueChange: (String) -> Unit,
    onUnitChange: (DistanceUnit) -> Unit,
    modifier: Modifier = Modifier
) {
    val isPreset = selectedUnit in PRESET_UNITS

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SectionLabel(text = "Distancia")

        // Campo numérico — oculto si hay preset seleccionado
        if (!isPreset) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NumericField(
                    value = distanceValue,
                    onValueChange = onValueChange,
                    placeholder = "0",
                    maxLength = 6,
                    imeAction = ImeAction.Next,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                UnitBadge(text = selectedUnit.label)
            }
        } else {
            // Muestra la distancia del preset en modo lectura
            PresetDisplay(unit = selectedUnit)
        }

        // Chips de unidad
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            contentPadding = PaddingValues(horizontal = 0.dp)
        ) {
            items(ALL_UNITS) { unit ->
                UnitChip(
                    label = unit.label,
                    isSelected = selectedUnit == unit,
                    onClick = { onUnitChange(unit) }
                )
            }
        }
    }
}

@Composable
private fun PresetDisplay(unit: DistanceUnit) {
    val km = unit.inMeters / 1000.0
    val formatted = if (km == km.toLong().toDouble())
        "${km.toLong()} km"
    else
        "%.3f km".format(km)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(2.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                1.5.dp,
                MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                RoundedCornerShape(2.dp)
            )
            .padding(vertical = 14.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = unit.label,
            fontSize = 28.sp,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = formatted,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
        )
    }
}

@Composable
private fun UnitChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.surface,
        animationSpec = tween(150),
        label = "chip_bg"
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected)
            MaterialTheme.colorScheme.onPrimary
        else
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        animationSpec = tween(150),
        label = "chip_text"
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(2.dp))
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 7.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = textColor,
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
fun UnitBadge(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(2.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 10.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            letterSpacing = 0.5.sp
        )
    }
}
