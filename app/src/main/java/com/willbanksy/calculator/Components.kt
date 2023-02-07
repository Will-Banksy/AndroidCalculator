package com.willbanksy.calculator

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class ButtonRole {
    PRIMARY,
    SECONDARY,
    TERTIARY
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalculatorButton(
    text: String,
    modifier: Modifier = Modifier,
    role: ButtonRole = ButtonRole.PRIMARY,
    onLongClick: () -> Unit = {},
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .then(modifier),
        color = when(role) {
            ButtonRole.PRIMARY -> MaterialTheme.colors.primary
            ButtonRole.SECONDARY -> MaterialTheme.colors.primaryVariant
            ButtonRole.TERTIARY -> MaterialTheme.colors.secondary
        },
        elevation = 8.dp
    ) {
        Box {
            Text(
                text = text,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(unbounded = true), // The unbounded = true means that even the text stays centred even when overflowing
                color = when(role) {
                    ButtonRole.PRIMARY -> MaterialTheme.colors.onPrimary
                    ButtonRole.SECONDARY -> MaterialTheme.colors.onPrimary
                    ButtonRole.TERTIARY -> MaterialTheme.colors.onSecondary
                },
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}