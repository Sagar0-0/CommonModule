package fit.asta.health.designsystemx.molecular.previews

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystemx.AstaThemeX
import fit.asta.health.designsystemx.molecular.button.AstaFilledButton
import fit.asta.health.designsystemx.molecular.button.AstaFloatingActionButton
import fit.asta.health.designsystemx.molecular.button.AstaIconButton
import fit.asta.health.designsystemx.molecular.button.AstaOutlinedButton
import fit.asta.health.designsystemx.molecular.button.AstaTextButton
import fit.asta.health.designsystemx.molecular.texts.LabelTexts
import fit.asta.health.designsystemx.molecular.texts.TitleTexts

// Preview Function
@Preview(
    "Light Button",
    heightDp = 2000
)
@Preview(
    name = "Dark Button",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    heightDp = 2000
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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium)
                ) {
                    TitleTexts.Large(
                        text = "Filled Buttons (Can pass the composable " +
                                "function or just a String directly)"
                    )

                    // Filled Button with custom Composable function passed down to it
                    AstaFilledButton(onClick = { /*TODO*/ }) {
                        LabelTexts.Large(text = "Enabled Button")
                    }
                    AstaFilledButton(
                        onClick = { /*TODO*/ },
                        enabled = false
                    ) {
                        LabelTexts.Large(text = "Disabled Button")
                    }


                    // Filled button With Texts Passed down to it
                    AstaFilledButton(
                        onClick = { /*TODO*/ },
                        textToShow = "Enabled Button"
                    )
                    AstaFilledButton(
                        onClick = { /*TODO*/ },
                        enabled = false,
                        textToShow = "Disabled Button"
                    )
                }

                // Outlined Button Section
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium)
                ) {
                    TitleTexts.Large(
                        text = "Outlined Buttons (Can pass the composable " +
                                "function or just a String directly)"
                    )

                    // Filled Button with custom Composable function passed down to it
                    AstaOutlinedButton(onClick = { /*TODO*/ }) {
                        LabelTexts.Large(text = "Enabled Button")
                    }
                    AstaOutlinedButton(
                        onClick = { /*TODO*/ },
                        enabled = false
                    ) {
                        LabelTexts.Large(text = "Disabled Button")
                    }

                    // Filled button With Texts Passed down to it
                    AstaOutlinedButton(
                        onClick = { /*TODO*/ },
                        textToShow = "Enabled Button"
                    )
                    AstaOutlinedButton(
                        onClick = { /*TODO*/ },
                        enabled = false,
                        textToShow = "Disabled Button"
                    )
                }

                // Icon Button Section
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium)
                ) {

                    TitleTexts.Large(
                        text = "Icon Buttons (Can pass the composable " +
                                "function or just a String directly)"
                    )

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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium)
                ) {

                    TitleTexts.Large(
                        text = "Text Buttons (Can pass the composable " +
                                "function or just a String directly)"
                    )

                    // Filled Button with custom Composable function passed down to it
                    AstaTextButton(onClick = { /*TODO*/ }) {
                        LabelTexts.Large(text = "Enabled Button")
                    }
                    AstaTextButton(
                        onClick = { /*TODO*/ },
                        enabled = false
                    ) {
                        LabelTexts.Large(text = "Disabled Button")
                    }


                    // Filled button With Texts Passed down to it
                    AstaTextButton(onClick = { /*TODO*/ }, textToShow = "Enabled Button")
                    AstaTextButton(
                        onClick = { /*TODO*/ },
                        enabled = false,
                        textToShow = "Disabled Button"
                    )
                }

                AstaFloatingActionButton(
                    onClick = { /*TODO*/ },
                    imageVector = Icons.Default.Person
                )
            }
        }
    }
}