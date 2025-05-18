package cuong.dev.dotymovie.ui.screen.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cuong.dev.dotymovie.R
import cuong.dev.dotymovie.constants.TypeMovieItem
import cuong.dev.dotymovie.ui.component.MovieItem
import cuong.dev.dotymovie.ui.theme.AppTheme
import cuong.dev.dotymovie.viewmodel.MovieViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun SearchScreen(
    navController: NavController,
    movieViewModel: MovieViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    val searchResults by movieViewModel.movies.collectAsState(initial = emptyList())
    val isLoading by movieViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        snapshotFlow { searchQuery }
            .debounce(2000L)
            .filter { it.isNotBlank() }
            .distinctUntilChanged()
            .collect{ query ->
                movieViewModel.searchMovie(query)
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.bg_primary),
                contentDescription = "search_bg_primary",
                contentScale = ContentScale.Crop
            )
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.bg),
                contentDescription = "search_bg",
                contentScale = ContentScale.Crop
            )
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.bg_1),
                contentDescription = "search_bg_1",
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                },
                label = {
                    Text(
                        "Search movies",
                        color = AppTheme.colors.primary,
                        style = AppTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.W600
                        ),
                        modifier = Modifier
                            .background(Color.Transparent)
                            .padding(2.dp)
                    )
                },
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = AppTheme.colors.primary,
                    unfocusedTextColor = AppTheme.colors.semiTransparentWhite,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = AppTheme.colors.transparentGray40,
                    disabledContainerColor = AppTheme.colors.transparentGray40,
                    cursorColor = AppTheme.colors.primary,
                    focusedLabelColor = AppTheme.colors.primary,
                    unfocusedLabelColor = AppTheme.colors.transparentWhite27,
                    focusedIndicatorColor = AppTheme.colors.primary,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(16.dp))

            if (searchQuery.isNotBlank()) {
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

                    searchResults.isNotEmpty() -> {
                        LazyColumn(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            items(searchResults) { movie ->
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
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Text(
                                "No movies found!",
                                style = AppTheme.typography.titleSmall,
                                color = AppTheme.colors.orangeColor
                            )
                        }
                    }
                }
            }
        }
    }

}