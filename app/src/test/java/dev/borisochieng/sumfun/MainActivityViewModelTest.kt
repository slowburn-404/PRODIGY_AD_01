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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainActivityViewModel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
   fun mainActivityViewModel_Addition_isCorrect_SumUpdatedErrorFlagUnset()  =  runTest(testDispatcher) {
        val numbers = getNumbers()
        viewModel.listenForUiEvents(CalculatorEvents.Add(numbers))
        //run all coroutines on the scheduler until there is nothing left to queue
        advanceUntilIdle()
        val updatedState = viewModel.calculatorResult.value

        assertEquals(EXPECTED_SUM, updatedState.sum)
    }

//    @Test
//    fun subtraction_isCorrect() {
//
//    }
//
//    @Test
//    fun multiplication_isCorrect() {
//
//    }
//
//    @Test
//    fun divisionIsCorrect() {
//
//    }

    companion object {
        private const val EXPECTED_SUM = 1000047.07518948
    }
}