package dev.borisochieng.sumfun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.borisochieng.sumfun.ui.theme.SumFunTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SumFunTheme {
                val mainActivityViewModel = viewModel<MainActivityViewModel>()
                val calculatorState =
                    mainActivityViewModel.calculatorState.collectAsStateWithLifecycle()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                ) { innerPadding ->
                    MainScreenLayout(
                        modifier = Modifier
                            .padding(innerPadding)
                            .windowInsetsPadding(WindowInsets.navigationBars),
                        viewModel = mainActivityViewModel,
                        state = calculatorState
                    )
                }
            }
        }
    }
}


@Composable
fun MainScreenLayout(
    viewModel: MainActivityViewModel,
    modifier: Modifier = Modifier,
    state: State<CalculatorState>
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = state.value.expression,
                style = TextStyle(
                    color = Color.LightGray,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.End,
                    fontSize = 24.sp
                ),
                maxLines = 2,
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "= ${state.value.result}",
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.End,
                    fontSize = 36.sp
                ),
                maxLines = 2
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CalculatorButton(
                    symbol = "AC",
                    onClick = {
                        viewModel.listenForUiEvents(CalculatorEvents.Clear)
                    },
                    modifier = Modifier
                        .weight(2f),
                    color = Color.Yellow
                )


                IconButton(
                    modifier = Modifier
                        .clip(CircleShape)
                        .weight(1f),
                    onClick = {
                        viewModel.listenForUiEvents(CalculatorEvents.Delete)
                    },
                    colors = IconButtonColors(
                        contentColor = Color.Black,
                        containerColor = Color.Yellow,
                        disabledContentColor = Color.LightGray,
                        disabledContainerColor = Color.LightGray
                    )
                ) {
                    Icon(
                        modifier = Modifier
                            .size(50.dp),
                        painter = painterResource(R.drawable.delete),
                        contentDescription = "Delete"
                    )

                }

                IconButton(
                    modifier = Modifier
                        .weight(1f),
                    onClick = {
                        viewModel.listenForUiEvents(CalculatorEvents.Multiply)
                    },
                    colors = IconButtonColors(
                        contentColor = Color.Black,
                        containerColor = Color.White,
                        disabledContentColor = Color.LightGray,
                        disabledContainerColor = Color.LightGray
                    )
                ) {
                    Icon(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(50.dp),
                        painter = painterResource(R.drawable.multiply),
                        contentDescription = "Multiply"
                    )

                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CalculatorButton(
                    symbol = "7",
                    onClick = {
                        viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(7))
                    },
                    modifier = Modifier
                        .weight(1f)
                )
                CalculatorButton(
                    symbol = "8",
                    onClick = {
                        viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(8))
                    },
                    modifier = Modifier
                        .weight(1f)
                )
                CalculatorButton(
                    symbol = "9",
                    onClick = {
                        viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(9))
                    },
                    modifier = Modifier
                        .weight(1f)
                )

                IconButton(
                    modifier = Modifier
                        .weight(1f),
                    onClick = {
                        viewModel.listenForUiEvents(CalculatorEvents.Divide)
                    },
                    colors = IconButtonColors(
                        contentColor = Color.Black,
                        containerColor = Color.White,
                        disabledContentColor = Color.LightGray,
                        disabledContainerColor = Color.LightGray
                    )
                ) {
                    Icon(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(50.dp),
                        painter = painterResource(R.drawable.divide),
                        contentDescription = "Divide"
                    )

                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CalculatorButton(
                    symbol = "4",
                    onClick = {
                        viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(4))
                    },
                    modifier = Modifier
                        .weight(1f)
                )
                CalculatorButton(
                    symbol = "5",
                    onClick = {
                        viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(5))
                    },
                    modifier = Modifier
                        .weight(1f)
                )
                CalculatorButton(
                    symbol = "6",
                    onClick = {
                        viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(6))
                    },
                    modifier = Modifier
                        .weight(1f)
                )

                IconButton(
                    modifier = Modifier
                        .weight(1f),
                    onClick = {
                        viewModel.listenForUiEvents(CalculatorEvents.Subtract)
                    },
                    colors = IconButtonColors(
                        contentColor = Color.Black,
                        containerColor = Color.White,
                        disabledContentColor = Color.LightGray,
                        disabledContainerColor = Color.LightGray
                    )
                ) {
                    Icon(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(50.dp),
                        painter = painterResource(R.drawable.subtract),
                        contentDescription = "Subtract"
                    )

                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CalculatorButton(
                    symbol = "1",
                    onClick = {
                        viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(1))
                    },
                    modifier = Modifier
                        .weight(1f)
                )
                CalculatorButton(
                    symbol = "2",
                    onClick = {
                        viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(2))
                    },
                    modifier = Modifier
                        .weight(1f)
                )
                CalculatorButton(
                    symbol = "3",
                    onClick = {
                        viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(3))
                    },
                    modifier = Modifier
                        .weight(1f)
                )
                IconButton(
                    modifier = Modifier
                        .weight(1f),
                    onClick = {
                        viewModel.listenForUiEvents(CalculatorEvents.Add)
                    },
                    colors = IconButtonColors(
                        contentColor = Color.Black,
                        containerColor = Color.White,
                        disabledContentColor = Color.LightGray,
                        disabledContainerColor = Color.LightGray
                    )
                ) {
                    Icon(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(50.dp),
                        painter = painterResource(R.drawable.add),
                        contentDescription = "Delete"
                    )

                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CalculatorButton(
                    symbol = "0",
                    onClick = {
                        viewModel.listenForUiEvents(CalculatorEvents.EnterNumber(0))
                    },
                    modifier = Modifier
                        .weight(1f)
                )
                CalculatorButton(
                    symbol = ".",
                    onClick = {
                        viewModel.listenForUiEvents(CalculatorEvents.EnterDecimal)
                    },
                    modifier = Modifier
                        .weight(1f)
                )

                CalculatorButton(
                    symbol = "=",
                    onClick = {
                        //TODO show total
                        //viewModel.listenForUiEvents(CalculatorEvents.EnterDecimal)
                    },
                    modifier = Modifier
                        .weight(2f),
                    color = Color.Yellow
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenLayoutPreview() {
    MainScreenLayout(
        modifier = Modifier,
        viewModel = viewModel<MainActivityViewModel>(),
        state = viewModel<MainActivityViewModel>().calculatorState.collectAsStateWithLifecycle()
    )
}