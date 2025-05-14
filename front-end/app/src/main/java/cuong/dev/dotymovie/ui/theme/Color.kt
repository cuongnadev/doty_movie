package cuong.dev.dotymovie.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Base Color
val PrimaryColor = Color(0xFF6C47DB)
val RedColor = Color(0xFFFA5353)
val YellowColor = Color(0xFFF5C618)
val WhiteColor = Color(0xFFFFFFFF)
val DeepBlack  = Color(0xFF0C0C0C)
val DarkGrayBlack = Color(0xFF0E0E0E)
val TransparentBlack  = Color(0xCA000000)
val OrangeColor = Color(0xFFE49600)
val GreenColor = Color(0xFF33B528)
val SemiTransparentWhite = Color(0x87FFFFFF)

// Gradient Color
val PurpleToTransparentGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF432E82),
        Color(0x00150E27)
    ),
    startY = 0f,
    endY = Float.POSITIVE_INFINITY
)

val RedToOrangeGradient = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFFED0006),
        Color(0xFFF9A000),
        Color.Black.copy(alpha = 0.4f)
    )
)

val DarkBlueToLightBlueGradient = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFF253B80),
        Color(0xFF179BD7),
        Color.Black.copy(alpha = 0.4f)
    )
)

val GoogleColorsGradient = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFFEA4335),
        Color(0xFFFBBC04),
        Color(0xFF34A853),
        Color(0xFF4285F4),
        Color.Black.copy(alpha = 0.4f)
    )
)

val WhiteToTransparentGradient = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFFFFFFFF),
        Color(0x00000000)
    ),
    startX = 0f,
    endX = Float.POSITIVE_INFINITY
)

val FadeToBlackGradient = Brush.verticalGradient(
    colors = listOf(
        Color.Transparent,
        Color.Transparent,
        Color.Transparent,
        Color.Black.copy(0.8f)
    ),
    startY = 100f,
    endY = Float.POSITIVE_INFINITY
)

val TransparentToDarkGrayGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0x00000000),
        Color(0x00000000),
        Color(0xFF0E0E0E)
    ),
    startY = 0f,
    endY = Float.POSITIVE_INFINITY
)


val TransparentWhite27 = Color(255, 255, 255, 69)
val TransparentGray40 = Color(77, 77, 77, 102)

@Immutable
data class AppColors(
    val primary: Color,
    val redColor: Color,
    val yellowColor: Color,
    val whiteColor: Color,
    val deepBlack : Color,
    val darkGrayBlack: Color,
    val transparentBlack : Color,
    val orangeColor: Color,
    val greenColor: Color,
    val semiTransparentWhite: Color,
    val transparentWhite27: Color,
    val transparentGray40: Color,
    val purpleToTransparentGradient: Brush,
    val redToOrangeGradient: Brush,
    val darkBlueToLightBlueGradient: Brush,
    val googleColorsGradient: Brush,
    val whiteToTransparentGradient: Brush,
    val fadeToBlackGradient: Brush,
    val transparentToDarkGrayGradient: Brush,
);

val LocalAppColors = staticCompositionLocalOf {
    AppColors(
        primary = Color.Unspecified,
        redColor = Color.Unspecified,
        yellowColor = Color.Unspecified,
        whiteColor = Color.Unspecified,
        deepBlack  = Color.Unspecified,
        darkGrayBlack = Color.Unspecified,
        transparentBlack  = Color.Unspecified,
        orangeColor = Color.Unspecified,
        greenColor = Color.Unspecified,
        semiTransparentWhite = Color.Unspecified,
        transparentWhite27 = Color.Unspecified,
        transparentGray40 = Color.Unspecified,
        purpleToTransparentGradient = Brush.horizontalGradient(emptyList()),
        redToOrangeGradient = Brush.horizontalGradient(emptyList()),
        darkBlueToLightBlueGradient = Brush.horizontalGradient(emptyList()),
        googleColorsGradient = Brush.horizontalGradient(emptyList()),
        whiteToTransparentGradient = Brush.horizontalGradient(emptyList()),
        fadeToBlackGradient = Brush.horizontalGradient(emptyList()),
        transparentToDarkGrayGradient = Brush.horizontalGradient(emptyList()),
    )
}

val extendedColors = AppColors(
    primary = PrimaryColor,
    redColor = RedColor,
    yellowColor = YellowColor,
    whiteColor = WhiteColor,
    deepBlack  = DeepBlack,
    darkGrayBlack = DarkGrayBlack,
    transparentBlack  = TransparentBlack,
    orangeColor = OrangeColor,
    greenColor = GreenColor,
    semiTransparentWhite = SemiTransparentWhite,
    transparentWhite27 = TransparentWhite27,
    transparentGray40 = TransparentGray40,
    purpleToTransparentGradient = PurpleToTransparentGradient,
    redToOrangeGradient = RedToOrangeGradient,
    darkBlueToLightBlueGradient = DarkBlueToLightBlueGradient,
    googleColorsGradient = GoogleColorsGradient,
    whiteToTransparentGradient = WhiteToTransparentGradient,
    fadeToBlackGradient = FadeToBlackGradient,
    transparentToDarkGrayGradient = TransparentToDarkGrayGradient,
)
