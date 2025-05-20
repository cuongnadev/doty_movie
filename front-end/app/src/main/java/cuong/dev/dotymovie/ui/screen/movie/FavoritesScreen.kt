package cuong.dev.dotymovie.ui.screen.movie

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cuong.dev.dotymovie.constants.TypeMovieItem
import cuong.dev.dotymovie.ui.component.MovieItem
import cuong.dev.dotymovie.ui.screen.layout.HomeLayout
import cuong.dev.dotymovie.ui.theme.AppTheme
import cuong.dev.dotymovie.utils.decodeJWT
import cuong.dev.dotymovie.viewmodel.AuthViewModel
import cuong.dev.dotymovie.viewmodel.MovieViewModel
import cuong.dev.dotymovie.viewmodel.NavigationViewModel

@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: NavigationViewModel,
    movieViewModel: MovieViewModel,
    authViewModel: AuthViewModel
) {
    val movies by movieViewModel.movies.collectAsState(initial = emptyList())
    val isLoading by movieViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        val token = authViewModel.getToken()
        val payload = token?.let { decodeJWT(it) }
        val userId = payload?.optString("sub")?.toIntOrNull()

        if (userId == null) {
            Log.e("JWT", "Token invalid or missing user ID")
            return@LaunchedEffect
        }

        movieViewModel.fetchMovieFavorite(userId)
    }

    HomeLayout(navController, viewModel, authViewModel) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 100.dp)
        ) {
            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            color = AppTheme.colors.primary,
                            strokeWidth = 4.dp,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

                movies.isNotEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        movies.forEach { movie ->
                            MovieItem(
                                movie = movie,
                                type = TypeMovieItem.HORIZONTAL,
                                navController = navController,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }
                }

                else -> {
                    Text(
                        "No favorite movies.",
                        color = AppTheme.colors.whiteColor
                    )
                }
            }
        }
    }
}