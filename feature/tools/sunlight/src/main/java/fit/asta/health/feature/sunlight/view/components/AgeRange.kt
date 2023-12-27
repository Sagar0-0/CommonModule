package fit.asta.health.feature.sunlight.view.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import fit.asta.health.feature.sunlight.viewmodel.SunlightViewModel
import fit.asta.health.ui.common.components.ItemData
import fit.asta.health.ui.common.components.ItemList

@Composable
fun AgeRange(navController: NavController, homeViewModel: SunlightViewModel) {

    val itemListData = remember {
        mutableStateListOf(
            ItemData(1, "Pre Teen - 30 years", bgColor = Color(0x66959393)),
            ItemData(id = 2, display = "30 - 60 years", bgColor = Color(0x66959393)),
            ItemData(3, "Above 60 years", bgColor = Color(0x66959393))
        )
    }

    ItemList(list = itemListData, rowTitle = "Please select your age range")

}