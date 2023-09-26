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
import fit.asta.health.designsystemx.AstaThemeX
import fit.asta.health.designsystemx.molecular.cards.AstaCard
import fit.asta.health.designsystemx.molecular.cards.AstaElevatedCard
import fit.asta.health.designsystemx.molecular.cards.AstaOutlinedCard
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
    AstaThemeX {
        Surface {
            AstaCardScreen()
        }
    }
}

@Composable
fun AstaCardScreen() {
    AstaThemeX {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AstaThemeX.appSpacing.medium)
            ) {

                // Filled Card Section
                AstaCard(
                    enabled = true,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    LabelTexts.Large(text = "Filled Enabled Card")
                }

                AstaCard(
                    enabled = false,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    LabelTexts.Large(text = "Filled Disabled Card")
                }


                // Elevated Cards
                AstaElevatedCard(
                    enabled = true,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    LabelTexts.Large(text = "Elevated Enabled Card")
                }

                AstaElevatedCard(
                    enabled = false,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    LabelTexts.Large(text = "Elevated Disabled Card")
                }


                // outlined Cards
                AstaOutlinedCard(
                    enabled = true,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    LabelTexts.Large(text = "Outlined Enabled Card")
                }

                AstaOutlinedCard(
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