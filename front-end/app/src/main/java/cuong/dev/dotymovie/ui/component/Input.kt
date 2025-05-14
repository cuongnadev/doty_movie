package cuong.dev.dotymovie.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cuong.dev.dotymovie.R
import cuong.dev.dotymovie.ui.theme.AppTheme

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    isPasswordVisible: Boolean = false,
    onVisibilityToggle: (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                label,
                color = AppTheme.colors.semiTransparentWhite,
                style = AppTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.W600
                ),
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(2.dp)
            )
        },
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = AppTheme.colors.semiTransparentWhite,
            unfocusedTextColor = AppTheme.colors.semiTransparentWhite,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = AppTheme.colors.transparentGray40,
            disabledContainerColor = AppTheme.colors.transparentGray40,
            cursorColor = Color.White,
            focusedLabelColor = AppTheme.colors.transparentWhite27,
            unfocusedLabelColor = AppTheme.colors.transparentWhite27,
            focusedIndicatorColor = AppTheme.colors.transparentGray40,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (isPassword && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done) else KeyboardOptions.Default,
        trailingIcon = {
            if (isPassword && onVisibilityToggle != null) {
                IconButton(onClick = onVisibilityToggle) {
                    Icon(
                        painter = if (isPasswordVisible) painterResource(R.drawable.icon_eye_off) else painterResource(R.drawable.icon_eye),
                        contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                        tint = AppTheme.colors.semiTransparentWhite
                    )
                }
            }
        }
    )
}