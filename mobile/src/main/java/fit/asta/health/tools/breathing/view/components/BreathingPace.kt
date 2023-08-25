package fit.asta.health.tools.breathing.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.components.*
import fit.asta.health.designsystem.components.generic.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTopBarWithHelp
import fit.asta.health.tools.view.components.ItemData
import fit.asta.health.tools.view.components.ItemList

@Composable
fun BreathingPace(it: PaddingValues) {

    val itemListData = remember {
        mutableStateListOf(
            ItemData(1, "Slow", bgColor = Color.LightGray),
            ItemData(id = 2, display = "Medium", bgColor = Color.LightGray),
            ItemData(3, "Fast", bgColor = Color.LightGray),

            )
    }

    ItemList(list = itemListData, rowTitle = "Select pace for your breathing exercise", it = it)

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Pace() {

    AppScaffold(
        topBar = {
            AppTopBarWithHelp(
                title = "Pace",
                onBack = { /*TODO*/ },
                onHelp = { /*TODO*/ }
            )
        }, content = {
            BreathingPace(it = it)
        })
}