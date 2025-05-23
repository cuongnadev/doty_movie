package cuong.dev.dotymovie.ui.screen.ticket

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import cuong.dev.dotymovie.R
import cuong.dev.dotymovie.constants.TicketStatus
import cuong.dev.dotymovie.constants.TypeStep
import cuong.dev.dotymovie.model.ticket.CreateTicketRequest
import cuong.dev.dotymovie.ui.component.ButtonType
import cuong.dev.dotymovie.ui.component.CustomButton
import cuong.dev.dotymovie.ui.component.StepperIndicator
import cuong.dev.dotymovie.ui.theme.AppTheme
import cuong.dev.dotymovie.utils.decodeJWT
import cuong.dev.dotymovie.viewmodel.AuthViewModel
import cuong.dev.dotymovie.viewmodel.MovieViewModel
import cuong.dev.dotymovie.viewmodel.ShowtimeViewModel
import cuong.dev.dotymovie.viewmodel.TicketViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PaymentScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    showtimeViewModel: ShowtimeViewModel,
    ticketViewModel: TicketViewModel
) {
    val countdown = remember { mutableStateOf(300) }
    val isCanceled = remember { mutableStateOf(false) }

    val ticket = ticketViewModel.ticket.value
    val ticketCode = ticket?.ticketCode
    val qrCodeUrl = ticket?.urlQR

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val token = authViewModel.getToken()
        val payload = token?.let { decodeJWT(it) }
        val userId = payload?.optString("sub")?.toIntOrNull()

        if (userId == null) {
            Log.e("JWT", "Token invalid or missing user ID")
            return@LaunchedEffect
        }

        val showtimeId = showtimeViewModel.showtime.value!!.id
        val seatNumbers = ticketViewModel.seatNumbers.value
        val ticketCounts = ticketViewModel.ticketCounts.value

        val ticketData = CreateTicketRequest(
            userId = userId,
            ticketCount = ticketCounts,
            showtimeId = showtimeId,
            seatNumber = seatNumbers,
            amount = ticketViewModel.totalAmount.value
        )

        ticketViewModel.saveTicket(ticketData)
    }

    LaunchedEffect(Unit) {
        while (countdown.value > 0 && !isCanceled.value) {
            delay(1000)
            countdown.value--
        }
        if (countdown.value == 0 && !isCanceled.value) {
            isCanceled.value = true

            ticketCode?.let {
                withContext(Dispatchers.IO) {
                    ticketViewModel.cancelTicket(it)
                }
            }

            withContext(Dispatchers.Main) {
                navController.popBackStack()
            }
        }
    }

    val ticketStatus by ticketViewModel.ticketStatus.collectAsState()
    LaunchedEffect(ticketCode) {
        while (true) {
            ticketCode?.let {
                ticketViewModel.getStatusTicket(it)
            }

            if (ticketStatus == TicketStatus.PAID) {
                navController.navigate("payment-confirm")
                break
            }
            delay(5000)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.deepBlack)
            .padding(horizontal = 16.dp, vertical = 36.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        StepperIndicator(navController, TypeStep.PAYMENT_METHOD.value)

        Column(
            modifier = Modifier
                .background(Color.Transparent),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Scan QR to Pay",
                color = AppTheme.colors.whiteColor.copy(0.8f),
                style = AppTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(top = 24.dp)
            )

            val color = AppTheme.colors.whiteColor

            if (qrCodeUrl != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(20.dp))
                        .background(color)
                        .padding(8.dp)
                        .drawBehind {
                            val borderSize = 4.dp.toPx()
                            drawRoundRect(
                                color = color,
                                size = size,
                                cornerRadius = androidx.compose.ui.geometry.CornerRadius(20.dp.toPx()),
                                style = androidx.compose.ui.graphics.drawscope.Stroke(width = borderSize)
                            )
                        }
                ) {
                    AsyncImage(
                        model = qrCodeUrl,
                        contentDescription = "QR Code",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(16.dp))
                            .aspectRatio(1f)
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = AppTheme.colors.primary,
                        strokeWidth = 4.dp,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }


            Text(
                text = "Time left: ${countdown.value / 60}:${String.format("%02d", countdown.value % 60)}",
                color = if (countdown.value <= 30) AppTheme.colors.redColor else AppTheme.colors.whiteColor.copy(0.8f),
                style = AppTheme.typography.titleSmall
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .background(AppTheme.colors.deepBlack.copy(0.8f)),
        ) {
            val colorBorder = AppTheme.colors.whiteColor

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 64.dp)
            ) {
                Text(
                    "Total Amount",
                    color = AppTheme.colors.whiteColor,
                    style = AppTheme.typography.titleMedium.copy(
                        fontSize = 20.sp,
                        shadow = Shadow(
                            color = AppTheme.colors.whiteColor.copy(alpha = 0.8f),
                            offset = Offset(0f, 0f),
                            blurRadius = 8f
                        )
                    ),
                    modifier = Modifier
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
                    text = "$${ticketViewModel.totalAmount.value}",
                    color = AppTheme.colors.greenColor,
                    style = AppTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.W600,
                        fontSize = 58.sp,
                        shadow = Shadow(
                            color = AppTheme.colors.greenColor.copy(alpha = 0.8f),
                            offset = Offset(0f, 0f),
                            blurRadius = 8f
                        )
                    )
                )

                CustomButton(
                    text = "Cancel",
                    type = ButtonType.FILLED,
                    onClick = {
                        isCanceled.value = true
                        ticketCode?.let {
                            scope.launch {
                                ticketViewModel.cancelTicket(it)
                            }
                        }
                        navController.popBackStack()
                    },
                    isTextCentered = true,
                    iconPainter = painterResource(R.drawable.arrow),
                    textColor = AppTheme.colors.whiteColor,
                    modifier = Modifier
                        .fillMaxWidth(),
                    iconSize = 14.dp,
                    fontSize = 20.sp
                )
            }
        }
    }
}