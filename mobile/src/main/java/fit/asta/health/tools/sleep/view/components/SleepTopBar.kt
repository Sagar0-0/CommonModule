package fit.asta.health.tools.sleep.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import fit.asta.health.common.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SleepTopBar(
    title: String,
    navController: NavController
) {

    AppTopBar(
        title = title,
        onBack = {
            if (navController.previousBackStackEntry != null)
                navController.popBackStack()
        },
        actions = {
            IconButton(
                onClick = {
                    /*TODO*/
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Help,
                    contentDescription = null,
                )
            }
        }
    )
}