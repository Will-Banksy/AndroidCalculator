package com.willbanksy.calculator

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainUserInterface(model: CalculatorViewModel) {
    val buttonSpacing = 8.dp
    
    val coroutineScope = rememberCoroutineScope()
    val animDragVal = remember { Animatable(0f) }
    val dragMax = 200f
    
    val scaffoldState = rememberScaffoldState()
    val (showSnackBarEval, setShowSnackBarEval) = remember {
        mutableStateOf(false)
    }
    val (showSnackBarLimit, setShowSnackBarLimit) = remember {
        mutableStateOf(false)
    }
    if(showSnackBarEval) {
        LaunchedEffect(key1 = scaffoldState.snackbarHostState) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = "Cannot evaluate incomplete expression",
                actionLabel = "DISMISS"
            )
            setShowSnackBarEval(false)
        }
    }
    if(showSnackBarLimit) {
        LaunchedEffect(key1 = scaffoldState.snackbarHostState) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = "Number limit of 9 characters reached",
                actionLabel = "DISMISS"
            )
            setShowSnackBarLimit(false)
        }
    }
    
    Scaffold(
        modifier = Modifier,
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(buttonSpacing)
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        if (model.state.prevCalc.isNotEmpty()) {
                            coroutineScope.launch {
                                animDragVal.snapTo(animDragVal.value + (delta / dragMax))
                                animDragVal.snapTo(animDragVal.value.coerceAtLeast(0f))
                                if (animDragVal.value > 1f) {
                                    animDragVal.snapTo(1f)
                                }
                            }
                        }
                    },
                    onDragStopped = {
                        coroutineScope.launch {
                            if (animDragVal.value > 0.9f) {
                                animDragVal.animateTo(1f)
                                model.pullDown()
                                animDragVal.snapTo(0f)
                            } else {
                                animDragVal.animateTo(0f)
                            }
                        }
                    }
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                BoxWithConstraints {
                    val mH = maxHeight
                    val heightForMiniText = 300.dp
                    var txtHeightLarge: Int? by remember { mutableStateOf(null) }
                    var txtHeightMain: Int? by remember { mutableStateOf(null) }
                    Column {
                        val heightLeft = mH - with(LocalDensity.current) {
                            if(txtHeightMain != null) {
                                (txtHeightMain!!).toDp()
                            } else {
                                0.dp
                            }
                        }
                        if(heightLeft > heightForMiniText) {
                            Box(
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                Text(
                                    text = model.state.prevCalc,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .drawWithContent {},
                                    onTextLayout = {
                                        txtHeightLarge = it.size.height
                                    },
                                    textAlign = TextAlign.End,
                                    color = Color.Transparent,
                                    fontSize = 64.sp,
                                )
                                Text( // What is actually drawn
                                    text = model.state.prevCalc,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(y = with(LocalDensity.current) {
                                            val dist =
                                                if (txtHeightLarge != null && txtHeightMain != null) {
                                                    txtHeightLarge!!.toDp() + (txtHeightMain!! - txtHeightLarge!!).toDp()
                                                } else {
                                                    0.dp
                                                }
                                            return@with dist * animDragVal.value
                                        }),
                                    textAlign = TextAlign.End,
                                    color = MaterialTheme.colors.onBackground,
                                    fontSize = 32.sp * (animDragVal.value + 1),
                                )
                            }
                        }
                        Box {
                            if(heightLeft <= heightForMiniText) {
                                Text( // What is actually drawn
                                    text = model.state.prevCalc,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.End,
                                    color = MaterialTheme.colors.onBackground.copy(alpha = animDragVal.value),
                                    fontSize = 64.sp,
                                )
                            }
                            Row {
                                val text = if(model.state.prevCalc.isNotEmpty() && model.state.x == model.state.prevAns && model.state.op == null) "=" else ""
                                Text(
                                    text = text,
                                    color = MaterialTheme.colors.secondary.copy(alpha = 1 - animDragVal.value),
                                    fontSize = 64.sp
                                )
                                Text(
                                    text = model.state.x + (model.state.op?.opString ?: "") + model.state.y,
                                    modifier = Modifier
                                        .weight(1f)
                                        .drawWithContent { 
                                            if(txtHeightMain != null) {
                                                drawContent()
                                            }
                                        }
                                        .offset(y = 16.dp * animDragVal.value),
                                    onTextLayout = {
                                        txtHeightMain = it.size.height
                                    },
                                    textAlign = TextAlign.End,
                                    color = MaterialTheme.colors.onBackground.copy(alpha = 1 - animDragVal.value),
                                    fontSize = 64.sp
                                )
                            }
                        }
                    }
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
                        CalculatorButton(text = "1", Modifier.weight(1f)) { (!model.enterNumber("1")).also(setShowSnackBarLimit) }
                        CalculatorButton(text = "4", Modifier.weight(1f)) { (!model.enterNumber("4")).also(setShowSnackBarLimit) }
                        CalculatorButton(text = "7", Modifier.weight(1f)) { (!model.enterNumber("7")).also(setShowSnackBarLimit) }
                        CalculatorButton(text = "+/-", Modifier.weight(1f), role = ButtonRole.SECONDARY) { model.invertSign() }
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        CalculatorButton(text = "DEL", Modifier.weight(1f), role = ButtonRole.SECONDARY, onLongClick = { model.clearCalc() }) { model.delete() }
                        CalculatorButton(text = "2", Modifier.weight(1f)) { (!model.enterNumber("2")).also(setShowSnackBarLimit) }
                        CalculatorButton(text = "5", Modifier.weight(1f)) { (!model.enterNumber("5")).also(setShowSnackBarLimit) }
                        CalculatorButton(text = "8", Modifier.weight(1f)) { (!model.enterNumber("8")).also(setShowSnackBarLimit) }
                        CalculatorButton(text = "0", Modifier.weight(1f)) { (!model.enterNumber("0")).also(setShowSnackBarLimit) }
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        CalculatorButton(text = "^", Modifier.weight(1f), role = ButtonRole.SECONDARY) { model.enterOperator(CalcOperator.POWER) }
                        CalculatorButton(text = "3", Modifier.weight(1f)) { (!model.enterNumber("3")).also(setShowSnackBarLimit) }
                        CalculatorButton(text = "6", Modifier.weight(1f)) { (!model.enterNumber("6")).also(setShowSnackBarLimit) }
                        CalculatorButton(text = "9", Modifier.weight(1f)) { (!model.enterNumber("9")).also(setShowSnackBarLimit) }
                        CalculatorButton(text = ".", Modifier.weight(1f), role = ButtonRole.SECONDARY) { (!model.enterDecimalPoint()).also(setShowSnackBarLimit) }
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
                        CalculatorButton(text = "=", Modifier.weight(1f), role = ButtonRole.TERTIARY) {
                            val success = model.calculate()
                            if(success) {
                                coroutineScope.launch { 
                                    animDragVal.snapTo(1f)
                                    animDragVal.animateTo(0f)
                                }
                            } else {
                                setShowSnackBarEval(true)
                            }
                        }
                    }
                }
            }
        }
    }
}