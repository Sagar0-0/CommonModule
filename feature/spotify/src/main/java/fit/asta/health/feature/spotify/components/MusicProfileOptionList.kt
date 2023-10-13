package fit.asta.health.feature.spotify.components

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.chip.AppFilterChip

/**
 * This function creates the option list of what they want to get fetched eg. : Playlist , Tracks ,
 * Albums etc
 *
 * @param selectedItem This contains the selected Item which the user has choose
 * @param categoryList This contains the list of options from which the user can choose
 * @param onSelectionChange This function is used to send the chosen value to the parent function
 */
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MusicProfileOptionList(
    selectedItem: Int,
    categoryList: List<String> = listOf(
        "Liked",
        "Track",
        "Playlist",
        "Artist",
        "Album",
        "Show",
        "Episode"
    ),
    onSelectionChange: (Int) -> Unit
) {

    FlowRow(
        modifier = Modifier
            .padding(AppTheme.spacing.level2)
            .wrapContentWidth()
    ) {

        categoryList.forEachIndexed { index, option ->
            AppFilterChip(
                selected = (index == selectedItem),
                textToShow = option,
                modifier = Modifier.padding(
                    horizontal = AppTheme.spacing.level1,
                    vertical = AppTheme.spacing.level0
                )
            ) { onSelectionChange(index) }
        }
    }
}