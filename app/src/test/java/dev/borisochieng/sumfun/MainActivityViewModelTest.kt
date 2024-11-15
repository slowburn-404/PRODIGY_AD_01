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
    internal fun mainActivityViewModel_Addition_isCorrect_SumUpdatedErrorFlagUnset() =
        runTest(testDispatcher) {

            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(33))

            viewModel.listenForUiEvents(CalculatorEvents.Add)

            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(5))

            viewModel.listenForUiEvents(CalculatorEvents.EnterDecimal)

            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(6))

            viewModel.listenForUiEvents(CalculatorEvents.Add)

            //run all coroutines on the scheduler until there is nothing left to queue
            advanceUntilIdle()

            //assert that currentNumberInput is updated with "+"
            assertEquals("33.0+5.6", viewModel.calculatorState.value.expression)

            //assert that result is updated correctly (3.0 + 5.0 = 8.0)
            assertEquals(38.6, viewModel.calculatorState.value.result)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun mainActivityViewModel_Subtraction_isCorrect_DifferenceUpdatedErrorFlagUnset() =
        runTest(testDispatcher) {
            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(1))

            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(0))

            viewModel.listenForUiEvents(CalculatorEvents.Subtract)

            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(5))

            viewModel.listenForUiEvents(CalculatorEvents.EnterDecimal)

            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(5))

            viewModel.listenForUiEvents(CalculatorEvents.Subtract)


            advanceUntilIdle()

            //assert that expression has been built (5 - 5.5 -)

            assertEquals("10-5.5-", viewModel.calculatorState.value.expression)
            
            //assert that subtraction is correct

            assertEquals(5.5, viewModel.calculatorState.value.result)

        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun mainActivityViewModel_Multiplication_isCorrect_ProductUpdatedErrorFlagUnset() =
        runTest(testDispatcher) {
            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(1))
            
            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(0))
            
            viewModel.listenForUiEvents(CalculatorEvents.Multiply)
            
            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(1))
            
            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(0))
            
            viewModel.listenForUiEvents(CalculatorEvents.Multiply)
            
            advanceUntilIdle()

            //assert that expression has been built (10×10×)

            assertEquals("10×10×", viewModel.calculatorState.value.expression)

            //assert that multiplication is correct

            assertEquals(100, viewModel.calculatorState.value.result)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun mainActivityViewModel_Division_isCorrect_DividendUpdatedErrorFlagUnset() =
        runTest(testDispatcher) {
            viewModel.listenForUiEvents(CalculatorEvents.Divide)
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