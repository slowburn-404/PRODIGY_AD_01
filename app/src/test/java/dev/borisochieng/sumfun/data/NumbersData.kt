package dev.borisochieng.sumfun.data

val testNumbers = listOf<Number>(
    10,             // Int
    20.51918948,    // Double with high precision
    2.556,            // Double
    3.5f, // Float
    10.5f,
    1000000L   // Long, to check handling of large integers
)

internal fun getNumbers(): List<Number> = testNumbers