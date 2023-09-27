package fit.asta.health.designsystemx.molecular.previews

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystemx.AppTheme
import fit.asta.health.designsystemx.molecular.chip.AppAssistChip
import fit.asta.health.designsystemx.molecular.chip.AppFilterChip
import fit.asta.health.designsystemx.molecular.texts.TitleTexts


// Preview Function
@Preview(
    "Light Button",
    heightDp = 1100
)
@Preview(
    name = "Dark Button",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    heightDp = 1100
)
@Composable
private fun DefaultPreview1() {
    AppTheme {
        Surface {
            AppChipScreen()
        }
    }
}

@Composable
fun AppChipScreen() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AppTheme.appSpacing.medium)
            ) {

                // Assist Chip Section
                TitleTexts.Level1(text = "Assist Chips")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.appSpacing.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    AppAssistChip(
                        onClick = {},
                        textToShow = "Enabled",
                        leadingIcon = Icons.Default.Person,
                        trailingIcon = Icons.Default.TrackChanges
                    )

                    AppAssistChip(
                        enabled = false,
                        onClick = {},
                        textToShow = "Disabled",
                        leadingIcon = Icons.Default.Person,
                        trailingIcon = Icons.Default.TrackChanges
                    )
                }


                // Filter Chip Section
                TitleTexts.Level1(text = "Filter Chips")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.appSpacing.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppFilterChip(
                        onClick = {},
                        textToShow = "Enabled",
                        leadingIcon = Icons.Default.Person,
                        trailingIcon = Icons.Default.TrackChanges,
                        selected = true
                    )

                    AppFilterChip(
                        enabled = false,
                        onClick = {},
                        textToShow = "Disabled",
                        leadingIcon = Icons.Default.Person,
                        trailingIcon = Icons.Default.TrackChanges,
                        selected = false
                    )
                }
            }
        }
    }
}