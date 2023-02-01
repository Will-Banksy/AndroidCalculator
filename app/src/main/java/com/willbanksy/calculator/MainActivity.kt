package com.willbanksy.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.willbanksy.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                val model: CalculatorViewModel by viewModels<CalculatorViewModel>() // Scopes the ViewModel to the activity
                MainUserInterface(model)
            }
        }
    }
}