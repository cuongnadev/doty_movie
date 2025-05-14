package cuong.dev.dotymovie.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cuong.dev.dotymovie.constants.TypeMovieItem
import cuong.dev.dotymovie.model.movie.Movie
import cuong.dev.dotymovie.ui.theme.AppTheme

@Composable
fun MovieCarousel(
    listMovie: List<Movie>,
    typeMovie: TypeMovieItem,
    indicator: Boolean? = false,
    navController: NavController
) {
    val pageState = rememberPagerState(initialPage = 1, pageCount = { listMovie.size })

    HorizontalPager(
        state = pageState,
        contentPadding = PaddingValues(horizontal = 26.dp),
        pageSpacing = 12.dp
    ) { page ->
        val pageOffset = (pageState.currentPage - page) + pageState.currentPageOffsetFraction
        val scale = 0.85f + (1 - kotlin.math.abs(pageOffset)) * 0.15f

        MovieItem(
            modifier = Modifier.graphicsLayer {
                scaleY = scale
            },
            movie = listMovie[page],
            type = typeMovie,
            navController
        )
    }

    Spacer(Modifier.height(14.dp))

    if(indicator == true) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(listMovie.size) { index ->
                val selected = pageState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .width(if(selected) 18.dp else 8.dp)
                        .height(8.dp)
                        .background(
                            color = if(selected) AppTheme.colors.primary else Color.Gray,
                            shape = RoundedCornerShape(50)
                        )
                )
            }
        }
    }
}