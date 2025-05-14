package cuong.dev.dotymovie.ui.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cuong.dev.dotymovie.R
import cuong.dev.dotymovie.constants.numOfStep
import cuong.dev.dotymovie.ui.theme.AppTheme

@Composable
fun StepperIndicator(
    navController: NavController,
    currentStep: Int
) {
    val context = LocalContext.current

    Box(
        Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopStart
        ) {
            CustomButton(
                onClick = { navController.navigateUp() },
                type = ButtonType.ICON,
                iconPainter = painterResource(id = R.drawable.arrow_back),
                textColor = AppTheme.colors.primary,
                modifier = Modifier
                    .background(AppTheme.colors.whiteColor, shape = RoundedCornerShape(10.dp))
                    .width(40.dp)
                    .height(40.dp),
                iconSize = 20.dp
            )
        }

        Box(
            Modifier.fillMaxWidth()
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(numOfStep) { index ->
                    val step = index + 1
                    val isCurrentStep = step == currentStep
                    val isPrevStep = step < currentStep

                    val buttonType =
                        if (isCurrentStep)
                            ButtonType.FILLED
                        else if (isPrevStep)
                            ButtonType.ICON
                        else
                            ButtonType.OUTLINED

                    val textColor =
                        if (isCurrentStep || isPrevStep)
                            AppTheme.colors.whiteColor
                        else
                            AppTheme.colors.primary

                    val handleClick = {
                        if(isCurrentStep) {
                            Toast.makeText(context, "You are on step $currentStep", Toast.LENGTH_SHORT).show()
                        } else if (isPrevStep) {
                            Toast.makeText(context, "You have completed step $step", Toast.LENGTH_SHORT).show()
                        } else {
                            val prevStep = (currentStep until step).joinToString(", ") { "step $it" }
                            Toast.makeText(context,"Please, complete $prevStep before moving to step $step", Toast.LENGTH_SHORT).show()
                        }
                    }

                    CustomButton(
                        onClick = handleClick,
                        type = buttonType,
                        isTextCentered = true,
                        text = if(isPrevStep) "" else "$step",
                        iconPainter = if (isPrevStep) painterResource(R.drawable.check) else null,
                        iconSize = 12.dp,
                        textColor = textColor,
                        modifier = Modifier
                            .size(34.dp)
                            .clip(shape = RoundedCornerShape(40.dp))
                            .then(if (!isCurrentStep)
                                    if(isPrevStep)
                                        Modifier.background(AppTheme.colors.primary).size(34.dp)
                                    else Modifier.border(
                                        1.5.dp,
                                        AppTheme.colors.primary,
                                        shape = RoundedCornerShape(40.dp)
                                    )
                                else Modifier
                            ),
                        contentPadding = PaddingValues(0.dp),
                        fontSize = 14.sp
                    )

                    if (index < 3) {
                        HorizontalDivider(
                            modifier = Modifier.width(10.dp),
                            thickness = 2.dp,
                            color = AppTheme.colors.primary
                        )
                    }
                }
            }
        }
    }
}