package cuong.dev.dotymovie.ui.screen.ticket

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import cuong.dev.dotymovie.R
import cuong.dev.dotymovie.constants.TypeStep
import cuong.dev.dotymovie.model.movie.Movie
import cuong.dev.dotymovie.model.showtime.Showtime
import cuong.dev.dotymovie.model.theater.Theater
import cuong.dev.dotymovie.ui.component.ButtonType
import cuong.dev.dotymovie.ui.component.CustomButton
import cuong.dev.dotymovie.ui.component.CustomDropdown
import cuong.dev.dotymovie.ui.component.StepperIndicator
import cuong.dev.dotymovie.ui.theme.AppTheme
import cuong.dev.dotymovie.utils.formatTimeDropdown
import cuong.dev.dotymovie.viewmodel.MovieViewModel
import cuong.dev.dotymovie.viewmodel.ShowtimeViewModel
import cuong.dev.dotymovie.viewmodel.TheaterViewModel
import cuong.dev.dotymovie.viewmodel.TicketViewModel

@Composable
fun BuyTicketScreen(
    navController: NavController,
    movieViewModel: MovieViewModel,
    theaterViewModel: TheaterViewModel,
    showtimeViewModel: ShowtimeViewModel,
    ticketViewModel: TicketViewModel
) {
    var selectedTheater by remember { mutableStateOf<Theater?>(null) }
    var selectedSession by remember { mutableStateOf<Showtime?>(null) }
    val limitSeats by ticketViewModel.limitSeats

    LaunchedEffect(selectedTheater) {
        theaterViewModel.fetchAllTheaters()
        theaterViewModel.theater.value?.id?.let {
            showtimeViewModel.fetchShowtimes(movieViewModel.movie.value!!.id,
                it
            )
        }
        ticketViewModel.clearSeats()
    }

    Box(
        Modifier.fillMaxSize()
    ) {
        movieViewModel.movie.value?.let { TicketBackground(it.medias[0].urlPoster) }
        Box(
            Modifier
                .fillMaxSize()
                .background(AppTheme.colors.deepBlack.copy(0.6f))
        ) {
            Column (
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 36.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                StepperIndicator(navController, TypeStep.BUY_TICKET.value)

                movieViewModel.movie.value?.let { MovieCard(it) }

                IntroductionText()

                MovieOptions(
                    selectedTheater,
                    onTheaterSelected = { selectedTheater = it },
                    selectedSession,
                    onSessionSelected = { selectedSession = it },
                    theaterViewModel,
                    showtimeViewModel,
                    ticketViewModel
                )
            }

            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .background(AppTheme.colors.deepBlack.copy(0.6f))
                ) {
                    CustomButton(
                        onClick = { navController.navigate("ticket-details") },
                        type = ButtonType.FILLED,
                        text = "Next",
                        isTextCentered = true,
                        iconPainter = painterResource(R.drawable.arrow),
                        iconSize = 16.dp,
                        textColor = AppTheme.colors.whiteColor,
                        modifier = Modifier.padding(20.dp),
                        disable = (selectedTheater == null || selectedSession == null || limitSeats == 0)
                    )
                }
            }
        }
    }
}

@Composable
private fun TicketBackground(
    url: String?
) {
    AsyncImage(
        model = url,
        contentDescription = "background",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
    Box(
        Modifier
            .fillMaxSize()
            .background(AppTheme.colors.deepBlack.copy(0.9f))
    )
    Image(
        painter = painterResource(id = R.drawable.bg),
        contentDescription = "background_1",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
    Image(
        painter = painterResource(id = R.drawable.bg_1),
        contentDescription = "background_2",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun MovieCard(
    movie: Movie
) {
    var step by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(244.dp)
            .clip(shape = RoundedCornerShape(14.dp))
    ) {
        AsyncImage(
            model = movie.medias[0].galleryUrl?.get(step),
            contentDescription = "image_card",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(shape = RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .background(AppTheme.colors.deepBlack.copy(0.6f))
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        Modifier
                            .fillMaxHeight()
                            .padding(vertical = 4.dp, horizontal = 12.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = movie.title,
                            color = AppTheme.colors.whiteColor,
                            style = AppTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.W600
                            ),
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.width(180.dp)
                        )
                        Text(
                            "DreamWorks Animation",
                            color = AppTheme.colors.whiteColor,
                            style = AppTheme.typography.bodyMedium,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.width(180.dp)
                        )
                    }

                    CustomButton(
                        onClick = {
                            step++
                            if(step == movie.medias[0].galleryUrl?.size) {
                                step = 0
                            }
                        },
                        type = ButtonType.ICON,
                        iconPainter = painterResource(R.drawable.exchange),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun IntroductionText() {
    Text(
        text = "You need to select the mandatory fields (*) to proceed to the checkout page.",
        color = AppTheme.colors.whiteColor.copy(0.7f),
        style = AppTheme.typography.bodyLarge,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun MovieOptions(
    selectedTheater: Theater?,
    onTheaterSelected: (Theater) -> Unit,
    selectedSession: Showtime?,
    onSessionSelected: (Showtime?) -> Unit,
    theaterViewModel: TheaterViewModel,
    showtimeViewModel: ShowtimeViewModel,
    ticketViewModel: TicketViewModel
) {
    val theaters by theaterViewModel.theaters.collectAsState(initial = emptyList())
    val showtimes by showtimeViewModel.showtimes.collectAsState(initial = emptyList())

    LaunchedEffect(selectedTheater) {
        theaterViewModel.setTheater(selectedTheater)
        onSessionSelected(null)
        showtimeViewModel.setShowtime(null)
    }

    LaunchedEffect(selectedSession) {
        showtimeViewModel.setShowtime(selectedSession)
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        CustomDropdown(
            label = "Choose a Movie Theater *",
            items = theaters,
            selectedItem = selectedTheater,
            itemToText = { it.name },
            onItemSelected = onTheaterSelected
        )

        CustomDropdown(
            label = "Select Session *",
            items = showtimes,
            selectedItem = selectedSession,
            itemToText = { formatTimeDropdown(it.startTime) },
            onItemSelected = onSessionSelected,
            disable = selectedTheater == null
        )

        TicketCountOption(ticketViewModel)
    }
}

@Composable
private fun TicketCountOption (
    ticketViewModel: TicketViewModel
) {
    val ticketCounts by ticketViewModel.ticketCounts.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            "Please select the number of seats you would like to book:",
            color = AppTheme.colors.whiteColor,
            style = AppTheme.typography.titleSmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    AppTheme.colors.deepBlack.copy(0.8f),
                    shape = RoundedCornerShape(14.dp)
                )
                .border(
                    2.dp,
                    AppTheme.colors.whiteColor,
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
                    textColor = AppTheme.colors.whiteColor
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = ticketCounts.adult.toString(),
                        color = if(ticketCounts.adult < 1) AppTheme.colors.whiteColor.copy(0.4f)
                        else AppTheme.colors.whiteColor,
                        style = AppTheme.typography.bodyMedium.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W600
                        )
                    )

                    Text(
                        "ADULT",
                        color = if(ticketCounts.adult < 1) AppTheme.colors.whiteColor.copy(0.4f)
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
                    textColor = AppTheme.colors.whiteColor
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = ticketCounts.child.toString(),
                        color = if(ticketCounts.child < 1) AppTheme.colors.whiteColor.copy(0.4f)
                        else AppTheme.colors.whiteColor,
                        style = AppTheme.typography.bodyMedium.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W600
                        )
                    )

                    Text(
                        "CHILD",
                        color = if(ticketCounts.child < 1) AppTheme.colors.whiteColor.copy(0.4f)
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

        Text("Ticket Terms:\n" +
                "- There are two types of movie tickets: Adult and Child.\n" +
                "- Adult tickets cost \$10 each.\n" +
                "- Child tickets cost \$8 each.",
            color = AppTheme.colors.orangeColor,
            style = AppTheme.typography.titleSmall,
            modifier = Modifier.fillMaxWidth()
        )
    }
}