package fit.asta.health.feature.scheduler.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.resources.strings.R as StringR

/**
 * This composable function creates a Outlined Search Bar UI in the Screen
 *
 * @param modifier For Modifications Passed from the parent layout
 * @param userInput this is the user input string
 * @param onUserInputChange This function is triggered when the user Input is Changed
 * @param onUserDone This function is triggered when the User Presses the keyboard Done Option in
 * his android
 */
@Composable
fun SearchBarUI(
    modifier: Modifier = Modifier,
    userInput: String,
    onUserInputChange: (String) -> Unit,
    onUserDone: () -> Unit
) {

    // Focus Manager for Input Text Fields
    val focusManager = LocalFocusManager.current

    // This is the text Input where the user will give his input
    OutlinedTextField(
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
        label = {
            BodyTexts.Level2(stringResource(StringR.string.search_from_spotify))
        },

        // Setting Custom Colors for the Outlined TextField
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = AppTheme.colors.primary,
            disabledBorderColor = AppTheme.colors.primaryContainer,
        ),

        // Shape of the TextField
        shape = AppTheme.shape.level2,

        leadingIcon = {

            // Search Icon
            AppLocalImage(
                imageVector = Icons.Outlined.Search,
                contentDescription = stringResource(id = StringR.string.search_button),
                modifier = Modifier.size(AppTheme.imageSize.level3),
                colorFilter = ColorFilter.tint(AppTheme.colors.primary)
            )
        },

        trailingIcon = {
            if (userInput.isNotEmpty()) {

                // Clear Button
                AppIconButton(
                    imageVector = Icons.Outlined.Clear,
                    iconDesc = stringResource(StringR.string.clear_button),
                    modifier = Modifier.size(AppTheme.imageSize.level3),
                    iconTint = AppTheme.colors.primary
                ) { onUserInputChange("") }
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