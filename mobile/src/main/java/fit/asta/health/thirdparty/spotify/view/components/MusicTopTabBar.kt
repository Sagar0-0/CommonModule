package fit.asta.health.thirdparty.spotify.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
 * @param body This is the UI which will be shown on the Rest of the Screen
 */
@Composable
fun MusicTopTabBar(
    modifier: Modifier = Modifier,
    tabList: List<String>,
    selectedItem: Int,
    strokeWidth: Float = 10f,
    selectedColor: Color,
    unselectedColor: Color,
    onNewTabClicked: (Int) -> Unit,
    body: @Composable () -> Unit
) {

    // Card Layout Which is elevated
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        ElevatedCard(
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 8.dp
            ),
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color.Transparent
            ),
            shape = RectangleShape
        ) {

            // Contains all the Text Options
            Row(
                modifier = modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxWidth()
            ) {

                // Taking Each Item of the Tab List Items and making the Tab layout
                tabList.forEachIndexed { index: Int, option: String ->

                    // Is Clickable and contains the underline when selected
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .size(54.dp)
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
                        Text(
                            text = option,

                            // Text and Font Properties
                            color = if (selectedItem == index) selectedColor else unselectedColor,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.W800,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

        body()
    }
}