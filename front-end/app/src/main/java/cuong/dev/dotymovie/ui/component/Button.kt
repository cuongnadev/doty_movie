package cuong.dev.dotymovie.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import cuong.dev.dotymovie.ui.theme.AppTheme

enum class ButtonType { FILLED, OUTLINED, ICON }

@Composable
fun CustomButton(
    text: String = "",
    textColor: Color = AppTheme.colors.whiteColor,
    backgroundColor: Color = AppTheme.colors.primary,
    fontSize: TextUnit? = null,
    onClick: () -> Unit,
    type: ButtonType = ButtonType.FILLED,
    icon: ImageVector? = null,
    iconPainter: Painter? = null,
    iconSize: Dp? = 24.dp,
    isTextCentered: Boolean = true,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues? = ButtonDefaults.ContentPadding,
    disable: Boolean ?= false
) {
    when (type) {
        ButtonType.FILLED -> {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = backgroundColor,
                    disabledContainerColor = AppTheme.colors.primary.copy(alpha = 0.4f),
                    contentColor = textColor,
                    disabledContentColor = textColor.copy(alpha = 0.4f)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = modifier.fillMaxWidth(),
                contentPadding = contentPadding?: ButtonDefaults.ContentPadding,
                enabled = !(disable ?: false),
            ) {
                ButtonContent(text, textColor, fontSize, icon, iconPainter, iconSize, isTextCentered)
            }
        }

        ButtonType.OUTLINED -> {
            OutlinedButton(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    contentColor = textColor,
                    disabledContentColor = textColor.copy(alpha = 0.4f)
                ),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.5.dp, AppTheme.colors.primary),
                modifier = modifier.fillMaxWidth(),
                contentPadding = contentPadding?: ButtonDefaults.ContentPadding,
                enabled = !(disable ?: false)
            ) {
                ButtonContent(text, textColor, fontSize, icon, iconPainter, iconSize, isTextCentered)
            }
        }

        ButtonType.ICON -> {
            IconButton(
                onClick = onClick,
                modifier = modifier,
                enabled = !(disable ?: false)
            ) {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    icon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = text,
                            tint = if (!disable!!) textColor else textColor.copy(0.4f),
                            modifier = Modifier.size(iconSize ?: 24.dp)
                        )
                    }

                    iconPainter?.let {
                        Icon(
                            painter = it,
                            contentDescription = text,
                            tint = if (!disable!!) textColor else textColor.copy(0.4f),
                            modifier = Modifier.size(iconSize ?: 24.dp)
                        )
                    }
                }
            }
        }

    }
}

@Composable
private fun ButtonContent(
    text: String,
    textColor: Color,
    fontSize: TextUnit?,
    icon: ImageVector?,
    iconPainter: Painter?,
    iconSize: Dp?,
    isTextCentered: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (!isTextCentered) {
            Text(
                text,
                color = textColor,
                style = AppTheme.typography.titleSmall.copy(
                    fontSize = fontSize ?: AppTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.W500,
                    shadow = Shadow(
                        color = textColor.copy(alpha = 0.8f),
                        offset = Offset(0f, 0f),
                        blurRadius = 8f
                    )
                ),
                modifier = Modifier.weight(1f).padding(8.dp)
            )
        } else {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(
                    text,
                    color = textColor,
                    style = AppTheme.typography.titleSmall.copy(
                        fontSize = fontSize ?: AppTheme.typography.titleSmall.fontSize,
                        fontWeight = FontWeight.W500,
                        shadow = Shadow(
                            color = textColor.copy(alpha = 0.8f),
                            offset = Offset(0f, 0f),
                            blurRadius = 8f
                        )
                    ),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                Modifier.size(iconSize ?: 24.dp)
            )
        }

        iconPainter?.let {
            Icon(
                painter = it,
                contentDescription = null,
                Modifier.size(iconSize ?: 24.dp)
            )
        }
    }
}

