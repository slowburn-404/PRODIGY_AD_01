package dev.borisochieng.sumfun

import dev.borisochieng.sumfun.data.getNumbers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainActivityViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: MainActivityViewModel
    private val numbers = getNumbers()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainActivityViewModel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
   internal fun mainActivityViewModel_Addition_isCorrect_SumUpdatedErrorFlagUnset()  =  runTest(testDispatcher) {
        viewModel.listenForUiEvents(CalculatorEvents.Add(numbers))
        //run all coroutines on the scheduler until there is nothing left to queue
        advanceUntilIdle()
        val updatedSum = viewModel.calculatorState.value.result

        assertEquals(EXPECTED_SUM, updatedSum)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun mainActivityViewModel_Subtraction_isCorrect_DifferenceUpdatedErrorFlagUnset() = runTest(testDispatcher) {
        viewModel.listenForUiEvents(CalculatorEvents.Subtract(numbers))
        advanceUntilIdle()
        val updatedDifference = viewModel.calculatorState.value.result
        assertEquals(EXPECTED_DIFFERENCE, updatedDifference)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun mainActivityViewModel_Multiplication_isCorrect_ProductUpdatedErrorFlagUnset() = runTest(testDispatcher) {
        viewModel.listenForUiEvents(CalculatorEvents.Multiply(numbers))
        advanceUntilIdle()
        val updatedProduct = viewModel.calculatorState.value.result
        assertEquals(EXPECTED_PRODUCT, updatedProduct)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun mainActivityViewModel_Division_isCorrect_DividendUpdatedErrorFlagUnset() = runTest(testDispatcher) {
        viewModel.listenForUiEvents(CalculatorEvents.Divide(numbers))
        advanceUntilIdle()
        val updatedDividend = viewModel.calculatorState.value.result
        assertEquals(EXPECTED_DIVIDEND, updatedDividend)

    }

    companion object {
        private const val EXPECTED_SUM = 1000047.07518948
        private const val EXPECTED_DIFFERENCE = -1000027.07518948
        private const val EXPECTED_PRODUCT = 19274290254.248398
        private const val EXPECTED_DIVIDEND = 5.188258487388826E-9
    }
}