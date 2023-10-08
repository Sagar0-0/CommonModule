package fit.asta.health.tools.sunlight.view.skincolor_selection

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
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
import fit.asta.health.designsystem.molecular.background.AppTopBarWithHelp
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.tools.sunlight.viewmodel.SunlightViewModel
import fit.asta.health.tools.view.components.ItemData
import fit.asta.health.tools.view.components.ItemList
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun SkinColorLayout(navController: NavController, homeViewModel: SunlightViewModel) {
    val itemDataListDemo = remember {
        mutableStateListOf(
            ItemData(1, "Fair", bgColor = Color(0xffC8AC99)),
            ItemData(2, "Light", bgColor = Color(0xffB1886C)),
            ItemData(3, "Medium Light", bgColor = Color(0xffA0795F)),
            ItemData(4, "Medium ", bgColor = Color(0xff8C624E)),
            ItemData(5, "Medium Dark", bgColor = Color(0xff634B3F)),
            ItemData(6, "Dark", bgColor = Color(0xff544239))
        )

    }

    ItemList(list = itemDataListDemo, "Please select your skin color")
    SkinColorScreen(navController, itemDataListDemo) { homeViewModel.onSkinColorSelection(it) }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SkinColorScreen(navController: NavController, list: List<ItemData>, onClick: (String) -> Unit) {

    val itemSelection = remember { mutableIntStateOf(-1) }
    AppScaffold(
        topBar = {
            AppTopBarWithHelp(
                title = "Skin Color",
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
                    color = list[indexNumber].bgColor!!,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TitleTexts.Level2(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .weight(0.5f),
                            text = list[indexNumber].display
                        )
                        if (itemSelection.intValue == indexNumber) {
                            Box(contentAlignment = Alignment.TopStart) {
                                AppIcon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = Color.Green,
                                    modifier = Modifier
                                        .size(25.dp)
                                        .padding(top = 8.dp, end = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}