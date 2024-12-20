package dev.borisochieng.sumfun

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
 * Test arithmetic operations functionality in the MainActivityViewModel
 *
 */
class MainActivityViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: MainActivityViewModel

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

            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(3))

            viewModel.listenForUiEvents(CalculatorEvents.Add)

            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(5))

            viewModel.listenForUiEvents(CalculatorEvents.EnterDecimal)

            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(6))

            viewModel.listenForUiEvents(CalculatorEvents.CalculateResult)

            //run all coroutines on the scheduler until there is nothing left to queue
            advanceUntilIdle()

            //assert that currentNumberInput is updated with "+"
            assertEquals("3+5.6", viewModel.calculatorState.value.expression)

            //assert that result is updated correctly (3.0 + 5.0 = 8.0)
            assertEquals(8.6, viewModel.calculatorState.value.result)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    internal fun mainActivityViewModel_Subtraction_isCorrect_DifferenceUpdatedErrorFlagUnset() =
        runTest(testDispatcher) {
            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(1))

            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(0))

            viewModel.listenForUiEvents(CalculatorEvents.Subtract)

            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(5))

            viewModel.listenForUiEvents(CalculatorEvents.EnterDecimal)

            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(5))

            viewModel.listenForUiEvents(CalculatorEvents.CalculateResult)


            advanceUntilIdle()

            //assert that expression has been built (10-5.5)

            assertEquals("10-5.5", viewModel.calculatorState.value.expression)
            
            //assert that subtraction is correct

            assertEquals(4.5, viewModel.calculatorState.value.result)

        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    internal fun mainActivityViewModel_Multiplication_isCorrect_ProductUpdatedErrorFlagUnset() =
        runTest(testDispatcher) {
            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(1))
            
            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(0))
            
            viewModel.listenForUiEvents(CalculatorEvents.Multiply)
            
            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(1))
            
            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(0))
            
            viewModel.listenForUiEvents(CalculatorEvents.CalculateResult)
            
            advanceUntilIdle()

            //assert that expression has been built (10×10×)

            assertEquals("10×10", viewModel.calculatorState.value.expression)

            //assert that multiplication is correct

            assertEquals(100, viewModel.calculatorState.value.result)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    internal fun mainActivityViewModel_Division_isCorrect_DividendUpdatedErrorFlagUnset() =
        runTest(testDispatcher) {
            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(5))
            viewModel.listenForUiEvents(CalculatorEvents.Divide)
            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(2))
            viewModel.listenForUiEvents(CalculatorEvents.CalculateResult)
            advanceUntilIdle()

            assertEquals("5÷2", viewModel.calculatorState.value.expression)

            assertEquals(2.5, viewModel.calculatorState.value.result)

        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    internal fun mainActivityViewModel_UpdateExpressionOnThirdNumber() =
        runTest(testDispatcher) {
            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(5))
            viewModel.listenForUiEvents(CalculatorEvents.Add)
            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(3))
            viewModel.listenForUiEvents(CalculatorEvents.Multiply)
            viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(6))
            viewModel.listenForUiEvents(CalculatorEvents.CalculateResult)

            advanceUntilIdle()

            assertEquals("8×6", viewModel.calculatorState.value.expression)
            assertEquals(48, viewModel.calculatorState.value.result)
        }

}