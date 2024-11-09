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

    private val _calculatorResult: MutableStateFlow<CalculatorResult> =
        MutableStateFlow(CalculatorResult())
    val calculatorResult: StateFlow<CalculatorResult> =
        _calculatorResult.asStateFlow() //expose as read only

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
            }
        }

    private fun add(numbers: List<Number>) {
        val sum = performTypedOperation(numbers) { num1, num2 ->
            num1.toDouble() + num2.toDouble()
        }
        _calculatorResult.update { result ->
            result.copy(
                sum = sum
            )
        }
    }


    private fun subtract(numbers: List<Number>) {
        val difference =
            performTypedOperation(numbers) { num1, num2 -> num1.toDouble() - num2.toDouble() }
        _calculatorResult.update { result ->
            result.copy(
                difference = difference
            )
        }
    }


    private fun divide(numbers: List<Number>) {
        val dividend =
            performTypedOperation(numbers) { num1, num2 -> num1.toDouble() / num2.toDouble() }
        _calculatorResult.update { result ->
            result.copy(
                division = dividend
            )
        }
    }


    private fun multiply(numbers: List<Number>) {
        val product =
            performTypedOperation(numbers) { num1, num2 -> num1.toDouble() * num2.toDouble() }
        _calculatorResult.update { result ->
            result.copy(
                product = product
            )
        }
    }

    private fun performTypedOperation(
        numbers: List<Number>?,
        operation: (Number, Number) -> Double
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