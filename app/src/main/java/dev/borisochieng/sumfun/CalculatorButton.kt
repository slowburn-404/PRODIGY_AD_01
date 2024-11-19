package dev.borisochieng.sumfun

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CalculatorButton(
    modifier: Modifier = Modifier,
    symbol: String,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .background(color)
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = symbol,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.displaySmall
        )

    }
}