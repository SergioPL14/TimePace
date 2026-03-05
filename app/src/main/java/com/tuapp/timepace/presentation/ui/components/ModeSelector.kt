package com.tuapp.timepace.presentation.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuapp.timepace.presentation.viewmodel.ConverterMode

private data class ModeTab(
    val mode: ConverterMode,
    val label: String,
    val sublabel: String
)

private val TABS = listOf(
    ModeTab(ConverterMode.PACE_TO_TIME, "P → T", "Pace·Time"),
    ModeTab(ConverterMode.TIME_TO_PACE, "T → P", "Time·Pace"),
    ModeTab(ConverterMode.SPEED_TO_PACE, "S → P", "Spd·Pace"),
    ModeTab(ConverterMode.PACE_TO_SPEED, "P → S", "Pace·Spd")
)

@Composable
fun ModeSelector(
    selectedMode: ConverterMode,
    onModeSelected: (ConverterMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(2.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        TABS.forEach { tab ->
            ModeTab(
                tab = tab,
                isSelected = selectedMode == tab.mode,
                onClick = { onModeSelected(tab.mode) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ModeTab(
    tab: ModeTab,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = tween(200),
        label = "tab_bg"
    )
    val cornerSize by animateDpAsState(
        targetValue = if (isSelected) 1.dp else 2.dp,
        animationSpec = tween(200),
        label = "tab_corner"
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerSize))
            .background(
                MaterialTheme.colorScheme.primary.copy(alpha = bgAlpha)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = tab.label,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected)
                    MaterialTheme.colorScheme.onPrimary
                else
                    MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.graphicsLayer {
                    scaleX = if (isSelected) 1.05f else 1f
                    scaleY = if (isSelected) 1.05f else 1f
                }
            )
            Text(
                text = tab.sublabel,
                fontSize = 9.sp,
                fontWeight = FontWeight.Normal,
                color = if (isSelected)
                    MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                else
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                textAlign = TextAlign.Center,
                letterSpacing = 0.3.sp
            )
        }
    }
}
