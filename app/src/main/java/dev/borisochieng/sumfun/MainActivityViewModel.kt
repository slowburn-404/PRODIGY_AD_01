package dev.borisochieng.sumfun

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.borisochieng.sumfun.OperationExtensions.add
import dev.borisochieng.sumfun.OperationExtensions.divide
import dev.borisochieng.sumfun.OperationExtensions.multiply
import dev.borisochieng.sumfun.OperationExtensions.subtract
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.xml.xpath.XPathExpression
import kotlin.math.exp

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
                is CalculatorEvents.Add -> enterOperator(ADD_SYMBOL)
                is CalculatorEvents.Divide -> enterOperator(DIVIDE_SYMBOL)
                is CalculatorEvents.Multiply -> enterOperator(MULTIPLY_SYMBOL)
                is CalculatorEvents.Subtract -> enterOperator(SUBTRACT_SYMBOL)
                is CalculatorEvents.EnterNumber -> enterNumber(calculatorEvent.digit)
                is CalculatorEvents.EnterDecimal -> enterDecimal()
                is CalculatorEvents.Clear -> clear()
                is CalculatorEvents.Delete -> delete()
                is CalculatorEvents.CalculateResult -> {
                    val currentState = _calculatorState.value
                    doArithmeticOperation(currentState.expression)
                }

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
            if (state.expression.isEmpty()) return@update state
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
        _calculatorState.update { currentState ->
            val lastNumber = currentState.expression.split('+', '-', '×', '÷').last()
            if (lastNumber.contains(".")) return@update currentState

            currentState.copy(
                expression = currentState.expression + "."
            )

        }
    }

    private fun enterOperator(operator: String) {
        _calculatorState.update { currentState ->
            val lastChar = currentState.expression.lastOrNull()
            if (lastChar != null && OPERATORS.contains(lastChar.toString())) return@update currentState

            currentState.copy(
                expression = currentState.expression + operator
            )
        }
    }

    private fun doArithmeticOperation(expression: String) {
        when {
            expression.contains('+') -> calculateResult(CalculatorOperation.Add)
            expression.contains('-') -> calculateResult(CalculatorOperation.Subtract)
            expression.contains('×') -> calculateResult(CalculatorOperation.Multiply)
            expression.contains('÷') -> calculateResult(CalculatorOperation.Divide)

        }
    }

    private fun calculateResult(operation: CalculatorOperation) {

        _calculatorState.update { currentState ->

            val numbers = removeOperationSign(currentState.expression)
                .map { it.toDoubleOrNull() ?: 0.0 }

            if (numbers.size < 2) return@update currentState

            val result = when (operation) {
                is CalculatorOperation.Add -> numbers[0].add(numbers[1])

                is CalculatorOperation.Subtract -> numbers[0].subtract(numbers[1])

                is CalculatorOperation.Divide -> {
                    if (numbers[1] == 0.0) {
                        return@update currentState.copy(
                            expression = ERROR_DIVIDE_BY_ZERO
                        )
                    } else {
                        numbers[0].divide(numbers[1])
                    }
                }

                is CalculatorOperation.Multiply -> numbers[0].multiply(numbers[1])
            }

            currentState.copy(
                result = result
            )
        }


    }

    private fun removeOperationSign(expression: String): List<String> {
        return expression.split(*OPERATORS.toTypedArray())
            .filter {
                it.isNotBlank()
            }
    }

    companion object {
        private const val ADD_SYMBOL = "+"
        private const val SUBTRACT_SYMBOL = "-"
        private const val DIVIDE_SYMBOL = "÷"
        private const val MULTIPLY_SYMBOL = "×"
        private const val ERROR_DIVIDE_BY_ZERO = "Cannot divide by zero"

        private val OPERATORS = listOf(ADD_SYMBOL, SUBTRACT_SYMBOL, DIVIDE_SYMBOL, MULTIPLY_SYMBOL)
    }

}