@file:Suppress("UNUSED_EXPRESSION")

package fit.asta.health.navigation.home.view.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllSchedulesCards() {

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "My Schedules")
        }, navigationIcon = {
            Icon(Icons.Outlined.NavigateBefore, "back")
        })
    }) {
        it
    }

}