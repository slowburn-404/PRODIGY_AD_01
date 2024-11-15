package dev.borisochieng.sumfun

sealed class CalculatorOperation {
    data object Add: CalculatorOperation()

    data object Subtract: CalculatorOperation()

    data object Multiply: CalculatorOperation()

    data object Divide: CalculatorOperation()
}