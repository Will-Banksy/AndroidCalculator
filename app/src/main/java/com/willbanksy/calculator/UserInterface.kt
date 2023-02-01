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
    val buttonSpacing = 8.dp
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
            BoxWithConstraints {
                if(maxHeight > 400.dp) {
                    Text(
                        text = model.state.prevCalc,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 32.sp
                    )
                }
            }
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(0.dp, 500.dp),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(text = "AC", Modifier.weight(1f), role = ButtonRole.SECONDARY) { model.clear() }
                    CalculatorButton(text = "1", Modifier.weight(1f)) { model.enterNumber("1") }
                    CalculatorButton(text = "4", Modifier.weight(1f)) { model.enterNumber("4") }
                    CalculatorButton(text = "7", Modifier.weight(1f)) { model.enterNumber("7") }
                    CalculatorButton(text = "+/-", Modifier.weight(1f), role = ButtonRole.SECONDARY) { model.invertSign() }
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(text = "DEL", Modifier.weight(1f), role = ButtonRole.SECONDARY) { model.delete() }
                    CalculatorButton(text = "2", Modifier.weight(1f)) { model.enterNumber("2") }
                    CalculatorButton(text = "5", Modifier.weight(1f)) { model.enterNumber("5") }
                    CalculatorButton(text = "8", Modifier.weight(1f)) { model.enterNumber("8") }
                    CalculatorButton(text = "0", Modifier.weight(1f)) { model.enterNumber("0") }
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(text = "^", Modifier.weight(1f), role = ButtonRole.SECONDARY) { model.enterOperator(CalcOperator.POWER) }
                    CalculatorButton(text = "3", Modifier.weight(1f)) { model.enterNumber("3") }
                    CalculatorButton(text = "6", Modifier.weight(1f)) { model.enterNumber("6") }
                    CalculatorButton(text = "9", Modifier.weight(1f)) { model.enterNumber("9") }
                    CalculatorButton(text = ".", Modifier.weight(1f), role = ButtonRole.SECONDARY) { model.enterDecimalPoint() }
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(text = "/", Modifier.weight(1f), role = ButtonRole.SECONDARY) { model.enterOperator(CalcOperator.DIVIDE) }
                    CalculatorButton(text = "×", Modifier.weight(1f), role = ButtonRole.SECONDARY) { model.enterOperator(CalcOperator.MULTIPLY) }
                    CalculatorButton(text = "-", Modifier.weight(1f), role = ButtonRole.SECONDARY) { model.enterOperator(CalcOperator.SUBTRACT) }
                    CalculatorButton(text = "+", Modifier.weight(1f), role = ButtonRole.SECONDARY) { model.enterOperator(CalcOperator.ADD) }
                    CalculatorButton(text = "=", Modifier.weight(1f), role = ButtonRole.TERTIARY) { model.calculate() }
                }
            }
        }
    }
}