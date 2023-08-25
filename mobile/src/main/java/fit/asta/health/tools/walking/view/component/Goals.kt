package fit.asta.health.tools.walking.view.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.components.generic.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTopBarWithHelp
import fit.asta.health.tools.view.components.ItemData
import fit.asta.health.tools.view.components.ItemList


@Composable
fun GoalLayout(it: PaddingValues) {

    val itemListData = remember {
        mutableStateListOf(ItemData(1, "Loosing Weight", bgColor = Color.LightGray),
            ItemData(id = 2, display = "Boost Mind", bgColor = Color.LightGray),
            ItemData(3, "Tone Body", bgColor = Color.LightGray),
            ItemData(4, "Improve muscles", bgColor = Color.LightGray),
            ItemData(5, "Reduce Stress and Anxiety", bgColor = Color.LightGray))
    }

    ItemList(list = itemListData, rowTitle = "Select you walking goals", it = it)

}

@Preview
@Composable
fun Goal() {

    AppScaffold(topBar = {
        AppTopBarWithHelp(
            title = "Goals",
            onBack = { /*TODO*/ },
            onHelp = { /*TODO*/ }
        )
    }, content = {
        GoalLayout(it = it)
    })
}