package cuong.dev.dotymovie.ui.component

import android.util.Pair
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import cuong.dev.dotymovie.constants.TypeMovieItem
import cuong.dev.dotymovie.model.movie.Movie
import cuong.dev.dotymovie.ui.theme.AppTheme

@Composable
fun MovieItem(
    modifier: Modifier,
    movie: Movie,
    type: TypeMovieItem,
    navController: NavController
) {
    val dimension = when(type) {
        TypeMovieItem.HORIZONTAL -> Pair(380.dp, 185.dp)
        TypeMovieItem.VERTICAL -> Pair(160.dp, 220.dp)
    }

    val style = when(type) {
        TypeMovieItem.HORIZONTAL -> AppTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.W600,
            shadow = Shadow(
                color = Color.Black.copy(alpha = 0.8f),
                offset = Offset(0f, 0f),
                blurRadius = 8f
            )
        )
        TypeMovieItem.VERTICAL -> AppTheme.typography.titleMedium.copy(
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            shadow = Shadow(
                color = Color.Black.copy(alpha = 0.8f),
                offset = Offset(0f, 0f),
                blurRadius = 8f
            )
        )
    }
    val imageUrl = movie.medias.getOrNull(0)?.let { media ->
        if(type == TypeMovieItem.HORIZONTAL) media.urlPrimary else media.urlPoster
    } ?: "https://upload.wikimedia.org/wikipedia/commons/6/65/No-Image-Placeholder.svg"

    Card(
        modifier = modifier
            .width(dimension.first)
            .height(dimension.second)
            .clip(shape = RoundedCornerShape(14.dp))
            .clickable { navController.navigate("movie-details/${movie.id}") }
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.fadeToBlackGradient),
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomStart
        ) {
            movie.title.let {
                Text(
                    text = it,
                    color =  AppTheme.colors.whiteColor,
                    style = style,
                    modifier = Modifier
                        .padding(when(type) {
                            TypeMovieItem.HORIZONTAL -> 18.dp
                            TypeMovieItem.VERTICAL -> 10.dp
                        })
                )
            }
        }
    }
}