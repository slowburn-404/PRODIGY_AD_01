package dev.borisochieng.sumfun

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.borisochieng.sumfun.OperationExtensions.add
import dev.borisochieng.sumfun.OperationExtensions.divide
import dev.borisochieng.sumfun.OperationExtensions.multiply
import dev.borisochieng.sumfun.OperationExtensions.subtract
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.exp

class MainActivityViewModel : ViewModel() {

    private val _calculatorState: MutableStateFlow<CalculatorState> =
        MutableStateFlow(CalculatorState())
    val calculatorState: StateFlow<CalculatorState> =
        _calculatorState.asStateFlow() //expose as read only

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
                    _calculatorState.update { currentState ->
                        if (currentState.expression.endsWithOperator()) return@update currentState

                        val result = calculateResult(currentState.expression)

                        if (result.isNaN()) {
                            return@update currentState.copy(
                                expression = ERROR_DIVIDE_BY_ZERO
                            )
                        } else {
                            currentState.copy(
                                result = result
                            )
                        }
                    }
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
            val updatedExpression = state.expression + digit.toString()

            state.copy(
                expression = updatedExpression
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
            if (currentState.expression.endsWithOperator()) return@update currentState

            val updatedExpression = if (currentState.expression.containsOperator()) {
                val result = calculateResult(currentState.expression)

                return@update currentState.copy(
                    expression = "$result$operator",
                    result = result
                )
            } else {
                currentState.expression + operator
            }

            currentState.copy(
                expression = updatedExpression,
            )
        }
    }

    private fun calculateResult(expression: String): Double {
        val numbers = removeOperationSign(expression)
            .map { it.toDoubleOrNull() ?: 0.0 }

        if (numbers.size < 2) return Double.NaN

        return when {
            expression.contains(ADD_SYMBOL) -> numbers[0].add(numbers[1])

            expression.contains(SUBTRACT_SYMBOL) -> numbers[0].subtract(numbers[1])

            expression.contains(DIVIDE_SYMBOL) -> {
                if (numbers[1] == 0.0) Double.NaN else numbers[0].divide(numbers[1])
            }

            expression.contains(MULTIPLY_SYMBOL) -> numbers[0].multiply(numbers[1])

            else -> Double.NaN
        }

    }

    private fun removeOperationSign(expression: String): List<String> {
        return expression.split(*OPERATORS.toTypedArray())
            .filter {
                it.isNotBlank()
            }
    }

    //avoid expressions like 3+3+3 and only allow 3+3
    private fun String.containsOperator(): Boolean {
        return this.contains('+') ||
                this.contains('-') ||
                this.contains('×') ||
                this.contains('÷')
    }

    private fun String.endsWithOperator(): Boolean {
        val lastChar = this.lastOrNull() ?: return false

        return OPERATORS.contains(lastChar.toString())
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