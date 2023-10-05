package fit.asta.health.designsystem.molecular.previews

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.cards.AppElevatedCard
import fit.asta.health.designsystem.molecular.cards.AppOutlinedCard
import fit.asta.health.designsystem.molecular.texts.CaptionTexts


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
            AppCardScreen()
        }
    }
}

@Composable
fun AppCardScreen() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(AppTheme.spacing.medium)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium)
            ) {

                // Filled Card Section
                AppCard(
                    enabled = true,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppTheme.boxSize.large)
                ) {
                    CaptionTexts.Level1(text = "Filled Enabled Card")
                }

                AppCard(
                    enabled = false,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppTheme.boxSize.large)
                ) {
                    CaptionTexts.Level1(text = "Filled Disabled Card")
                }


                // Elevated Cards
                AppElevatedCard(
                    enabled = true,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppTheme.boxSize.large)
                ) {
                    CaptionTexts.Level1(text = "Elevated Enabled Card")
                }

                AppElevatedCard(
                    enabled = false,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppTheme.boxSize.large)
                ) {
                    CaptionTexts.Level1(text = "Elevated Disabled Card")
                }


                // outlined Cards
                AppOutlinedCard(
                    enabled = true,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppTheme.boxSize.large)
                ) {
                    CaptionTexts.Level1(text = "Outlined Enabled Card")
                }

                AppOutlinedCard(
                    enabled = false,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppTheme.boxSize.large)
                ) {
                    CaptionTexts.Level1(text = "Outlined Disabled Card")
                }
            }
        }
    }
}