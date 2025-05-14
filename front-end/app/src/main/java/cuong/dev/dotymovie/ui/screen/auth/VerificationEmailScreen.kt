package cuong.dev.dotymovie.ui.screen.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cuong.dev.dotymovie.ui.theme.AppTheme
import cuong.dev.dotymovie.ui.component.ButtonType
import cuong.dev.dotymovie.ui.component.CustomButton
import cuong.dev.dotymovie.ui.component.CustomTextField
import cuong.dev.dotymovie.ui.screen.layout.AuthLayout
import cuong.dev.dotymovie.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun VerificationEmailScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        authViewModel.authState.value = authViewModel.authState.value.copy(
            isSuccess = false,
            errorMessage = null
        )
    }

    AuthLayout {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                "We have sent a verification code to the e-mail address you entered, by entering the code you can set your create your account.",
                color = AppTheme.colors.whiteColor,
                style = AppTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            CustomTextField(
                value = authViewModel.code.value,
                onValueChange = {
                    authViewModel.code.value = it
                    authViewModel.authState.value = authViewModel.authState.value.copy(
                        errorMessage = null
                    )
                },
                label = "Verification Code"
            )

            if(authViewModel.authState.value.errorMessage != null) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = authViewModel.authState.value.errorMessage.toString(),
                    color = AppTheme.colors.redColor,
                    style = AppTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.W500,
                    ),
                )

                Spacer(modifier = Modifier.height(10.dp))
            } else {
                Spacer(modifier = Modifier.height(32.dp))
            }

            CustomButton(
                text = "Verify",
                onClick = {
                    authViewModel.authState.value = authViewModel.authState.value.copy(
                        isLoading = true,
                        errorMessage = null
                    )
                    scope.launch {
                        authViewModel.verify(authViewModel.code.value)

                        if(authViewModel.authState.value.isSuccess) {
                            navController.navigate("sign-in")
                        }
                    }
                },
                type = ButtonType.FILLED,
                isTextCentered = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    "Do you already have an account?",
                    color = AppTheme.colors.whiteColor,
                    style = AppTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.W600,
                    ),
                )

                Text(
                    "Sign In",
                    color = AppTheme.colors.primary,
                    style = AppTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.W600,
                        shadow = Shadow(
                            color = AppTheme.colors.primary,
                            offset = Offset(0f, 0f),
                            blurRadius = 10f
                        )
                    ),
                    modifier = Modifier
                        .clickable {navController.navigate("sign-in") }
                        .pointerHoverIcon(PointerIcon.Hand)
                )

                Text(
                    "Now!",
                    color = AppTheme.colors.whiteColor,
                    style = AppTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.W600,
                    ),
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}