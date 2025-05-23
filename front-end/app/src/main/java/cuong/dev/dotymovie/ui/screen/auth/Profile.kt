package cuong.dev.dotymovie.ui.screen.auth

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cuong.dev.dotymovie.R
import cuong.dev.dotymovie.ui.component.CustomButton
import cuong.dev.dotymovie.ui.component.CustomTextField
import cuong.dev.dotymovie.ui.screen.layout.HomeLayout
import cuong.dev.dotymovie.ui.theme.AppTheme
import cuong.dev.dotymovie.utils.decodeJWT
import cuong.dev.dotymovie.viewmodel.AuthViewModel
import cuong.dev.dotymovie.viewmodel.NavigationViewModel
import cuong.dev.dotymovie.viewmodel.UserViewModel

@Composable
fun Profile(
    navController: NavController,
    viewModel: NavigationViewModel,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel
) {
    var showPasswordDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val successMessage by userViewModel.successMessage
    val errorMessage by userViewModel.errorMessage

    val userId = remember {
        val token = authViewModel.getToken()
        val payload = token?.let { decodeJWT(it) }
        payload?.optString("sub")?.toIntOrNull()
    }

    LaunchedEffect(Unit) {
        if (userId == null) {
            Log.e("JWT", "Token invalid or missing user ID")
            return@LaunchedEffect
        }

        userViewModel.loadUser(userId)
    }

    LaunchedEffect(successMessage, errorMessage) {
        successMessage?.let {
            snackbarHostState.showSnackbar(it)
            userViewModel.clearMessages()
        }
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            userViewModel.clearMessages()
        }
    }

    HomeLayout(navController, viewModel, authViewModel) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomTextField(
                value = userViewModel.name.value,
                onValueChange = { userViewModel.onNameChange(it) },
                label = "Username",
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                value = userViewModel.email.value,
                onValueChange = { userViewModel.onEmailChange(it) },
                label = "E-mail",
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomButton(
                onClick = {
                    userId?.let {
                        userViewModel.updateUser(it)
                    }
                },
                text = "Save"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                onClick = { showPasswordDialog = true },
                text = "Change password"
            )

            Spacer(modifier = Modifier.height(16.dp))

            SnackbarHost(hostState = snackbarHostState)

            if (showPasswordDialog) {
                userViewModel.onOldPasswordChange("")
                userViewModel.onNewPasswordChange("")
                userId?.let {
                    ChangePasswordDialog(userViewModel, onDismiss = { showPasswordDialog = false }, it)
                }
            }
        }
    }
}

@Composable
private fun ChangePasswordDialog(userViewModel: UserViewModel, onDismiss: () -> Unit, userId: Int) {
    val passwordVisible = remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            CustomButton(
                onClick = {
                    userViewModel.changePassword(userId)
                    onDismiss()
                },
                text = "Confirm"
            )
        },
        dismissButton = {
            CustomButton(
                onClick = onDismiss,
                text = "Cancel"
            )
        },
        title = { Text("Change password", color = AppTheme.colors.whiteColor) },
        text = {
            Column {
                CustomTextField(
                    value = userViewModel.oldPassword.value,
                    onValueChange = { userViewModel.onOldPasswordChange(it) },
                    label = "Old-Password",
                    isPassword = true,
                    isPasswordVisible = passwordVisible.value,
                    onVisibilityToggle = { passwordVisible.value = !passwordVisible.value }
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomTextField(
                    value = userViewModel.newPassword.value,
                    onValueChange = { userViewModel.onNewPasswordChange(it) },
                    label = "New-Password",
                    isPassword = true,
                    isPasswordVisible = passwordVisible.value,
                    onVisibilityToggle = { passwordVisible.value = !passwordVisible.value }
                )
            }
        },
        modifier = Modifier.border(1.dp, AppTheme.colors.primary, shape = RoundedCornerShape(16.dp)),
        containerColor = AppTheme.colors.deepBlack
    )
}