package fit.asta.health.tools.sunlight.view.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Composable
fun SkinColorLayout() {
    val itemDataListDemo = remember {
        mutableStateListOf(ItemData(1, "Fair", bgColor = Color(0xffC8AC99)),
            ItemData(2, "Light", bgColor = Color(0xffB1886C)),
            ItemData(3, "Medium Light", bgColor = Color(0xffA0795F)),
            ItemData(4, "Medium ", bgColor = Color(0xff8C624E)),
            ItemData(5, "Medium Dark", bgColor = Color(0xff634B3F)),
            ItemData(6, "Dark", bgColor = Color(0xff544239)))

    }
    ItemList(list = itemDataListDemo, "Please select your skin color")
}