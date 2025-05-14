package cuong.dev.dotymovie.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cuong.dev.dotymovie.R
import cuong.dev.dotymovie.ui.theme.AppTheme

sealed class TypeLogo(val type: String) {
    object Large: TypeLogo("large")
    object Small: TypeLogo("small")
}

@Composable
fun DotyLogoMovie(
    type: TypeLogo,
    modifier: Modifier = Modifier
) {
    val textStyle = if (type is TypeLogo.Large) AppTheme.typography.titleLarge else AppTheme.typography.titleSmall
    val iconSize = if (type is TypeLogo.Large) 24.dp else 16.dp
    val dividerWidth = if (type is TypeLogo.Large) 120.dp else 72.dp
    val spacing = if (type is TypeLogo.Large) 6.dp else 4.dp
    val whiteColor = AppTheme.colors.whiteColor

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(
            color = AppTheme.colors.whiteColor,
            thickness = spacing - 3.dp,
            modifier = Modifier
                .width(dividerWidth)
        )

        Spacer(modifier = Modifier.height(spacing))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.movie),
                contentDescription = "movie icon",
                tint = AppTheme.colors.primary,
                modifier = Modifier.size(iconSize)
            )

            Spacer(modifier = Modifier.width(spacing- 2.dp))

            Text(
                text = "DOTY",
                color = AppTheme.colors.whiteColor,
                style = textStyle,
                fontWeight = FontWeight.W600
            )
        }

        Text(
            text = "MOVIE",
            color = AppTheme.colors.whiteColor,
            style = textStyle,
            fontWeight = FontWeight.W600
        )

        Spacer(modifier = Modifier.height(spacing))

        HorizontalDivider(
            color = AppTheme.colors.whiteColor,
            thickness = spacing - 3.dp,
            modifier = Modifier
                .width(dividerWidth)
        )
    }
}