package fit.asta.health.designsystem.molecular


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.outlined.NavigateBefore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.texts.TitleTexts

/**The [AppTopBar] is a custom composable function used to create a top app bar for Android apps
 * with Jetpack Compose UI toolkit.
 * @param modifier the [Modifier] to be applied to this top app bar
 * @param title the title to be displayed in the top app bar
 * @param actions the actions displayed at the end of the top app bar.
 * @param [containerColor] The background color of the app bar.
 * @param [titleContentColor] The text color of the title in the app bar.
 * @param [backIcon] An optional parameter that represents the back navigation icon in the app bar.
 * @param [onBack] A callback function that gets executed when the back navigation icon is clicked.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    containerColor: Color = Color.Transparent,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    titleContentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    backIcon: ImageVector? = Icons.Outlined.NavigateBefore,
    onBack: () -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = {
            TitleTexts.Level2(text = title)
        },
        navigationIcon = {
            backIcon?.let {
                AppIconButton(imageVector = it, onClick = onBack)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor, titleContentColor = titleContentColor
        ),
        modifier = modifier,
        actions = actions,
        scrollBehavior = scrollBehavior,
        windowInsets = windowInsets
    )
}

/** [AppTopBarWithHelp] is a Composable function that creates a custom top app bar with a help icon.
 * @param [title] The title to be displayed in the app bar.
 * @param [onBack] The lambda function to be executed when the back button is clicked.
 * @param [onHelp] The lambda function to be executed when the help icon is clicked.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBarWithHelp(
    title: String,
    onBack: () -> Unit,
    onHelp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppTopBar(
        title = title, onBack = onBack, modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AppIconButton(imageVector = Icons.Default.Help, onClick = onHelp)
        }
    }

}