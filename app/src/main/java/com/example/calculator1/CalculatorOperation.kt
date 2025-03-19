package com.example.calculator1

sealed class CalculatorOperation(val symbol: String) {
    object Add: CalculatorOperation(symbol = "+")
    object Substract: CalculatorOperation(symbol = "-")
    object Multiply: CalculatorOperation(symbol = "x")
    object Divide: CalculatorOperation(symbol = "/")
}