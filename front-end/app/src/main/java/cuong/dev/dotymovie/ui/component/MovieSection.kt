package cuong.dev.dotymovie.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cuong.dev.dotymovie.ui.theme.AppTheme

@Composable
fun MovieSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier
                .padding(start = 24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .width(8.dp)
                        .height(8.dp)
                        .background(
                            AppTheme.colors.whiteColor,
                            shape = RoundedCornerShape(20.dp)
                        )
                )
                Text(title,
                    color = Color.White,
                    style = AppTheme.typography.titleSmall.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W600
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }

        Spacer(Modifier.height(10.dp))

        content()
    }
}