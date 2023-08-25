package fit.asta.health.tools.sunlight.view.age_selection

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fit.asta.health.designsystem.components.*
import fit.asta.health.designsystem.components.generic.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTopBarWithHelp
import fit.asta.health.tools.sunlight.viewmodel.SunlightViewModel
import fit.asta.health.tools.view.components.ItemData
import fit.asta.health.tools.view.components.ItemList
import kotlinx.coroutines.ExperimentalCoroutinesApi

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalCoroutinesApi::class)

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
    AgeSelectionScreen(
        navController = navController,
        list = itemListData
    ) { homeViewModel.onAgeSelection(it) }

}


@Composable
fun AgeSelectionScreen(
    navController: NavController,
    list: List<ItemData>,
    onClick: (String) -> Unit
) {
    val itemSelection = remember {
        mutableIntStateOf(-1)
    }
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
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "Select your age range",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            items(count = list.size) { indexNumber ->
                Surface(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable {
                            onClick(list[indexNumber].display)
                            itemSelection.value =
                                if (itemSelection.value != indexNumber) indexNumber
                                else -1
                        },
                    color = if (itemSelection.value != indexNumber) {
                        Color(0xFFE9D7F7)
                    } else {
                        Color(0xFF7415BD)
                    },
                    shape = RoundedCornerShape(corner = CornerSize(15.dp)),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .weight(0.5f),
                            text = list[indexNumber].display,
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 25.sp
                        )
                    }
                }
            }
        }
    }
}