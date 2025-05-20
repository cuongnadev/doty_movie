package cuong.dev.dotymovie.ui.screen.auth

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cuong.dev.dotymovie.R
import cuong.dev.dotymovie.ui.component.CustomButton
import cuong.dev.dotymovie.ui.component.CustomTextField
import cuong.dev.dotymovie.ui.screen.layout.HomeLayout
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
    var name by remember { mutableStateOf(userViewModel.name.value) }
    var email by remember { mutableStateOf(userViewModel.email.value) }
    var showPasswordDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val token = authViewModel.getToken()
        val payload = token?.let { decodeJWT(it) }
        val userId = payload?.optString("sub")?.toIntOrNull()

        if (userId == null) {
            Log.e("JWT", "Token invalid or missing user ID")
            return@LaunchedEffect
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
                value = name,
                onValueChange = { name = it },
                label = "Username",
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "E-mail",
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomButton(
                onClick = {
                    authViewModel.name.value = name
                    authViewModel.email.value = email
                    // TODO: Gọi API để lưu thông tin
                },
                text = "Save"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                onClick = { showPasswordDialog = true },
                text = "Change password"
            )

            if (showPasswordDialog) {
                ChangePasswordDialog(onDismiss = { showPasswordDialog = false })
            }
        }
    }
}

@Composable
private fun ChangePasswordDialog(onDismiss: () -> Unit) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            CustomButton(
                onClick = {
                    // TODO: xử lý đổi mật khẩu
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
        title = { Text("Change password") },
        text = {
            Column {
                CustomTextField(
                    value = oldPassword,
                    onValueChange = { oldPassword = it },
                    label = "Old-Password",
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = "New-Password",
                )
            }
        },
    )
}