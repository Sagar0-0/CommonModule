package fit.asta.health.thirdparty.spotify.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

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
            Text(
                "Search",
                color = MaterialTheme.colorScheme.primary
            )
        },

        // Setting Custom Colors for the Outlined TextField
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.primary,
            disabledBorderColor = MaterialTheme.colorScheme.primaryContainer,
        ),

        // Shape of the TextField
        shape = RoundedCornerShape(8.dp),

        leadingIcon = {

            // Search Icon
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search Button",
                modifier = Modifier
                    .size(24.dp)
            )
        },

        trailingIcon = {

            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                if (userInput.isNotEmpty()) {

                    // Clear Button
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = "Clear Button",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onUserInputChange("")
                            }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

                // Filter / Sort Icons
                Icon(
                    imageVector = Icons.Outlined.Sort,
                    contentDescription = "Filter Button",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onFilterButtonClick()
                        }
                )
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