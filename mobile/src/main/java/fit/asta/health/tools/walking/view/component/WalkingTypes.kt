package fit.asta.health.tools.walking.view.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.common.ui.components.AppScaffold
import fit.asta.health.common.ui.components.AppTopBarWithHelp
import fit.asta.health.tools.view.components.ItemData
import fit.asta.health.tools.view.components.ItemList

@Composable
fun WalkingTypeLayoutWalkingType(it: PaddingValues) {

    val itemListData = remember {
        mutableStateListOf(ItemData(1, "Walking", bgColor = Color.LightGray),
            ItemData(id = 2, display = "Stairs, Walk", bgColor = Color.LightGray),
            ItemData(3, "Treadmill", bgColor = Color.LightGray),
            ItemData(4, "Dog Walk", bgColor = Color.LightGray),
            ItemData(5, "Power Walk", bgColor = Color.LightGray),
            ItemData(5, "Running", bgColor = Color.LightGray),
            ItemData(5, "Trekking", bgColor = Color.LightGray))
    }

    ItemList(list = itemListData, rowTitle = "Select the walking type", it = it)

}

@Preview
@Composable
fun WalkingType() {

    AppScaffold(topBar = {
        AppTopBarWithHelp(
            title = "Walking Types",
            onBack = { /*TODO*/ },
            onHelp = { /*TODO*/ }
        )
    }, content = {
        WalkingTypeLayoutWalkingType(it = it)
    })
}