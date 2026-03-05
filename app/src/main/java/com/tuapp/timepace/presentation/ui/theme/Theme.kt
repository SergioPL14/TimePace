package com.tuapp.timepace.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Black    = Color(0xFF000000)
private val White    = Color(0xFFFFFFFF)
private val OffWhite = Color(0xFFF5F5F5)
private val DarkGray = Color(0xFF1A1A1A)
private val MidGray  = Color(0xFF888888)

private val LightColors = lightColorScheme(
    primary      = Black,   onPrimary      = White,
    secondary    = DarkGray, onSecondary   = White,
    background   = White,   onBackground   = Black,
    surface      = OffWhite, onSurface     = Black,
    outline      = MidGray
)

private val DarkColors = darkColorScheme(
    primary      = White,   onPrimary      = Black,
    secondary    = MidGray, onSecondary    = Black,
    background   = Black,   onBackground   = White,
    surface      = DarkGray, onSurface     = White,
    outline      = MidGray
)

@Composable
fun TimePaceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}
