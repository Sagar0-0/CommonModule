package fit.asta.health.tools.breathing.view.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.common.ui.components.generic.AppScaffold
import fit.asta.health.common.ui.components.generic.AppTopBarWithHelp
import fit.asta.health.tools.view.components.ItemData
import fit.asta.health.tools.view.components.ItemList

@Composable
fun BreathingBreakTime(it: PaddingValues) {

    val itemListData = remember {
        mutableStateListOf(
            ItemData(1, "1 Minute", bgColor = Color.LightGray),
            ItemData(id = 2, display = "2 Minutes", bgColor = Color.LightGray),
            ItemData(3, "3 Minutes", bgColor = Color.LightGray),
            ItemData(4, "4 Minutes", bgColor = Color.LightGray),
            ItemData(5, "5 Minutes", bgColor = Color.LightGray),
            ItemData(6, "6 Minutes", bgColor = Color.LightGray),
            ItemData(7, "7 Minutes", bgColor = Color.LightGray),
            ItemData(8, "8 Minutes", bgColor = Color.LightGray),
        )
    }

    ItemList(list = itemListData,
        rowTitle = "Select the break time for your breathing exercise",
        it = it)

}

@Preview
@Composable
fun BreakTime() {

    AppScaffold(
        topBar = {
            AppTopBarWithHelp(
                title = "Break Time",
                onBack = { /*TODO*/ },
                onHelp = { /*TODO*/ }
            )
        }, content = {
        BreathingBreakTime(it = it)
    })
}