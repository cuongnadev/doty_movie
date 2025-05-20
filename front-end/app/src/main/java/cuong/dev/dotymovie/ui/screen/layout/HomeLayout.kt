package cuong.dev.dotymovie.ui.screen.layout

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cuong.dev.dotymovie.R
import cuong.dev.dotymovie.ui.theme.AppTheme
import cuong.dev.dotymovie.ui.component.DotyLogoMovie
import cuong.dev.dotymovie.ui.component.TypeLogo
import cuong.dev.dotymovie.viewmodel.AuthViewModel
import cuong.dev.dotymovie.viewmodel.NavigationViewModel

@Composable
fun HomeLayout(
    navController: NavController,
    viewModel: NavigationViewModel,
    authViewModel: AuthViewModel? = hiltViewModel(),
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val selectedItem = viewModel.selectedItem
    val show = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.deepBlack )
    ) {
        Image(
            painter = painterResource(R.drawable.bg),
            contentDescription = "background",
            modifier = Modifier.fillMaxSize()
        )

        Image(
            painter = painterResource(R.drawable.bg_1),
            contentDescription = "background_1",
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 40.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = "avatar",
                    modifier = Modifier
                        .width(46.dp)
                        .height(46.dp)
                        .clip(
                            shape = RoundedCornerShape(50.dp)
                        )
                        .clickable {
                            show.value = !show.value
                        }
                );

                DotyLogoMovie(TypeLogo.Small);

                Box(
                    modifier = Modifier
                        .size(40.dp)
                ) {
                    IconButton(
                        onClick = { navController.navigate("search-movies") },
                        modifier = Modifier
                            .size(40.dp)
                            .border(
                                1.dp, AppTheme.colors.primary,
                                shape = RoundedCornerShape(40.dp)
                            )
                            .align(Alignment.Center)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_search),
                            contentDescription = "notification",
                            tint = AppTheme.colors.primary,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            content()

            Spacer(modifier = Modifier.weight(1f))
        }

        if (show.value) {
            Column (
                modifier = Modifier
                    .offset(x = 24.dp, y = 92.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(AppTheme.colors.deepBlack.copy(0.8f))
                    .border(1.dp, AppTheme.colors.whiteColor.copy(0.6f), shape = RoundedCornerShape(8.dp))
            ) {
                Text(
                    text = "Profile",
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            navController.navigate("profile")
                        },
                    color = AppTheme.colors.whiteColor,
                    style = AppTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.W600,
                    )
                )
                Text(
                    text = "Logout",
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            authViewModel?.logout()
                            navController.navigate("auth")
                        },
                    color = AppTheme.colors.whiteColor,
                    style = AppTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.W600,
                    )
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            BottomNavigationBar(
                selectedItem = selectedItem.value,
                onSelectedItem = { index ->
                    viewModel.setSelectedItem(index)
                    when(index) {
                        0 -> navController.navigate("favorites")
                        1 -> navController.navigate("home")
                        2 -> navController.navigate("tickets")
                    }
                },
            )
        }
    }
}