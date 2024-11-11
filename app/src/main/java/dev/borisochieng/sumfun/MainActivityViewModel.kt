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
                is CalculatorEvents.Add -> add(calculatorEvent.numbers)
                is CalculatorEvents.Divide -> divide(calculatorEvent.numbers)
                is CalculatorEvents.Multiply -> multiply(calculatorEvent.numbers)
                is CalculatorEvents.Subtract -> subtract(calculatorEvent.numbers)
                is CalculatorEvents.EnterNumber -> enterNumber(calculatorEvent.number)
                is CalculatorEvents.EnterDecimal -> {}
                is CalculatorEvents.Clear -> clear()
                is CalculatorEvents.Delete -> delete()
            }
        }

    private fun clear() {
        _calculatorState.update { state ->
            state.copy(
                numbersInput = emptyList()
            )
        }
    }

    private fun delete() {

        _calculatorState.update { state ->
            //delete each item from the list backwards
            state.copy(
                numbersInput = state.numbersInput.dropLast(state.numbersInput.size - 1)
            )
        }
    }

    private fun enterNumber(number: Number) {
        val numbersInput = mutableListOf<Number>()
        numbersInput.add(number)
        _calculatorState.update { state ->
            state.copy(
                numbersInput = numbersInput
            )
        }
    }

    private fun add(numbers: List<Number>) {
        val sum = performArithmeticOperation(numbers) { num1, num2 ->
            num1.toDouble() + num2.toDouble()
        }
        sum?.let {
            _calculatorState.update { result ->
                result.copy(
                    result = it
                )
            }
        }
    }


    private fun subtract(numbers: List<Number>) {
        val difference =
            performArithmeticOperation(numbers) { num1, num2 -> num1.toDouble() - num2.toDouble() }

        difference?.let {
            _calculatorState.update { result ->
                result.copy(
                    result = it
                )
            }
        }
    }


    private fun divide(numbers: List<Number>) {
        val dividend =
            performArithmeticOperation(numbers) { num1, num2 -> num1.toDouble() / num2.toDouble() }

        dividend?.let {
            _calculatorState.update { result ->
                result.copy(
                    result = it
                )
            }
        }
    }


    private fun multiply(numbers: List<Number>) {
        val product =
            performArithmeticOperation(numbers) { num1, num2 -> num1.toDouble() * num2.toDouble() }

        product?.let {
            _calculatorState.update { result ->
                result.copy(
                    result = it
                )
            }
        }
    }

    private fun performArithmeticOperation(
        numbers: List<Number>?,
        operation: (Number, Number) -> Number
    ): Number? {
        if (numbers.isNullOrEmpty()) return null
        /*
        * Perform operation treating all numbers as their native types
        * */
        return numbers.reduce { acc, number ->
            operation(acc, number)
        }
    }

}