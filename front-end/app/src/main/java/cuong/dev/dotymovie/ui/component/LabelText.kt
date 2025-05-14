package cuong.dev.dotymovie.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cuong.dev.dotymovie.ui.theme.AppTheme

@Composable
fun LabelText(
    label: String,
    value: String,
    valueColor: Color = AppTheme.colors.whiteColor.copy(0.4f),
    labelColor: Color = AppTheme.colors.whiteColor.copy(0.4f),
    showValue: Boolean = true
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "$label:",
            color = labelColor,
            style = AppTheme.typography.bodyLarge
        )
        if(showValue) {
            Text(
                text = value,
                color = valueColor,
                style = AppTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
            )
        }
    }
}