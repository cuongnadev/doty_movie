package cuong.dev.dotymovie.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cuong.dev.dotymovie.constants.TicketStatus
import cuong.dev.dotymovie.ui.theme.AppTheme
import cuong.dev.dotymovie.utils.formatTimeTicket
import cuong.dev.dotymovie.viewmodel.TicketCount

@Composable
fun TicketItem(
    ticketCounts: TicketCount,
    selectedSeats: List<String>,
    movie: String,
    theater: String,
    startTime: String,
    endTime: String,
    amount: Int,
    status: String = "pendding"
) {
    val statusMapping = when(status.lowercase()) {
        "paid" -> TicketStatus.PAID
        "confirmed" -> TicketStatus.CONFIRMED
        "cancelled" -> TicketStatus.CANCELLED
        else -> TicketStatus.PENDING
    }
    val borderColor = when (statusMapping) {
        TicketStatus.PAID -> AppTheme.colors.greenColor
        TicketStatus.CONFIRMED -> AppTheme.colors.yellowColor
        TicketStatus.CANCELLED -> AppTheme.colors.redColor
        TicketStatus.PENDING -> AppTheme.colors.semiTransparentWhite
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .then(
                if(statusMapping !== TicketStatus.PAID) Modifier
                    .background(
                        AppTheme.colors.deepBlack.copy(0.8f),
                        shape = RoundedCornerShape(14.dp)
                    )
                else Modifier.clip(shape = RoundedCornerShape(14.dp))
            )
            .border(
                2.dp,
                borderColor,
                shape = RoundedCornerShape(14.dp)
            ),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val colorSeparator = AppTheme.colors.whiteColor.copy(alpha = 0.6f)

        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .then(
                    if(statusMapping === TicketStatus.PAID) Modifier
                        .background(
                            AppTheme.colors.deepBlack.copy(0.8f)
                        )
                        .border(
                            2.dp,
                            AppTheme.colors.deepBlack,
                            shape = RoundedCornerShape(topStart = 14.dp, bottomStart = 14.dp)
                        )
                    else Modifier
                )
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    val dashHeight = 6.dp.toPx()
                    val dashGap = 4.dp.toPx()
                    var y = 0f
                    while (y < (size.height - dashHeight)) {
                        drawLine(
                            color = colorSeparator,
                            start = Offset(x = size.width, y = y),
                            end = Offset(x = size.width, y = y + dashHeight),
                            strokeWidth = strokeWidth
                        )
                        y += dashHeight + dashGap
                    }
                },
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                LabelText(
                    label = "Movie",
                    value = movie
                )
                LabelText(
                    label = "Ticket Count",
                    value = listOfNotNull(
                        ticketCounts.adult.takeIf { it > 0 }?.let { "$it Adult" },
                        ticketCounts.child.takeIf { it > 0 }?.let { "$it Child" },
                    ).joinToString(", ").ifEmpty { "None" },
                    valueColor = AppTheme.colors.whiteColor,
                    labelColor = AppTheme.colors.whiteColor
                )
                LabelText(
                    label = "Session",
                    value = if(startTime.isNotBlank() && endTime.isNotBlank()) formatTimeTicket(startTime, endTime) else "None"
                )
                LabelText(
                    label = "Seat Number",
                    value = if (selectedSeats.isNotEmpty()) {
                        selectedSeats.joinToString(", ") { it.uppercase() }
                    } else "None",
                    valueColor = AppTheme.colors.whiteColor,
                    labelColor = AppTheme.colors.whiteColor
                )
                LabelText(
                    label = "Movie Theater",
                    value = theater
                )
            }
        }

        if(statusMapping === TicketStatus.PAID) Spacer(Modifier.width(10.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .then(
                    if (statusMapping === TicketStatus.PAID) Modifier
                        .background(
                            AppTheme.colors.deepBlack.copy(0.8f)
                        )
                        .border(
                            2.dp,
                            AppTheme.colors.deepBlack,
                            shape = RoundedCornerShape(topEnd = 14.dp, bottomEnd = 14.dp)
                        )
                    else Modifier
                )
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    val dashHeight = 6.dp.toPx()
                    val dashGap = 4.dp.toPx()
                    var y = 0f
                    while (y < (size.height - dashHeight)) {
                        drawLine(
                            color = colorSeparator,
                            start = Offset(x = 0f, y = y),
                            end = Offset(x = 0f, y = y + dashHeight),
                            strokeWidth = strokeWidth
                        )
                        y += dashHeight + dashGap
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            val colorBorder = AppTheme.colors.whiteColor

            Text(
                "Total Amount",
                color = AppTheme.colors.whiteColor,
                style = AppTheme.typography.bodyLarge.copy(
                    fontSize = 14.sp,
                    shadow = Shadow(
                        color = AppTheme.colors.whiteColor.copy(alpha = 0.8f),
                        offset = Offset(0f, 0f),
                        blurRadius = 8f
                    )
                ),
                modifier = Modifier
                    .padding(top = 28.dp)
                    .drawBehind {
                        val strokeWidth = 2.dp.toPx()
                        val y = size.height - strokeWidth / 2


                        drawLine(
                            color = colorBorder,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                    }
            )

            Text(
                text = "$$amount",
                color = AppTheme.colors.greenColor,
                style = AppTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.W600,
                    fontSize = 32.sp,
                    shadow = Shadow(
                        color = AppTheme.colors.greenColor.copy(alpha = 0.8f),
                        offset = Offset(0f, 0f),
                        blurRadius = 8f
                    )
                ),
            )
        }
    }
}