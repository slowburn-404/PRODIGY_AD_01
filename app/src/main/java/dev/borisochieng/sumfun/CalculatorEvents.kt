package dev.borisochieng.sumfun

sealed class CalculatorEvents {

    data class Add(val numbers: List<Number>): CalculatorEvents()

    data class Subtract(val numbers: List<Number>): CalculatorEvents()

    data class Divide(val numbers: List<Number>): CalculatorEvents()

    data class Multiply(val numbers: List<Number>): CalculatorEvents()

    data class EnterNumber(val number: Number): CalculatorEvents()

    data object EnterDecimal: CalculatorEvents()

    data object Clear: CalculatorEvents()

    data object Delete: CalculatorEvents()

}