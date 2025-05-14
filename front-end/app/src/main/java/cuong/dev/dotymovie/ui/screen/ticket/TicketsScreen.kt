package cuong.dev.dotymovie.ui.screen.ticket

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cuong.dev.dotymovie.ui.component.TicketItem
import cuong.dev.dotymovie.ui.screen.layout.HomeLayout
import cuong.dev.dotymovie.ui.theme.AppTheme
import cuong.dev.dotymovie.utils.decodeJWT
import cuong.dev.dotymovie.viewmodel.AuthViewModel
import cuong.dev.dotymovie.viewmodel.NavigationViewModel
import cuong.dev.dotymovie.viewmodel.TicketViewModel

@Composable
fun TicketsScreen(
    navController: NavController,
    viewModel: NavigationViewModel,
    authViewModel: AuthViewModel,
    ticketViewModel: TicketViewModel
) {
    LaunchedEffect(Unit) {
        val token = authViewModel.getToken()
        val payload = token?.let { decodeJWT(it) }
        val userId = payload?.optString("sub")?.toIntOrNull()

        if (userId == null) {
            Log.e("JWT", "Token invalid or missing user ID")
            return@LaunchedEffect
        }

        ticketViewModel.fetchAllTicketsByUser(userId)
    }

    val tickets = ticketViewModel.tickets.collectAsState()

    HomeLayout(navController, viewModel) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            if (tickets.value.isEmpty()) {
                Text(
                    "You do not have any tickets",
                    color = AppTheme.colors.whiteColor
                )
            } else {
                tickets.value.forEach { ticket ->
                    TicketItem(
                        ticketCounts = ticket.ticketCount,
                        selectedSeats = ticket.selectedSeats,
                        movie = ticket.movie,
                        theater = ticket.theater,
                        startTime = ticket.startTime,
                        endTime = ticket.endTime,
                        amount = ticket.amount,
                        isPaid = true
                    )
                }
            }
        }
    }
}