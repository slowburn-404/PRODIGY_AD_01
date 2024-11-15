package dev.borisochieng.sumfun

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val _calculatorState: MutableStateFlow<CalculatorState> =
        MutableStateFlow(CalculatorState())
    val calculatorState: StateFlow<CalculatorState> =
        _calculatorState.asStateFlow() //expose as read only

    private val _calculatorEvents: MutableSharedFlow<CalculatorEvents> = MutableSharedFlow()
    val calculatorEvents: SharedFlow<CalculatorEvents> =
        _calculatorEvents.asSharedFlow() //expose as read only

    fun listenForUiEvents(calculatorEvent: CalculatorEvents) =
        viewModelScope.launch {
            when (calculatorEvent) {
                is CalculatorEvents.Add -> add()
                is CalculatorEvents.Divide -> divide()
                is CalculatorEvents.Multiply -> multiply()
                is CalculatorEvents.Subtract -> subtract()
                is CalculatorEvents.EnterNumber -> enterNumber(calculatorEvent.digit)
                is CalculatorEvents.EnterDecimal -> enterDecimal()
                is CalculatorEvents.Clear -> clear()
                is CalculatorEvents.Delete -> delete()
            }
        }

    private fun clear() {
        _calculatorState.update { state ->
            state.copy(
                expression = "",
                result = 0
            )
        }
    }

    private fun delete() {

        _calculatorState.update { state ->
            //delete each item from the string backwards
            state.copy(
                expression = state.expression.dropLast(1)
            )
        }
    }

    private fun enterNumber(digit: Int) {
        _calculatorState.update { state ->
            val updatedInput = state.expression + digit.toString()

            state.copy(
                expression = updatedInput
            )
        }
    }

    private fun enterDecimal() {
        _calculatorState.update { state ->
            state.copy(
                expression = state.expression + "."
            )

        }
    }

    private fun add() {
        val state = _calculatorState.value

        val lastNumber = state.expression
            .trimEnd('+', '-', '×', '÷')
            .split("+")
            .lastOrNull()
            ?.toDoubleOrNull() ?: return // return early if input is invalid

        if(state.expression.endsWith('+')) return

        val currentSum = performArithmeticOperation(
            num1 = state.result,
            num2 = lastNumber,
            operation = CalculatorOperation.Add
        )

        _calculatorState.update { currentState ->
            currentState.copy(
                expression = "$currentSum+",
                result = currentSum
            )
        }
    }


    private fun subtract() {
        val state = _calculatorState.value

        val lastNumber = state.expression
            .trimEnd('+', '-', '×', '÷')
            .split("-")
            .lastOrNull()
            ?.toDoubleOrNull() ?: return // return early if input is invalid

        val currentDifference = performArithmeticOperation(
            num1 = state.result,
            num2 = lastNumber,
            operation = CalculatorOperation.Subtract
        )

        _calculatorState.update { currentState ->
            currentState.copy(
                expression = "$currentDifference-",
                result = currentDifference
            )
        }

    }


    private fun divide() {
        val state = _calculatorState.value

        val lastNumber = state.expression
            .trimEnd('+', '-', '×', '÷')
            .split("÷")
            .lastOrNull()
            ?.toDoubleOrNull() ?: return // return early if input is invalid

        val currentDividend = performArithmeticOperation(
            num1 = state.result,
            num2 = lastNumber,
            operation = CalculatorOperation.Divide
        )

        _calculatorState.update { currentState ->
            currentState.copy(
                expression = "$currentDividend÷",
                result = currentDividend
            )
        }
    }


    private fun multiply() {
        val state = _calculatorState.value

        val lastNumber = state.expression
            .trimEnd('+', '-', '×', '÷')
            .split("×")
            .lastOrNull()
            ?.toDoubleOrNull() ?: return // return early if input is invalid

        val currentProduct = performArithmeticOperation(
            num1 = state.result,
            num2 = lastNumber,
            operation = CalculatorOperation.Multiply
        )

        _calculatorState.update { currentState ->
            currentState.copy(
                expression = "$currentProduct×",
                result = currentProduct
            )
        }
    }

    private fun performArithmeticOperation(
        num1: Number,
        num2: Number,
        operation: CalculatorOperation
    ): Number {
        return when (operation) {
            is CalculatorOperation.Add -> {
                num1.toDouble() + num2.toDouble()
            }

            is CalculatorOperation.Subtract -> {
                num1.toDouble() - num2.toDouble()
            }

            is CalculatorOperation.Divide -> {
                num1.toDouble() / num2.toDouble()
            }

            is CalculatorOperation.Multiply -> {
                num1.toDouble() * num2.toDouble()
            }

        }
    }

}