package com.example.calculator1

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class CalculatorViewModel: ViewModel() {
    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(action: CalculatorActions) {
        when(action) {
            is CalculatorActions.Number -> enterNumber(action.number)
            is CalculatorActions.Decimal -> enterDecimal()
            is CalculatorActions.Clear -> state = CalculatorState()
            is CalculatorActions.Operation -> enterOperation(action.operation)
            is CalculatorActions.Calculate -> performCalculation()
            is CalculatorActions.Delete -> performDeletion()
        }
    }

    private fun enterNumber(number: String) {
        if(state.operation == null) {
            if(state.number1.length >= 8) {
                return
            }
            state = state.copy(number1 = state.number1 + number)
            return
        }
        if(state.number2.length >= 8){
            return
        }
        state = state.copy(number2 = state.number2 + number)
    }

    private fun enterDecimal() {
        if(state.operation == null && !state.number1.contains(".")) {
            state = state.copy(number1 = state.number1 + ".")
            return
        }
        if(!state.number2.contains(".")) {
            state = state.copy(number2 = state.number2 + ".")
        }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if(state.number1 == "."){
            return
        }
        if(state.number1.isBlank()){
            when(operation){
                is CalculatorOperation.Substract -> state = state.copy(number1 = "-", operation = null)
                is CalculatorOperation.Add -> state = state.copy(number1 = "+", operation = null)
                is CalculatorOperation.Divide -> return
                is CalculatorOperation.Multiply -> return
            }
        }
        if(state.number1.isNotBlank()){
            if(state.number1 == "-"){
                when(operation){
                    is CalculatorOperation.Substract -> state = state.copy(number1 = "-", operation = null)
                    is CalculatorOperation.Add -> state = state.copy(number1 = "+", operation = null)
                    is CalculatorOperation.Divide -> return
                    is CalculatorOperation.Multiply -> return
                }
                return
            }
            if(state.number1 == "+"){
                when(operation){
                    is CalculatorOperation.Substract -> state = state.copy(number1 = "-", operation = null)
                    is CalculatorOperation.Add -> state = state.copy(number1 = "+", operation = null)
                    is CalculatorOperation.Divide -> return
                    is CalculatorOperation.Multiply -> return
                }
                return
            }
            state = state.copy(operation = operation)
        }
    }

    private fun performCalculation() {
        if(state.number1.isBlank() || state.number2.isBlank() || state.operation == null){
            return
        }
        if(state.number2 == "."){
            return
        }
        val number1 = state.number1.toDouble()
        val number2 = state.number2.toDouble()
        var result = number1.toDouble()
        if(number1 != null && number2 != null){
            result = when(state.operation){
                is CalculatorOperation.Add -> number1 + number2
                is CalculatorOperation.Multiply -> number1 * number2
                is CalculatorOperation.Divide -> number1 / number2
                is CalculatorOperation.Substract -> number1 - number2
                null -> return
            }
        }
        if(result == result.toInt().toDouble()){
            state = state.copy(
                number1 = result.toInt().toString().take(15),
                number2 = "",
                operation = null
            )
            return
        }
        state = state.copy(
            number1 = result.toString().take(15),
            number2 = "",
            operation = null
        )
    }

    private fun performDeletion() {

            if(state.number2.isNotBlank()) {
                state = state.copy(number2 = state.number2.dropLast(1))
                return
            }
            if(state.operation != null) {
                state = state.copy(operation = null)
                return
            }
            if(state.number1.isNotBlank()) {
                state = state.copy(number1 = state.number1.dropLast(1))
                return
            }

    }

}