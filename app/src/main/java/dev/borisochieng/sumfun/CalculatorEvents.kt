package dev.borisochieng.sumfun

sealed class CalculatorEvents {

    data object Add: CalculatorEvents()

    data class Subtract(val numbers: List<Number>): CalculatorEvents()

    data class Divide(val numbers: List<Number>): CalculatorEvents()

    data class Multiply(val numbers: List<Number>): CalculatorEvents()

    data class EnterNumber(val digit: Int): CalculatorEvents()

    data object EnterDecimal: CalculatorEvents()

    data object Clear: CalculatorEvents()

    data object Delete: CalculatorEvents()

}