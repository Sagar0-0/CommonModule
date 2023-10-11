package fit.asta.health.feature.spotify.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.textfield.AppOutlinedTextField


/**
 * This composable function creates a Outlined Search Bar UI in the Screen
 *
 * @param modifier For Modifications Passed from the parent layout
 * @param userInput this is the user input string
 * @param onUserInputChange This function is triggered when the user Input is Changed
 * @param onFilterButtonClick This function is triggered when the Filter / Sort button is pressed
 * @param onUserDone This function is triggered when the User Presses the keyboard Done Option in
 * his android
 */
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    userInput: String,
    onUserInputChange: (String) -> Unit,
    onFilterButtonClick: () -> Unit,
    onUserDone: () -> Unit
) {

    // Focus Manager for Input Text Fields
    val focusManager = LocalFocusManager.current

    // This is the text Input where the user will give his input
    AppOutlinedTextField(
        value = userInput,
        onValueChange = {

            val shouldDo = userInput.length < it.length
            onUserInputChange(it)
            if (shouldDo)
                onUserDone()
        },

        // This input field contains 1 line
        singleLine = true,

        modifier = modifier,

        // This is the Label of the input which is shown to the top left when selected
        label = "Search from Spotify",

        // Shape of the TextField
        shape = AppTheme.shape.level2,

        leadingIcon = {

            // Search Icon
            AppIcon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search Button"
            )
        },

        trailingIcon = {

            Row(
                modifier = Modifier
                    .padding(horizontal = AppTheme.spacing.level3),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
            ) {

                if (userInput.isNotEmpty()) {

                    // Clear Button
                    AppIconButton(
                        imageVector = Icons.Outlined.Clear,
                        iconDesc = "Clear Button",
                        modifier = Modifier.size(AppTheme.iconSize.level3)
                    ) { onUserInputChange("") }
                }

                // Filter / Sort Icons
                AppIconButton(
                    imageVector = Icons.Outlined.Sort,
                    iconDesc = "Filter Button",
                    modifier = Modifier.size(AppTheme.iconSize.level3)
                ) { onFilterButtonClick() }
            }
        },

        // Shows the Search Option in the Place of Enter in Keyboard
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),

        // Triggers this function when the Search Icon on keyboard is Pressed
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
                onUserDone()
            }
        )
    )
}