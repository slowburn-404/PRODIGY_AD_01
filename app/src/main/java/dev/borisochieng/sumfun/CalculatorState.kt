package dev.borisochieng.sumfun

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class CalculatorState(
    val numbersInput: List<Number> = emptyList(),
    val result: Number = 0
)
