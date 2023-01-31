package com.willbanksy.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainUserInterface(model: CalculatorViewModel) {
    val buttonSpacing = 16.dp
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(buttonSpacing)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            Text(
                text = model.state.prevCalc,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                color = MaterialTheme.colors.onBackground,
                fontSize = 32.sp
            )
            Row {
                val text = if(model.state.prevCalc.isNotEmpty()) "=" else ""
                Text(
                    text = text,
                    color = MaterialTheme.colors.secondary,
                    fontSize = 64.sp
                )
                Text(
                    text = model.state.x + (model.state.op?.opString ?: "") + model.state.y,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 64.sp
                )
            }
            Spacer(modifier = Modifier.height(buttonSpacing))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(text = "AC", Modifier.weight(1f), role = ButtonRole.SECONDARY) { model.clear() }
                CalculatorButton(text = "DEL", Modifier.weight(1f), role = ButtonRole.SECONDARY) { model.delete() }
                CalculatorButton(text = "^", Modifier.weight(1f), role = ButtonRole.SECONDARY) { model.enterOperator(CalcOperator.POWER) }
                CalculatorButton(text = "/", Modifier.weight(1f), role = ButtonRole.SECONDARY) { model.enterOperator(CalcOperator.DIVIDE) }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(text = "1", Modifier.weight(1f)) { model.enterNumber("1") }
                CalculatorButton(text = "2", Modifier.weight(1f)) { model.enterNumber("2") }
                CalculatorButton(text = "3", Modifier.weight(1f)) { model.enterNumber("3") }
                CalculatorButton(text = "×", Modifier.weight(1f), role = ButtonRole.SECONDARY) { model.enterOperator(CalcOperator.MULTIPLY) }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(text = "4", Modifier.weight(1f)) { model.enterNumber("4") }
                CalculatorButton(text = "5", Modifier.weight(1f)) { model.enterNumber("5") }
                CalculatorButton(text = "6", Modifier.weight(1f)) { model.enterNumber("6") }
                CalculatorButton(text = "-", Modifier.weight(1f), role = ButtonRole.SECONDARY) { model.enterOperator(CalcOperator.SUBTRACT) }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(text = "7", Modifier.weight(1f)) { model.enterNumber("7") }
                CalculatorButton(text = "8", Modifier.weight(1f)) { model.enterNumber("8") }
                CalculatorButton(text = "9", Modifier.weight(1f)) { model.enterNumber("9") }
                CalculatorButton(text = "+", Modifier.weight(1f), role = ButtonRole.SECONDARY) { model.enterOperator(CalcOperator.ADD) }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(text = "+/-", Modifier.weight(1f), role = ButtonRole.SECONDARY) { model.invertSign() }
                CalculatorButton(text = "0", Modifier.weight(1f)) { model.enterNumber("0") }
                CalculatorButton(text = ".", Modifier.weight(1f), role = ButtonRole.SECONDARY) { model.enterDecimalPoint() }
                CalculatorButton(text = "=", Modifier.weight(1f), role = ButtonRole.TERTIARY) { model.calculate() }
            }
        }
    }
}