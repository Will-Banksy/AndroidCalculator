package com.willbanksy.calculator

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.math.pow

class CalculatorViewModel: ViewModel() {
    var state by mutableStateOf(CalculatorState())

    fun enterNumber(number: String) {
        if(state.op == null) {
            if(state.x.length >= 9) {
                return
            }
            state = state.copy(x = state.x + number)
            return
        }
        if(state.y.length >= 9) {
            return
        }
        state = state.copy(y = state.y + number)
    }

    fun enterOperator(operator: CalcOperator) {
        if(state.x.isNotBlank()) {
            state = state.copy(op = operator)
        }
    }

    fun clear() {
        state = CalculatorState()
    }

    fun delete() {
        if(state.y.isNotBlank()) {
            var delNum = 1
            for(i in 1..state.y.length) {
                val newStr = state.y.substring(0..state.y.length - 1 - i)
                if(newStr.isEmpty() || newStr.toFloatOrNull() != null) {
                    delNum = i
                    break
                }
            }

            state = state.copy(y = state.y.substring(0..state.y.length - 1 - delNum))
        } else if(state.op != null) {
            state = state.copy(op = null)
        } else if(state.x.isNotBlank()) {
            var delNum = 1
            for(i in 1..state.x.length) {
                val newStr = state.x.substring(0..state.x.length - 1 - i)
                if(newStr.isEmpty() || newStr.toFloatOrNull() != null) {
                    delNum = i
                    break
                }
            }

            state = state.copy(x = state.x.substring(0..state.x.length - 1 - delNum))
        }
    }

    fun calculate() {
        val x = state.x.toFloatOrNull()
        val y = state.y.toFloatOrNull()
        if(x != null && state.op != null && y != null) {
            val result = when(state.op) {
                CalcOperator.ADD -> x + y
                CalcOperator.SUBTRACT -> x - y
                CalcOperator.MULTIPLY -> x * y
                CalcOperator.DIVIDE -> x / y
                CalcOperator.POWER -> x.pow(y)
                null -> x // Unreachable
            }
            state = CalculatorState(prevCalc = state.x + state.op!!.opString + state.y, x = result.toString())
        }
    }

    fun invertSign() {
        if(state.op == null) {
            val x = state.x.toFloatOrNull()
            if(x != null) {
                state = state.copy(x = (-x).toString())
            }
        } else {
            val y = state.y.toFloatOrNull()
            if(y != null) {
                state = state.copy(y = (-y).toString())
            }
        }
    }

    fun enterDecimalPoint() {
        if(state.op == null) {
            if(state.x.length >= 8) return
            if(state.x.isNotEmpty()) {
                val x = state.x.toIntOrNull() // Will fail if there is a .
                if(x != null) {
                    state = state.copy(x = state.x + ".")
                }
            } else {
                state = state.copy(x = "0.")
            }
        } else {
            if(state.y.length >= 8) return
            if(state.y.isNotEmpty()) {
                val y = state.y.toIntOrNull() // Will fail if there is a .
                if(y != null) {
                    state = state.copy(y = state.y + ".")
                }
            } else {
                state = state.copy(y = "0.")
            }
        }
    }
}