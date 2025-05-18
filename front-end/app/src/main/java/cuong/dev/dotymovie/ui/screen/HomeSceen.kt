package cuong.dev.dotymovie.ui.screen

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import cuong.dev.dotymovie.constants.TypeMovieItem
import cuong.dev.dotymovie.ui.component.MovieCarousel
import cuong.dev.dotymovie.ui.component.MovieItem
import cuong.dev.dotymovie.ui.component.MovieSection
import cuong.dev.dotymovie.ui.screen.layout.HomeLayout
import cuong.dev.dotymovie.ui.theme.AppTheme
import cuong.dev.dotymovie.viewmodel.AuthViewModel
import cuong.dev.dotymovie.viewmodel.MovieViewModel
import cuong.dev.dotymovie.viewmodel.NavigationViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: NavigationViewModel,
    authViewModel: AuthViewModel,
    movieViewModel: MovieViewModel
) {
    val movieHighlights by movieViewModel.movieHighlights.collectAsState(initial = emptyList())
    val newMovies by movieViewModel.newMovies.collectAsState(initial = emptyList())
    val comingSoonMovies by movieViewModel.comingSoonMovies.collectAsState(initial = emptyList())
    val isLoading by movieViewModel.isLoading.collectAsState()

    val view = LocalView.current
    if(!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.setDecorFitsSystemWindows(window, false)

            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        }
    }

    LaunchedEffect(Unit) {
        movieViewModel.fetchMovieHighLights()
        movieViewModel.fetchNewMovies()
        movieViewModel.fetchComingSoonMovies()
    }

    HomeLayout(navController, viewModel, authViewModel) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 100.dp),
        ) {
            MovieSection(
                title = "HighLights"
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

                    movieHighlights.isNotEmpty() -> {
                        MovieCarousel(
                            listMovie = movieHighlights,
                            typeMovie = TypeMovieItem.HORIZONTAL,
                            indicator = true,
                            navController
                        )
                    }

                    else -> {
                        Text(
                            "No data movie highlights",
                            color = AppTheme.colors.whiteColor
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            MovieSection(
                title = "New Movies In Theaters"
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

                    newMovies.isNotEmpty() -> {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 26.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(newMovies.size) { index ->
                                MovieItem(
                                    modifier = Modifier.width(160.dp),
                                    movie = newMovies[index],
                                    type = TypeMovieItem.VERTICAL,
                                    navController
                                )
                            }
                        }
                    }

                    else -> {
                        Text(
                            "No data new movies",
                            color = AppTheme.colors.whiteColor
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            MovieSection(
                title = "Coming Soon"
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

                    comingSoonMovies.isNotEmpty() -> {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 26.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(comingSoonMovies.size) { index ->
                                MovieItem(
                                    modifier = Modifier.width(160.dp),
                                    movie = comingSoonMovies[index],
                                    type = TypeMovieItem.VERTICAL,
                                    navController
                                )
                            }
                        }
                    }

                    else -> {
                        Text(
                            "No data coming soon movies",
                            color = AppTheme.colors.whiteColor
                        )
                    }
                }
            }
        }
    }
}