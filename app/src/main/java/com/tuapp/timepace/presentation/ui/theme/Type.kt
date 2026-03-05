package com.tuapp.timepace.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    displayLarge = TextStyle(fontWeight = FontWeight.Thin,   fontSize = 57.sp, letterSpacing = (-0.25).sp),
    headlineLarge = TextStyle(fontWeight = FontWeight.Light,  fontSize = 32.sp),
    headlineMedium = TextStyle(fontWeight = FontWeight.Light, fontSize = 28.sp),
    titleLarge = TextStyle(fontWeight = FontWeight.Normal,   fontSize = 22.sp),
    titleMedium = TextStyle(fontWeight = FontWeight.Medium,  fontSize = 16.sp, letterSpacing = 0.15.sp),
    bodyLarge = TextStyle(fontWeight = FontWeight.Normal,    fontSize = 16.sp),
    bodyMedium = TextStyle(fontWeight = FontWeight.Normal,   fontSize = 14.sp),
    labelLarge = TextStyle(fontWeight = FontWeight.Medium,   fontSize = 14.sp, letterSpacing = 0.1.sp),
    labelSmall = TextStyle(fontWeight = FontWeight.Medium,   fontSize = 11.sp, letterSpacing = 0.5.sp)
)
