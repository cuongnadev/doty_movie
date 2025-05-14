package cuong.dev.dotymovie.ui.screen.ticket

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cuong.dev.dotymovie.R
import cuong.dev.dotymovie.constants.SeatStatus
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
    val ticketCounts by ticketViewModel.ticketCounts.collectAsState()
    val reservedSeatNumbers = seatsReserved.map { it.seatNumber }
    val limitSeats = selectedSeats.size

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
                    limitSeats,
                    selectedSeats,
                    movieViewModel,
                    theaterViewModel,
                    showtimeViewModel,
                    ticketViewModel
                )
            }
        }
    }

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
                onClick = { navController.navigate("payment") },
                isTextCentered = true,
                iconPainter = painterResource(R.drawable.arrow),
                textColor = AppTheme.colors.whiteColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                iconSize = 14.dp,
                disable = selectedSeats.isEmpty()
                        || (ticketCounts.adult + ticketCounts.child < limitSeats)

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
                                    ticketViewModel.updateSeats(seat.seatNumber)
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
    limitSeats: Int,
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    AppTheme.colors.deepBlack.copy(0.8f),
                    shape = RoundedCornerShape(14.dp)
                )
                .border(
                    2.dp,
                    AppTheme.colors.deepBlack,
                    shape = RoundedCornerShape(14.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(0.44f)
                    .padding(8.dp)
            ) {
                CustomButton(
                    onClick = {
                        ticketViewModel.updateAdult(ticketCounts.adult + 1)
                    },
                    contentPadding = PaddingValues(0.dp),
                    type = ButtonType.ICON,
                    iconPainter = painterResource(R.drawable.plus),
                    textColor = AppTheme.colors.whiteColor,
                    disable = ticketCounts.adult + ticketCounts.child >= limitSeats
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = ticketCounts.adult.toString(),
                        color = if(ticketCounts.child >= limitSeats) AppTheme.colors.whiteColor.copy(0.4f)
                                else AppTheme.colors.whiteColor,
                        style = AppTheme.typography.bodyMedium.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W600
                        )
                    )

                    Text(
                        "ADULT",
                        color = if(ticketCounts.child >= limitSeats) AppTheme.colors.whiteColor.copy(0.4f)
                                else AppTheme.colors.whiteColor,
                        style = AppTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.W500
                        )
                    )
                }

                CustomButton(
                    onClick = {
                        ticketViewModel.updateAdult(ticketCounts.adult - 1)
                    },
                    contentPadding = PaddingValues(0.dp),
                    type = ButtonType.ICON,
                    iconPainter = painterResource(R.drawable.subtract),
                    textColor = AppTheme.colors.whiteColor,
                    disable = ticketCounts.adult == 0
                )
            }

            HorizontalDivider(
                modifier = Modifier
                    .width(2.dp)
                    .height(60.dp)
                    .background(AppTheme.colors.whiteColor.copy(0.6f)),
                thickness = 2.dp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(0.84f)
                    .padding(8.dp)
            ) {
                CustomButton(
                    onClick = {
                        ticketViewModel.updateChild(ticketCounts.child + 1)
                    },
                    contentPadding = PaddingValues(0.dp),
                    type = ButtonType.ICON,
                    iconPainter = painterResource(R.drawable.plus),
                    textColor = AppTheme.colors.whiteColor,
                    disable = ticketCounts.adult + ticketCounts.child >= limitSeats
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,

                ) {
                    Text(
                        text = ticketCounts.child.toString(),
                        color = if(ticketCounts.adult >= limitSeats) AppTheme.colors.whiteColor.copy(0.4f)
                                else AppTheme.colors.whiteColor,
                        style = AppTheme.typography.bodyMedium.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W600
                        )
                    )

                    Text(
                        "CHILD",
                        color = if(ticketCounts.adult >= limitSeats) AppTheme.colors.whiteColor.copy(0.4f)
                                else AppTheme.colors.whiteColor,
                        style = AppTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.W500
                        )
                    )
                }

                CustomButton(
                    onClick = {
                        ticketViewModel.updateChild(ticketCounts.child - 1)
                    },
                    contentPadding = PaddingValues(0.dp),
                    type = ButtonType.ICON,
                    iconPainter = painterResource(R.drawable.subtract),
                    textColor = AppTheme.colors.whiteColor,
                    disable = ticketCounts.child == 0
                )
            }
        }

        TicketItem(
            ticketCounts,
            selectedSeats,
            movie =  movieViewModel.movie.value!!.title,
            theater = theaterViewModel.theater.value?.name ?: "None",
            startTime = showtimeViewModel.showtime.value?.startTime ?: "",
            endTime = showtimeViewModel.showtime.value?.endTime ?: "",
            amount = ticketViewModel.totalAmount.value,
        )
    }
}