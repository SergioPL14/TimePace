package com.tuapp.timepace.presentation.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultCard(
    primary: String,
    secondary: String,
    modifier: Modifier = Modifier
) {
    val hasResult = primary.isNotEmpty()
    val primaryColor = MaterialTheme.colorScheme.primary
    val surfaceColor = MaterialTheme.colorScheme.surface

    AnimatedVisibility(
        visible = hasResult,
        enter = fadeIn(tween(300)) + expandVertically(tween(300)),
        exit = fadeOut(tween(200)) + shrinkVertically(tween(200)),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(2.dp))
                .background(MaterialTheme.colorScheme.primary)
                .drawBehind {
                    // Línea decorativa sutil en la parte inferior
                    drawLine(
                        color = primaryColor.copy(alpha = 0.3f),
                        start = Offset(0f, size.height - 2.dp.toPx()),
                        end = Offset(size.width, size.height - 2.dp.toPx()),
                        strokeWidth = 2.dp.toPx()
                    )
                }
                .padding(horizontal = 24.dp, vertical = 28.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Etiqueta
                Text(
                    text = "RESULTADO",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 3.sp,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(12.dp))

                // Resultado principal con animación de cambio
                AnimatedContent(
                    targetState = primary,
                    transitionSpec = {
                        (fadeIn(tween(200)) + slideInVertically(
                            tween(200)
                        ) { it / 4 }).togetherWith(
                            fadeOut(tween(150)) + slideOutVertically(
                                tween(150)
                            ) { -it / 4 }
                        )
                    },
                    label = "result_primary"
                ) { value ->
                    Text(
                        text = value,
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Thin,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center,
                        letterSpacing = 2.sp
                    )
                }

                // Resultado secundario (velocidad o pace complementario)
                if (secondary.isNotEmpty()) {
                    Spacer(Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(1.dp))
                            .background(
                                MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.12f)
                            )
                            .padding(horizontal = 12.dp, vertical = 5.dp)
                    ) {
                        AnimatedContent(
                            targetState = secondary,
                            transitionSpec = {
                                fadeIn(tween(200)).togetherWith(fadeOut(tween(150)))
                            },
                            label = "result_secondary"
                        ) { value ->
                            Text(
                                text = value,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.75f),
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }
            }
        }
    }

    // Placeholder cuando no hay resultado
    AnimatedVisibility(
        visible = !hasResult,
        enter = fadeIn(tween(200)),
        exit = fadeOut(tween(150)),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(2.dp))
                .background(surfaceColor)
                .padding(horizontal = 24.dp, vertical = 28.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "—",
                fontSize = 32.sp,
                fontWeight = FontWeight.Thin,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.15f),
                textAlign = TextAlign.Center
            )
        }
    }
}
