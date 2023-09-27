package fit.asta.health.designsystemx.molecular.previews

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
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystemx.AppTheme
import fit.asta.health.designsystemx.molecular.cards.AppCard
import fit.asta.health.designsystemx.molecular.cards.AppElevatedCard
import fit.asta.health.designsystemx.molecular.cards.AppOutlinedCard
import fit.asta.health.designsystemx.molecular.texts.LabelTexts


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
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AppTheme.appSpacing.medium)
            ) {

                // Filled Card Section
                AppCard(
                    enabled = true,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    LabelTexts.Large(text = "Filled Enabled Card")
                }

                AppCard(
                    enabled = false,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    LabelTexts.Large(text = "Filled Disabled Card")
                }


                // Elevated Cards
                AppElevatedCard(
                    enabled = true,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    LabelTexts.Large(text = "Elevated Enabled Card")
                }

                AppElevatedCard(
                    enabled = false,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    LabelTexts.Large(text = "Elevated Disabled Card")
                }


                // outlined Cards
                AppOutlinedCard(
                    enabled = true,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    LabelTexts.Large(text = "Outlined Enabled Card")
                }

                AppOutlinedCard(
                    enabled = false,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    LabelTexts.Large(text = "Outlined Disabled Card")
                }
            }
        }
    }
}