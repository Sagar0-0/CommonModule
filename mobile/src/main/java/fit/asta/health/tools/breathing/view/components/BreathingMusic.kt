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
fun BreathingMusic(it: PaddingValues) {

    val itemListData = remember {
        mutableStateListOf(
            ItemData(1, "The Breath of Joy", bgColor = Color.LightGray),
            ItemData(id = 2, display = "Source of your Prana", bgColor = Color.LightGray),
            ItemData(3, "Vital life force", bgColor = Color.LightGray),
            ItemData(4, "Release Stress", bgColor = Color.LightGray),
            ItemData(5, "Quieting the Mind", bgColor = Color.LightGray),
        )
    }

    ItemList(list = itemListData, rowTitle = "Select music for your breathing exercise", it = it)
}

@Preview
@Composable
fun Music() {

    AppScaffold(
        topBar = {
            AppTopBarWithHelp(
                title = "Language",
                onBack = { /*TODO*/ },
                onHelp = { /*TODO*/ }
            )
        }, content = {
            BreathingMusic(it = it)
        })
}