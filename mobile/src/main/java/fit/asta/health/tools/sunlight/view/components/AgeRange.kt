package fit.asta.health.tools.sunlight.view.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Composable
fun AgeRange() {
    val itemListData = remember {
        mutableStateListOf(ItemData(1, "Pre Teen - 30 years", bgColor = Color(0x66959393)),
            ItemData(id = 2, display = "30 - 60 years", bgColor = Color(0x66959393)),
            ItemData(3, "Above 60 years", bgColor = Color(0x66959393)))
    }

    ItemList(list = itemListData, rowTitle = "Please select your age range")
}