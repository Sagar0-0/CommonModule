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
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystemx.AstaThemeX
import fit.asta.health.designsystemx.molecular.button.AstaElevatedButton
import fit.asta.health.designsystemx.molecular.button.AstaFilledButton
import fit.asta.health.designsystemx.molecular.button.AstaFloatingActionButton
import fit.asta.health.designsystemx.molecular.button.AstaIconButton
import fit.asta.health.designsystemx.molecular.button.AstaOutlinedButton
import fit.asta.health.designsystemx.molecular.button.AstaRadioButton
import fit.asta.health.designsystemx.molecular.button.AstaTextButton
import fit.asta.health.designsystemx.molecular.button.AstaToggleButton
import fit.asta.health.designsystemx.molecular.button.AstaTonalButton
import fit.asta.health.designsystemx.molecular.texts.TitleTexts

// Preview Function
@Preview(
    "Light Button",
    heightDp = 1000
)
@Preview(
    name = "Dark Button",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    heightDp = 1000
)
@Composable
private fun DefaultPreview1() {
    AstaThemeX {
        Surface {
            AstaButtonScreen()
        }
    }
}


@Composable
fun AstaButtonScreen() {
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
                verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium)
            ) {

                // Filled Button Section
                TitleTexts.Large(text = "Filled Buttons")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Filled button With Texts Passed down to it
                    AstaFilledButton(
                        onClick = { /*TODO*/ },
                        textToShow = "Enabled",
                        leadingIcon = Icons.Default.Person
                    )
                    AstaFilledButton(
                        onClick = { /*TODO*/ },
                        enabled = false,
                        textToShow = "Disabled"
                    )
                }

                TitleTexts.Large(text = "Tonal Buttons")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AstaTonalButton(
                        onClick = {},
                        textToShow = "Enabled",
                        leadingIcon = Icons.Default.Person
                    )

                    AstaTonalButton(
                        enabled = false,
                        onClick = {},
                        textToShow = "Disabled",
                        leadingIcon = Icons.Default.Person
                    )
                }

                TitleTexts.Large(text = "Elevated Buttons")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AstaElevatedButton(
                        onClick = {},
                        textToShow = "Enabled",
                        leadingIcon = Icons.Default.Person
                    )

                    AstaElevatedButton(
                        enabled = false,
                        onClick = {},
                        textToShow = "Disabled",
                        leadingIcon = Icons.Default.Person
                    )
                }

                // Outlined Button Section
                TitleTexts.Large(text = "Outlined Buttons")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Filled button With Texts Passed down to it
                    AstaOutlinedButton(
                        onClick = { /*TODO*/ },
                        textToShow = "Enabled",
                        leadingIcon = Icons.Default.Person
                    )
                    AstaOutlinedButton(
                        onClick = { /*TODO*/ },
                        enabled = false,
                        textToShow = "Disabled",
                        leadingIcon = Icons.Default.Person
                    )
                }

                // Icon Button Section
                TitleTexts.Large(text = "Icon Buttons")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Icon Button from Material Icons
                    AstaIconButton(
                        imageVector = Icons.Default.Person, onClick = { /*TODO*/ }
                    )
                    AstaIconButton(
                        imageVector = Icons.Default.Person,
                        onClick = { /*TODO*/ },
                        enabled = false
                    )
                }

                // Asta Text Buttons
                TitleTexts.Large(text = "Text Buttons")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Filled button With Texts Passed down to it
                    AstaTextButton(
                        onClick = { /*TODO*/ },
                        textToShow = "Enabled",
                        leadingIcon = Icons.Default.Person
                    )
                    AstaTextButton(
                        onClick = { /*TODO*/ },
                        enabled = false,
                        textToShow = "Disabled",
                        leadingIcon = Icons.Default.Person
                    )
                }


                // Floating Action Buttons
                TitleTexts.Large(text = "Floating Action Buttons")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AstaFloatingActionButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    }

                    AstaFloatingActionButton(
                        onClick = { /*TODO*/ },
                        imageVector = Icons.Default.Person
                    )
                }

                // Floating Action Buttons
                TitleTexts.Large(text = "Radio Buttons")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AstaRadioButton(
                        selected = true,
                        onClick = {}
                    )

                    AstaRadioButton(
                        selected = true,
                        enabled = false,
                        onClick = {}
                    )
                }


                // Floating Action Buttons
                TitleTexts.Large(text = "Toggle Buttons")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AstaToggleButton(
                        checked = true,
                    )

                    AstaToggleButton(
                        checked = false,
                        enabled = false
                    )
                }
            }
        }
    }
}