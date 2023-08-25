package fit.asta.health.tools.water.viewmodel

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
fun BreathingCourseLevels(it: PaddingValues) {

    val itemListData = remember {
        mutableStateListOf(ItemData(1, "The beginner 1  needs slow-paced direction and a great level of detail so they can become familiar with basic  use of the breath.", bgColor = Color.LightGray),
            ItemData(id = 2, display = "Stairs, WalkThe beginner 2 would like to explore their practice and begin to become more familiar with use of the breath. Becoming More Familiar", bgColor = Color.LightGray),
            ItemData(3, "The Intermediate are designed for those who have a good understanding of the basic yoga postures, and have begun to explore a wider variety of poses and styles.", bgColor = Color.LightGray),
            ItemData(4, "classes are designed for more experienced yogis with a very solid understanding of basic yoga postures who are comfortable performing more advanced poses.", bgColor = Color.LightGray),
            ItemData(5, "The advanced practices yoga regularly and has begun to master connection of breath with movement. Hard Work!", bgColor = Color.LightGray),
            )
    }

    ItemList(list = itemListData, rowTitle = "Select the Course Level", it = it)

}

@Preview
@Composable
fun CourseLevel() {

    AppScaffold(topBar = {
        AppTopBarWithHelp(
            title = "Course Level",
            onBack = { },
            onHelp = { /*TODO*/ }
        )
    }, content = {
        BreathingCourseLevels(it = it)
    })
}