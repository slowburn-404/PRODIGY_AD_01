package dev.borisochieng.sumfun

/*
* Double is used as it does not lose precision during type conversion
 */
object OperationExtensions {

    fun Double.add(number: Double): Double = this + number

    fun Double.subtract(number: Double): Double = this - number

    fun Double.multiply(number: Double): Double =  this * number

    fun Double.divide(number: Double): Double = this / number
}