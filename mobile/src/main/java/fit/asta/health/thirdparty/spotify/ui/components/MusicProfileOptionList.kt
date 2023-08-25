package fit.asta.health.thirdparty.spotify.ui.components

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * This function creates the option list of what they want to get fetched eg. : Playlist , Tracks ,
 * Albums etc
 *
 * @param selectedItem This contains the selected Item which the user has choose
 * @param categoryList This contains the list of options from which the user can choose
 * @param onSelectionChange This function is used to send the chosen value to the parent function
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MusicProfileOptionList(
    selectedItem: Int,
    categoryList: List<String> = listOf("Track", "Playlist", "Artist", "Album", "Show", "Episode"),
    onSelectionChange: (Int) -> Unit
) {

    FlowRow(
        modifier = Modifier
            .padding(12.dp)
            .wrapContentWidth()
    ) {

        categoryList.forEachIndexed { index, option ->

            OutlinedButton(
                onClick = {
                    onSelectionChange(index)
                },
                modifier = Modifier
                    .padding(4.dp),

                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = if (index == selectedItem)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.secondary
                )
            ) {

                // Category Name
                Text(
                    text = option,
                    fontSize = 12.sp
                )
            }
        }
    }
}