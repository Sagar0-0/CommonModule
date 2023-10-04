package fit.asta.health.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.texts.TitleTexts

// Preview Function
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview() {
    AppTheme {
        AppTopTabBar(
            tabList = listOf("DAY", "WEEK", "MONTH", "YEAR"),
            selectedItem = 0
        ) { }
    }
}

/**
 * This function draws Tab Options in the screen when called.
 *
 * @param modifier This is the modifications passed down by the parent Function
 * @param tabList This contains the List of the String which should be displayed at the Screen
 * @param selectedItem This is the current Selected Item
 * @param strokeWidth This is the width of the stroke which will be displayed under selected Item
 * @param selectedColor This is the color of the text and the underline when Selected
 * @param unselectedColor This is the color of the text and the underline when Unselected
 * @param onNewTabClicked This changes the Tab
 */
@Composable
fun AppTopTabBar(
    modifier: Modifier = Modifier,
    tabList: List<String>,
    selectedItem: Int,
    strokeWidth: Float = 10f,
    selectedColor: Color = AppTheme.colors.primary,
    unselectedColor: Color = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level3),
    onNewTabClicked: (Int) -> Unit
) {

    // Card Layout Which is elevated
    // TODO :- Removing the Elevated card and using AppElevatedCard is changing shape
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = AppTheme.elevation.small),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.Transparent),
        shape = RectangleShape
    ) {

        // Contains all the Text Options
        Row(
            modifier = modifier
                .background(AppTheme.colors.surface)
                .fillMaxWidth()
        ) {

            // Taking Each Item of the Tab List Items and making the Tab layout
            tabList.forEachIndexed { index: Int, option: String ->

                // Is Clickable and contains the underline when selected
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .size(AppTheme.boxSize.smallMedium)
                        .clickable {

                            // Changing the selected Item to the Item Index Clicked to move the State
                            onNewTabClicked(index)
                        }
                        .drawBehind {

                            // Checking if the Option is selected
                            if (index == selectedItem)
                                drawLine(
                                    color = selectedColor,
                                    start = Offset(0f, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = strokeWidth
                                )
                        },
                    contentAlignment = Alignment.Center
                ) {

                    // Text of the Option to be showed
                    TitleTexts.Level4(
                        text = option,
                        color = if (selectedItem == index) selectedColor else unselectedColor
                    )
                }
            }
        }
    }
}