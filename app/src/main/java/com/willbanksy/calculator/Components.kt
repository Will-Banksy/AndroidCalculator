package com.willbanksy.calculator

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class ButtonRole {
    PRIMARY,
    SECONDARY,
    TERTIARY
}

@Composable
fun CalculatorButton(
    text: String,
    modifier: Modifier = Modifier,
    aspectRatio: Float = 1f,
    role: ButtonRole = ButtonRole.PRIMARY,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .aspectRatio(aspectRatio)
            .clickable {
                onClick()
            }
            .then(modifier),
        color = when(role) {
            ButtonRole.PRIMARY -> MaterialTheme.colors.primary
            ButtonRole.SECONDARY -> MaterialTheme.colors.primaryVariant
            ButtonRole.TERTIARY -> MaterialTheme.colors.secondary
        },
        elevation = 8.dp
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = when(role) {
                    ButtonRole.PRIMARY -> MaterialTheme.colors.onPrimary
                    ButtonRole.SECONDARY -> MaterialTheme.colors.onPrimary
                    ButtonRole.TERTIARY -> MaterialTheme.colors.onSecondary
                },
                fontSize = 32.sp
            )
        }
    }
}