package cuong.dev.dotymovie.ui.screen.ticket

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cuong.dev.dotymovie.R
import cuong.dev.dotymovie.constants.TypeStep
import cuong.dev.dotymovie.ui.component.ButtonType
import cuong.dev.dotymovie.ui.component.CustomButton
import cuong.dev.dotymovie.ui.component.StepperIndicator
import cuong.dev.dotymovie.ui.theme.AppTheme
import cuong.dev.dotymovie.viewmodel.PaymentViewModel

@Composable
fun PaymentConfirmScreen(
    navController: NavController,
    paymentViewModel: PaymentViewModel
) {
    val paymentSuccess by paymentViewModel.paymentSuccess.collectAsState()
    val background = if(paymentSuccess!!) R.drawable.payment_successful_bg else R.drawable.bg_primary
    val icon = if(paymentSuccess!!) R.drawable.payment_successful else R.drawable.payment_failed
    val paymentStatus = if(paymentSuccess!!) "Payment SuccessFul" else "Payment Failed"
    val paymentDescription =
        if(paymentSuccess!!)
            "We have sent a copy of your ticket to your e-mail address. " +
            "You can check your ticket in the My Tickets section on the homepage."
        else "Your ticket purchase could not be processed because there was a problem with the payment process. " +
             "Try to buy a ticket again by pressing the try again button."

    Image(
        painter = painterResource(background),
        contentDescription = "Payment Background",
        modifier = Modifier
            .fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Column (
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            Modifier.padding(start = 16.dp, end = 16.dp, top = 36.dp)
        ) {
            StepperIndicator(navController, TypeStep.PAYMENT_S_F.value)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "Icon Payment Status",
            tint = AppTheme.colors.whiteColor
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = paymentStatus,
            color = AppTheme.colors.whiteColor,
            style = AppTheme.typography.titleMedium
        )

        Spacer(Modifier.height(14.dp))

        Text(
            text = paymentDescription,
            color = AppTheme.colors.whiteColor,
            style = AppTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(280.dp)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column (
            Modifier.padding(start = 16.dp, end = 16.dp, bottom = 36.dp)
        ) {
            CustomButton(
                onClick = {
                    if(paymentSuccess!!)
                        navController.navigate("my-ticket")
                    else
                        navController.navigateUp()
                },
                type = ButtonType.FILLED,
                text = if(paymentSuccess!!) "View Ticket" else "Try Again",
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(Modifier.height(10.dp))

            CustomButton(
                onClick = { navController.navigate("home") },
                type = ButtonType.FILLED,
                text = "Back to Home",
                modifier = Modifier
                    .fillMaxWidth(),
                backgroundColor = AppTheme.colors.deepBlack.copy(0.6f)
            )
        }
    }
}