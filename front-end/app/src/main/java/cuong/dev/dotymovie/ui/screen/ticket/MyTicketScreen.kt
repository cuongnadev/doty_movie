package cuong.dev.dotymovie.ui.screen.ticket

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cuong.dev.dotymovie.R
import cuong.dev.dotymovie.ui.theme.AppTheme

@Composable
fun MyTicketScreen(
    navController: NavController
) {
    Image(
        painter = painterResource(id = R.drawable.bg_primary),
        contentDescription = "Payment Background",
        modifier = Modifier
            .fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "My Ticket",
            color = AppTheme.colors.whiteColor,
            style = AppTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.W600
            ),
            modifier = Modifier
                .padding(36.dp)
        )


    }
}