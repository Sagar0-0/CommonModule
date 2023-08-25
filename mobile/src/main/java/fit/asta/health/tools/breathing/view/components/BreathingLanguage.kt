package fit.asta.health.tools.breathing.view.components

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
fun BreathingLanguage(it: PaddingValues) {

    val itemListData = remember {
        mutableStateListOf(
            ItemData(1, "English", bgColor = Color.LightGray),
            ItemData(id = 2, display = "Hindi", bgColor = Color.LightGray),
            ItemData(3, "Kannada", bgColor = Color.LightGray),
            ItemData(4, "Telugu", bgColor = Color.LightGray),
            ItemData(5, "Bengali", bgColor = Color.LightGray),
        )
    }

    ItemList(list = itemListData, rowTitle = "Select your preferred Language", it = it)

}

@Preview
@Composable
fun Language() {

    AppScaffold(
        topBar = {
            AppTopBarWithHelp(
                title = "Language",
                onBack = { /*TODO*/ },
                onHelp = { /*TODO*/ }
            )
        }, content = {
            BreathingLanguage(it = it)
        })
}