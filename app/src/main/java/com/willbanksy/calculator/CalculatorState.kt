package com.willbanksy.calculator

enum class CalcOperator(val opString: String) {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("×"),
    DIVIDE("/"),
    POWER("^")
}

data class CalculatorState(
    val prevCalc: String = "",
    val prevAns: String = "",
    val prevOperator: CalcOperator? = null,
    val x: String = "",
    val y: String = "",
    val op: CalcOperator? = null
)