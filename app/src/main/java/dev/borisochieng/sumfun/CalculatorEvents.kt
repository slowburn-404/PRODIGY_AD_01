package dev.borisochieng.sumfun

sealed class CalculatorEvents {

    data object Add: CalculatorEvents()

    data object Subtract : CalculatorEvents()

    data object Divide : CalculatorEvents()

    data object Multiply : CalculatorEvents()

    data class EnterNumber(val digit: Int): CalculatorEvents()

    data object EnterDecimal: CalculatorEvents()

    data object Clear: CalculatorEvents()

    data object Delete: CalculatorEvents()

}