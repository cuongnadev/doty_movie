package cuong.dev.dotymovie.ui.screen.movie

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import cuong.dev.dotymovie.R
import cuong.dev.dotymovie.model.movie.Movie
import cuong.dev.dotymovie.model.movie.MovieFavoriteDTO
import cuong.dev.dotymovie.model.movie.UpdateMovieDTO
import cuong.dev.dotymovie.ui.component.ButtonType
import cuong.dev.dotymovie.ui.component.CustomButton
import cuong.dev.dotymovie.ui.component.MovieSection
import cuong.dev.dotymovie.ui.component.VideoPlayer
import cuong.dev.dotymovie.ui.theme.AppTheme
import cuong.dev.dotymovie.utils.decodeJWT
import cuong.dev.dotymovie.viewmodel.AuthViewModel
import cuong.dev.dotymovie.viewmodel.MovieViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MovieDetailsScreen(
    navController: NavController,
    movieId: String?,
    movieViewModel: MovieViewModel,
    authViewModel: AuthViewModel
) {
    val movie by movieViewModel.movie
    val scope = rememberCoroutineScope()

    var userId by remember { mutableStateOf<Int?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }
    var areControlsVisible by remember { mutableStateOf(true) }
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val token = authViewModel.getToken()
        val payload = token?.let { decodeJWT(it) }
        userId = payload?.optString("sub")?.toIntOrNull()

        if (userId == null) {
            Log.e("JWT", "Token invalid or missing user ID")
            return@LaunchedEffect
        }

        movieId?.toIntOrNull()?.let { movieViewModel.fetchMovieById(it, userId!!) }
    }

    LaunchedEffect(movie) {
        movie?.let {
            isFavorite = it.isFavorite
        }
    }

    LaunchedEffect(areControlsVisible, isPlaying) {
        if (areControlsVisible && isPlaying) {
            delay(3000)
            areControlsVisible = false
        }
    }

    Box(
        Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.darkGrayBlack)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 40.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clickable {
                        areControlsVisible = true
                    },
                contentAlignment = Alignment.TopCenter
            ) {
                movieViewModel.movie.value?.let {
                    VideoPlayer(
                        url = it.medias[0].urlTrailer,
                        isPlaying,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(AppTheme.colors.transparentToDarkGrayGradient)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(horizontal = 16.dp, vertical = 36.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomButton(
                        onClick = { navController.navigateUp() },
                        type = ButtonType.ICON,
                        iconPainter = painterResource(id = R.drawable.arrow_back),
                        textColor = AppTheme.colors.primary,
                        modifier = Modifier
                            .background(AppTheme.colors.whiteColor, shape = RoundedCornerShape(10.dp))
                            .width(40.dp)
                            .height(40.dp),
                        iconSize = 20.dp
                    )

                    CustomButton(
                        onClick = {
                            isFavorite = !isFavorite
                            val movieIdInt = movieId?.toIntOrNull()
                            scope.launch {
                                if (movieIdInt != null) {
                                    movieViewModel.setFavorite(isFavorite, MovieFavoriteDTO(movieIdInt, userId!!))
                                }
                            }
                        },
                        type = ButtonType.ICON,
                        icon = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        textColor = if (isFavorite) AppTheme.colors.redColor else AppTheme.colors.whiteColor,
                        modifier = Modifier
                            .background(
                                AppTheme.colors.deepBlack.copy(0.5f),
                                shape = RoundedCornerShape(40.dp)
                            )
                            .width(40.dp)
                            .height(40.dp),
                        iconSize = 20.dp
                    )
                }

                if(areControlsVisible) {
                    Box(
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        CustomButton(
                            onClick = {
                                isPlaying = !isPlaying
                            },
                            type = ButtonType.ICON,
                            iconPainter = painterResource(id = R.drawable.play),
                            textColor = AppTheme.colors.primary,
                            modifier = Modifier
                                .width(60.dp)
                                .height(60.dp)
                                .background(
                                    AppTheme.colors.whiteColor.copy(0.5f),
                                    shape = RoundedCornerShape(60.dp)
                                ),
                            iconSize = 20.dp
                        )
                    }
                }
            }

            movieViewModel.movie.value?.let { MovieInfo(it) }

            movieViewModel.movie.value?.let { MovieSubject(it, expanded) { expanded = !expanded } }

            Spacer(Modifier.height(18.dp))

            movieViewModel.movie.value?.let { it.medias[0].galleryUrl?.let { it1 -> MovieImage(it1) } }
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
                    text = "Buy Ticket Now",
                    type = ButtonType.FILLED,
                    onClick = { navController.navigate("buy-ticket") },
                    isTextCentered = true,
                    iconPainter = painterResource(R.drawable.arrow),
                    textColor = AppTheme.colors.whiteColor,
                    modifier = Modifier
                        .padding(20.dp),
                    iconSize = 14.dp
                )
            }
        }
    }
}

@Composable
private fun MovieInfo(
    movie: Movie
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .offset(y = (-100).dp)
    ) {
        AsyncImage(
            model = movie.medias[0].urlPoster,
            contentDescription = "Poster film",
            modifier = Modifier
                .width(154.dp)
                .height(220.dp)
                .clip(shape = RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.width(20.dp))

        Column {
            Text(
                text = movie.title,
                color = AppTheme.colors.whiteColor,
                style = AppTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.W600
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "DreamWorks Animation",
                color = AppTheme.colors.whiteColor,
                style = AppTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.W300
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(6.dp))

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(5) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star",
                        modifier = Modifier.size(20.dp),
                        tint = AppTheme.colors.orangeColor
                    )
                }

                Spacer(Modifier.width(4.dp))

                Text(
                    "(5/5)",
                    color = AppTheme.colors.whiteColor,
                    style = AppTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.W300,
                    )
                )
            }

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.imdb),
                    contentDescription = "Star",
                    modifier = Modifier.size(40.dp),
                    tint = AppTheme.colors.yellowColor
                )

                Spacer(Modifier.width(4.dp))

                Text(
                    "8.1",
                    color = AppTheme.colors.whiteColor,
                    style = AppTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.W300,
                    )
                )
            }
        }
    }
}

@Composable
private fun MovieSubject(
    movie: Movie,
    expanded: Boolean,
    setExpanded: () -> Unit
) {
    val shotText = movie.description.take(233)
    val isLongDescription = movie.description.length > 233
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    Box(
        Modifier.offset(y = (-60).dp)
    ) {
        MovieSection(
            title = "Movie Subject"
        ) {
            val annotatedText = buildAnnotatedString {
                if (expanded || !isLongDescription) {
                    append(movie.description)
                } else {
                    append(shotText)
                    append("... ")
                    pushStringAnnotation(tag = "SEE_ALL", annotation = "See All")
                    withStyle(style = SpanStyle(color = AppTheme.colors.primary)) {
                        append("See All")
                    }
                    pop()
                }
                if (expanded && isLongDescription) {
                    append(". ")
                    pushStringAnnotation(tag = "HIDE", annotation = "Hide")
                    withStyle(style = SpanStyle(color = AppTheme.colors.primary)) {
                        append("Hide")
                    }
                    pop()
                }
            }

            Text(
                text = annotatedText,
                maxLines = if (expanded) Int.MAX_VALUE else 6,
                overflow = TextOverflow.Ellipsis,
                color = AppTheme.colors.whiteColor,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .pointerInput(annotatedText) {
                        detectTapGestures { position ->
                            textLayoutResult?.let { layoutResult ->
                                val offset = layoutResult.getOffsetForPosition(position)

                                val seeAllAnnotations = annotatedText.getStringAnnotations(
                                    tag = "SEE_ALL",
                                    start = offset,
                                    end = offset
                                )
                                if (seeAllAnnotations.isNotEmpty()) {
                                    setExpanded()
                                }

                                val hideAnnotations = annotatedText.getStringAnnotations(
                                    tag = "HIDE",
                                    start = offset,
                                    end = offset
                                )
                                if (hideAnnotations.isNotEmpty()) {
                                    setExpanded()
                                }
                            }
                        }
                    },
                style = AppTheme.typography.titleSmall.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W400
                ),
                onTextLayout = { layoutResult -> textLayoutResult = layoutResult }
            )
        }
    }
}

@Composable
fun MovieImage(
    movieImages: List<String>,
) {
    Box(
        Modifier
            .offset(y = (-50).dp)
    ) {
        MovieSection(
            title = "Images from the movie"
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(movieImages.size) { index ->
                    AsyncImage(
                        model = movieImages[index],
                        contentDescription = "movie image item",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(320.dp)
                            .height(180.dp)
                            .clip(shape = RoundedCornerShape(10.dp))
                    )
                }
            }
        }
    }
}
