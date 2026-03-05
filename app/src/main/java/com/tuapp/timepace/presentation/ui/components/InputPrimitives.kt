package com.tuapp.timepace.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Campo numérico minimalista — usado por todos los inputs de la app.
 */
@Composable
fun NumericField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    maxLength: Int = 4,
    imeAction: ImeAction = ImeAction.Next
) {
    val isFilled = value.isNotEmpty()

    BasicTextField(
        value = value,
        onValueChange = { if (it.length <= maxLength) onValueChange(it) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        textStyle = TextStyle(
            fontSize = 28.sp,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        ),
        modifier = modifier
            .clip(RoundedCornerShape(2.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = if (isFilled) 1.5.dp else 1.dp,
                color = if (isFilled)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                shape = RoundedCornerShape(2.dp)
            )
            .padding(vertical = 14.dp, horizontal = 8.dp),
        decorationBox = { innerTextField ->
            Box(contentAlignment = Alignment.Center) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Thin,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                        textAlign = TextAlign.Center
                    )
                }
                innerTextField()
            }
        }
    )
}

/**
 * Separador tipográfico entre campos (el ":" de mm:ss o HH:mm:ss).
 */
@Composable
fun TimeSeparator(modifier: Modifier = Modifier) {
    Text(
        text = ":",
        fontSize = 28.sp,
        fontWeight = FontWeight.Thin,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
        modifier = modifier.padding(horizontal = 4.dp)
    )
}

/**
 * Etiqueta de sección con línea decorativa.
 */
@Composable
fun SectionLabel(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text.uppercase(),
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 2.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
        )
        Spacer(Modifier.width(12.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .height(0.5.dp)
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))
        )
    }
}

/**
 * Etiqueta pequeña debajo de un campo (ej: "min", "seg", "horas").
 */
@Composable
fun FieldLabel(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.35f),
        textAlign = TextAlign.Center,
        letterSpacing = 1.sp,
        modifier = modifier.fillMaxWidth()
    )
}
