package cuong.dev.dotymovie.ui.screen.layout

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cuong.dev.dotymovie.R
import cuong.dev.dotymovie.ui.theme.AppTheme

@Composable
fun BottomNavigationBar(
    selectedItem: Int,
    onSelectedItem: (Int) -> Unit
) {
    val items = listOf(
        "Favorite" to Icons.Outlined.FavoriteBorder,
        "Home" to Icons.Outlined.Home,
        "Tickets" to R.drawable.tickets
    )


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(
                color = AppTheme.colors.transparentBlack,
                shape = RoundedCornerShape(
                    topStart = 60.dp,
                    topEnd = 60.dp
                )
            )
            .padding(top = 12.dp)
    ) {
        NavigationBar(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent
        ) {
            items.forEachIndexed { index, (title, icon) ->
                val scale by animateFloatAsState(
                    if (selectedItem == index) 1.1f else 1f,
                    label = "ScaleAnimation"
                )

                NavigationBarItem(
                    selected = selectedItem == index,
                    onClick = { onSelectedItem(index) },
                    icon = {
                        Box(
                            modifier = Modifier
                                .scale(scale)
                                .padding(4.dp)
                        ) {
                            when (icon) {
                                is ImageVector -> Icon(
                                    imageVector = icon,
                                    contentDescription = title,
                                    tint = if (selectedItem == index) AppTheme.colors.primary
                                        else AppTheme.colors.whiteColor
                                )
                                is Int -> Icon(
                                    painter = painterResource(id = icon),
                                    contentDescription = title,
                                    tint = if (selectedItem == index) AppTheme.colors.primary
                                        else AppTheme.colors.whiteColor
                                )
                            }
                        }
                    },
                    label = null,
                )
            }
        }
    }
}