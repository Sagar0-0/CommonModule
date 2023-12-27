package fit.asta.health.feature.sunlight.view.spf_selection

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.background.AppTopBarWithHelp
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.sunlight.view.components.SPFLevelContent
import fit.asta.health.feature.sunlight.viewmodel.SunlightViewModel
import fit.asta.health.ui.common.components.ItemData
import fit.asta.health.ui.common.components.ItemList
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun SPFLevel(navController: NavController, homeViewModel: SunlightViewModel) {

    val itemDataListDemo = remember {
        mutableStateListOf(
            ItemData(1, "SPF 15", bgColor = Color(0x66959393)),
            ItemData(2, "SPF 20", bgColor = Color(0x66959393)),
            ItemData(3, "SPF 30", bgColor = Color(0x66959393)),
            ItemData(4, "SPF 40", bgColor = Color(0x66959393)),
            ItemData(5, "SPF 50", bgColor = Color(0x66959393)),
            ItemData(6, "SPF 70 ", bgColor = Color(0x66959393)),
            ItemData(7, "None", bgColor = Color(0x66959393))
        )
    }

    ItemList(list = itemDataListDemo,
        rowTitle = "Choose SPF level of your sunscreen",
        content = { SPFLevelContent() })
    SPFSelectionScreen(
        navController = navController,
        list = itemDataListDemo
    ) { homeViewModel.onSpfSelection(it) }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SPFSelectionScreen(
    navController: NavController,
    list: List<ItemData>,
    onClick: (String) -> Unit
) {
    val itemSelection = remember { mutableIntStateOf(-1) }
    AppScaffold(
        topBar = {
            AppTopBarWithHelp(
                title = "Age",
                onBack = { navController.popBackStack() },
                onHelp = { /*TODO*/ }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            item {
                BodyTexts.Level1(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "Select your age range"
                )
            }
            items(count = list.size) { indexNumber ->
                AppSurface(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable {
                            onClick(list[indexNumber].display)
                            itemSelection.intValue =
                                if (itemSelection.intValue != indexNumber) indexNumber
                                else -1
                        },
                    color = if (itemSelection.intValue != indexNumber) {
                        Color(0xFFE9D7F7)
                    } else {
                        Color(0xFF7415BD)
                    }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TitleTexts.Level2(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .weight(0.5f),
                            text = list[indexNumber].display
                        )
                    }
                }
            }
        }
    }
}