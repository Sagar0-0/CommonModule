package fit.asta.health.feature.spotify.components

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * This function draws a Filter/ Sorting Options List in the Screen
 *
 * @param modifier To be passed from the parent
 * @param filterList This contains a list of Filters for the User to choose from
 * @param onFilterOptionClicked
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MusicFilterOptions(
    modifier: Modifier = Modifier,
    filterList: MutableMap<String, Boolean>,
    onFilterOptionClicked: (Boolean, String) -> Unit
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {

        // This Row continues to put the UI elements to next line if the screen width is reached
        FlowRow(modifier = Modifier.wrapContentWidth()) {

            filterList.forEach { currentOption ->

                // Checkbox Layout with texts
                Row(verticalAlignment = Alignment.CenterVertically) {

                    // Check Box
                    Checkbox(
                        checked = currentOption.value,
                        onCheckedChange = {
                            onFilterOptionClicked(it, currentOption.key)
                        }
                    )

                    // Filter Name
                    Text(
                        text = currentOption.key,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}