package cuong.dev.dotymovie.ui.screen.ticket

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cuong.dev.dotymovie.R
import cuong.dev.dotymovie.constants.SeatStatus
import cuong.dev.dotymovie.constants.TicketStatus
import cuong.dev.dotymovie.constants.TypeStep
import cuong.dev.dotymovie.ui.component.ButtonType
import cuong.dev.dotymovie.ui.component.CustomButton
import cuong.dev.dotymovie.ui.component.StepperIndicator
import cuong.dev.dotymovie.ui.component.TicketItem
import cuong.dev.dotymovie.ui.theme.AppTheme
import cuong.dev.dotymovie.utils.buildSeatMatrixWithReservations
import cuong.dev.dotymovie.viewmodel.MovieViewModel
import cuong.dev.dotymovie.viewmodel.SeatViewModel
import cuong.dev.dotymovie.viewmodel.ShowtimeViewModel
import cuong.dev.dotymovie.viewmodel.TheaterViewModel
import cuong.dev.dotymovie.viewmodel.TicketCount
import cuong.dev.dotymovie.viewmodel.TicketViewModel

@Composable
fun TicketDetails(
    navController: NavController,
    movieViewModel: MovieViewModel,
    theaterViewModel: TheaterViewModel,
    showtimeViewModel: ShowtimeViewModel,
    seatViewModel: SeatViewModel,
    ticketViewModel: TicketViewModel
) {
    val selectedSeats by ticketViewModel.seatNumbers.collectAsState(initial = emptyList())
    val seatsReserved by seatViewModel.seats.collectAsState(initial = emptyList())
    val reservedSeatNumbers = seatsReserved.map { it.seatNumber }
    val ticketCounts by ticketViewModel.ticketCounts.collectAsState()

    LaunchedEffect(Unit) {
        seatViewModel.fetchSeatsByShowtime(showtimeViewModel.showtime.value!!.id)
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to AppTheme.colors.deepBlack,
                        0.6f to AppTheme.colors.deepBlack,
                        1.0f to AppTheme.colors.primary.copy(alpha = 0.8f)
                    )
                )
            ),
    ) {
        Column (
            Modifier
                .fillMaxSize()
                .background(AppTheme.colors.deepBlack.copy(0.6f))
                .padding(top = 36.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            StepperIndicator(navController, TypeStep.TICKET_DETAILS.value)

            Column (
                Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                MovieTheaterScreen()

                SeatGrid(
                    reservedSeatNumbers,
                    selectedSeats,
                    ticketViewModel
                )

                TicketDetailsContent(
                    ticketCounts,
                    selectedSeats,
                    movieViewModel,
                    theaterViewModel,
                    showtimeViewModel,
                    ticketViewModel
                )
            }
        }
    }

    var isButtonClicked by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppTheme.colors.deepBlack.copy(0.8f)),
            contentAlignment = Alignment.Center
        ) {
            CustomButton(
                text = "Payment",
                type = ButtonType.FILLED,
                onClick = {
                    if (!isButtonClicked) {
                        isButtonClicked = true
                        navController.navigate("payment")
                    }
                },
                isTextCentered = true,
                iconPainter = painterResource(R.drawable.arrow),
                textColor = AppTheme.colors.whiteColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                iconSize = 14.dp,
                disable = selectedSeats.size != ticketViewModel.limitSeats.value || isButtonClicked
            )
        }
    }
}

@Composable
private fun MovieTheaterScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Screen",
            color = AppTheme.colors.whiteColor,
            style = AppTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.W600
            )
        )

        Box(
            Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(AppTheme.colors.whiteColor, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.BottomCenter
        ) {
            val color = AppTheme.colors.deepBlack
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .align(Alignment.BottomCenter)
            ) {
                drawArc(
                    color = color,
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = true,
                    size = Size(size.width, size.height * 2),
                    topLeft = Offset(0f, 0f)
                )
            }
        }
    }
}

@Composable
private fun SeatGrid(
    reservedSeats : List<String>,
    selectedSeats: List<String>,
    ticketViewModel: TicketViewModel
) {
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val updatedSeatMatrix = buildSeatMatrixWithReservations(reservedSeats);
        updatedSeatMatrix.forEachIndexed { _, row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                row.forEachIndexed { _, seat ->
                    if(seat != null) {
                        val isSelected = selectedSeats.contains(seat.seatNumber)

                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(
                                    color = when {
                                        isSelected -> AppTheme.colors.orangeColor
                                        seat.status == SeatStatus.BOOKED.name -> AppTheme.colors.primary
                                        else -> AppTheme.colors.whiteColor.copy(0.54f)
                                    },
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .clickable(enabled = (seat.status != SeatStatus.BOOKED.name)) {
                                    val success = ticketViewModel.updateSeats(seat.seatNumber)
                                    if (!success) {
                                        Toast.makeText(
                                            context,
                                            "Bạn chỉ có thể chọn tối đa ${ticketViewModel.limitSeats.value} ghế.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        )
                    } else {
                        Spacer(
                            Modifier
                                .width(28.dp)
                                .height(22.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TicketDetailsContent(
    ticketCounts: TicketCount,
    selectedSeats: List<String>,
    movieViewModel: MovieViewModel,
    theaterViewModel: TheaterViewModel,
    showtimeViewModel: ShowtimeViewModel,
    ticketViewModel: TicketViewModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                Modifier
                    .width(8.dp)
                    .height(8.dp)
                    .background(AppTheme.colors.whiteColor, shape = RoundedCornerShape(8.dp))
            )

            Text(
                "Ticket Details",
                color = AppTheme.colors.whiteColor,
                style = AppTheme.typography.titleMedium.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600
                )
            )
        }

        TicketItem(
            ticketCounts,
            selectedSeats,
            movie =  movieViewModel.movie.value!!.title,
            theater = theaterViewModel.theater.value?.name ?: "None",
            startTime = showtimeViewModel.showtime.value?.startTime ?: "",
            endTime = showtimeViewModel.showtime.value?.endTime ?: "",
            amount = ticketViewModel.totalAmount.value
        )

        Text(
            text = "Note:\n" +
                    "- The first two rows are VIP seats priced at $20 each.\n" +
                    "- The remaining seats are standard and cost $15 each.\n" +
                    "- Please choose your seats according to your needs and budget.",
            color = AppTheme.colors.orangeColor,
            style = AppTheme.typography.titleSmall,
            modifier = Modifier.fillMaxWidth()
        )
    }
}