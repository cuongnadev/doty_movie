package cuong.dev.dotymovie.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cuong.dev.dotymovie.ui.screen.*
import cuong.dev.dotymovie.ui.screen.auth.AuthenticationScreen
import cuong.dev.dotymovie.ui.screen.auth.SignInScreen
import cuong.dev.dotymovie.ui.screen.auth.SignUpScreen
import cuong.dev.dotymovie.ui.screen.auth.VerificationEmailScreen
import cuong.dev.dotymovie.ui.screen.movie.FavoritesScreen
import cuong.dev.dotymovie.ui.screen.movie.MovieDetailsScreen
import cuong.dev.dotymovie.ui.screen.ticket.BuyTicketScreen
import cuong.dev.dotymovie.ui.screen.ticket.MyTicketScreen
import cuong.dev.dotymovie.ui.screen.ticket.PaymentConfirmScreen
import cuong.dev.dotymovie.ui.screen.ticket.PaymentScreen
import cuong.dev.dotymovie.ui.screen.ticket.TicketDetails
import cuong.dev.dotymovie.ui.screen.ticket.TicketsScreen
import cuong.dev.dotymovie.viewmodel.AuthViewModel
import cuong.dev.dotymovie.viewmodel.MovieViewModel
import cuong.dev.dotymovie.viewmodel.NavigationViewModel
import cuong.dev.dotymovie.viewmodel.PaymentViewModel
import cuong.dev.dotymovie.viewmodel.SeatViewModel
import cuong.dev.dotymovie.viewmodel.ShowtimeViewModel
import cuong.dev.dotymovie.viewmodel.TheaterViewModel
import cuong.dev.dotymovie.viewmodel.TicketViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: NavigationViewModel = viewModel()
    val paymentViewModel: PaymentViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val movieViewModel: MovieViewModel = hiltViewModel()
    val theaterViewModel: TheaterViewModel = hiltViewModel()
    val showtimeViewModel: ShowtimeViewModel = hiltViewModel()
    val seatViewModel: SeatViewModel = hiltViewModel()
    val ticketViewModel: TicketViewModel = hiltViewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    LaunchedEffect(navBackStackEntry) {
        val let = navBackStackEntry?.destination?.route?.let { route ->
            viewModel.updateSelectedItem(route)
        }
    }


    NavHost(
        navController = navController,
        startDestination = if(authViewModel.isUserLoggedIn()) "home" else "auth"
    ) {
        composable(
            route = "auth",
        ) {
            AuthenticationScreen(navController, authViewModel)
        }

        composable(
            route = "sign-in",
        ) {
            SignInScreen(navController, authViewModel)
        }

        composable(
            route = "sign-up",
        ) {
            SignUpScreen(navController, authViewModel)
        }

        composable(
            route = "verification-email",
        ) {
            VerificationEmailScreen(navController, authViewModel)
        }

        composable(
            route = "home",
        ) {
            HomeScreen(navController, viewModel, authViewModel, movieViewModel)
        }

        composable(
            route = "favorites",
        ) {
            FavoritesScreen(
                navController,
                viewModel,
                movieViewModel,
                authViewModel
            )
        }

        composable(
            route = "tickets",
        ) {
            TicketsScreen(
                navController,
                viewModel,
                authViewModel,
                ticketViewModel
            )
        }

        composable(
            route = "movie-details/{movieId}"
        ) { navBackStackEntry ->
            val  movieId = navBackStackEntry.arguments?.getString("movieId")
            MovieDetailsScreen(
                navController,
                movieId,
                movieViewModel,
                authViewModel
            )
        }

        composable(
            route = "buy-ticket"
        ) {
            BuyTicketScreen(
                navController,
                movieViewModel,
                theaterViewModel,
                showtimeViewModel,
                ticketViewModel
            )
        }

        composable(
            route = "ticket-details"
        ) {
            TicketDetails(
                navController,
                movieViewModel,
                theaterViewModel,
                showtimeViewModel,
                seatViewModel,
                ticketViewModel
            )
        }

        composable(
            route = "payment"
        ) {
            PaymentScreen(
                navController,
                paymentViewModel,
                authViewModel,
                movieViewModel,
                showtimeViewModel,
                ticketViewModel
            )
        }

        composable(
            route = "payment-confirm"
        ) {
            PaymentConfirmScreen(navController, paymentViewModel)
        }

        composable(
            route = "my-ticket"
        ) {
            MyTicketScreen(navController)
        }
    }
}
