package cuong.dev.dotymovie.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import cuong.dev.dotymovie.R
import cuong.dev.dotymovie.ui.theme.AppTheme

@Composable
fun <T> CustomDropdown(
    modifier: Modifier = Modifier,
    label: String,
    items: List<T>,
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    itemToText: (T) -> String = { it.toString() },
    disable: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        CustomButton(
            onClick = { if(!disable) expanded = !expanded },
            type = if(selectedItem != null) ButtonType.FILLED else ButtonType.OUTLINED,
            isTextCentered = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            text = selectedItem?.let { itemToText(selectedItem) } ?: label,
            iconPainter = if(expanded) painterResource(R.drawable.arrow_down) else painterResource(R.drawable.arrow),
            iconSize = 16.dp,
            textColor = if(selectedItem != null) AppTheme.colors.whiteColor else AppTheme.colors.primary
        )

        if (expanded && !disable) {
            Popup(
                alignment = Alignment.TopStart,
                offset = IntOffset(0, 180),
                onDismissRequest = { expanded = false },
                properties = PopupProperties(focusable = true)
            ) {
                Column(
                    modifier = Modifier
                        .width(LocalConfiguration.current.screenWidthDp.dp - 36.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(AppTheme.colors.deepBlack)
                        .border(1.5.dp, AppTheme.colors.primary, RoundedCornerShape(14.dp))
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .heightIn(max = 200.dp),
                    ) {
                        if(items.isEmpty()) {
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            expanded = false
                                        }
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "No items available",
                                        color = AppTheme.colors.primary,
                                    )
                                }
                            }
                        } else {
                            items(items) { item ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            onItemSelected(item)
                                            expanded = false
                                        }
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = itemToText(item),
                                        color = AppTheme.colors.primary,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
