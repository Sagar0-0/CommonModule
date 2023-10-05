package fit.asta.health.designsystem.molecular.previews

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
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppCheckBoxButton
import fit.asta.health.designsystem.molecular.button.AppElevatedButton
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppFloatingActionButton
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.button.AppRadioButton
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.button.AppToggleButton
import fit.asta.health.designsystem.molecular.button.AppTonalButton
import fit.asta.health.designsystem.molecular.texts.TitleTexts

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
            AppButtonScreen()
        }
    }
}


@Composable
fun AppButtonScreen() {
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

                // Filled Button Section
                TitleTexts.Level1(text = "Filled Buttons")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Filled button With Texts Passed down to it
                    AppFilledButton(
                        onClick = { /*TODO*/ },
                        textToShow = "Enabled",
                        leadingIcon = Icons.Default.Person
                    )
                    AppFilledButton(
                        onClick = { /*TODO*/ },
                        enabled = false,
                        textToShow = "Disabled"
                    )
                }

                TitleTexts.Level1(text = "Tonal Buttons")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppTonalButton(
                        onClick = {},
                        textToShow = "Enabled",
                        leadingIcon = Icons.Default.Person
                    )

                    AppTonalButton(
                        enabled = false,
                        onClick = {},
                        textToShow = "Disabled",
                        leadingIcon = Icons.Default.Person
                    )
                }

                TitleTexts.Level1(text = "Elevated Buttons")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppElevatedButton(
                        onClick = {},
                        textToShow = "Enabled",
                        leadingIcon = Icons.Default.Person
                    )

                    AppElevatedButton(
                        enabled = false,
                        onClick = {},
                        textToShow = "Disabled",
                        leadingIcon = Icons.Default.Person
                    )
                }

                // Outlined Button Section
                TitleTexts.Level1(text = "Outlined Buttons")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Filled button With Texts Passed down to it
                    AppOutlinedButton(
                        onClick = { /*TODO*/ },
                        textToShow = "Enabled",
                        leadingIcon = Icons.Default.Person
                    )
                    AppOutlinedButton(
                        onClick = { /*TODO*/ },
                        enabled = false,
                        textToShow = "Disabled",
                        leadingIcon = Icons.Default.Person
                    )
                }

                // Icon Button Section
                TitleTexts.Level1(text = "Icon Buttons")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Icon Button from Material Icons
                    AppIconButton(
                        imageVector = Icons.Default.Person, onClick = { /*TODO*/ }
                    )
                    AppIconButton(
                        imageVector = Icons.Default.Person,
                        onClick = { /*TODO*/ },
                        enabled = false
                    )
                }

                // Asta Text Buttons
                TitleTexts.Level1(text = "Text Buttons")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Filled button With Texts Passed down to it
                    AppTextButton(
                        onClick = { /*TODO*/ },
                        textToShow = "Enabled",
                        leadingIcon = Icons.Default.Person
                    )
                    AppTextButton(
                        onClick = { /*TODO*/ },
                        enabled = false,
                        textToShow = "Disabled",
                        leadingIcon = Icons.Default.Person
                    )
                }


                // Floating Action Buttons
                TitleTexts.Level1(text = "Floating Action Buttons")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppFloatingActionButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    }

                    AppFloatingActionButton(
                        onClick = { /*TODO*/ },
                        imageVector = Icons.Default.Person
                    )
                }

                // Radio Buttons
                TitleTexts.Level1(text = "Radio Buttons")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppRadioButton(
                        selected = true,
                        onClick = {}
                    )

                    AppRadioButton(
                        selected = true,
                        enabled = false,
                        onClick = {}
                    )
                }


                // Toggle Buttons
                TitleTexts.Level1(text = "Toggle Buttons")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppToggleButton(
                        checked = true,
                    )

                    AppToggleButton(
                        checked = false,
                        enabled = false
                    )
                }


                // Check Box Buttons
                TitleTexts.Level1(text = "Check Box Buttons")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppCheckBoxButton(
                        checked = true,
                        onCheckedChange = {}
                    )

                    AppCheckBoxButton(
                        checked = true,
                        enabled = false,
                        onCheckedChange = {}
                    )
                }
            }
        }
    }
}