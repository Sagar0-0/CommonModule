package fit.asta.health.tools.walking.view.walking_types

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdsClick
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fit.asta.health.R
import fit.asta.health.common.ui.components.*
import fit.asta.health.tools.walking.viewmodel.WalkingViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun WalkingtypesScreen(
    navController: NavController, homeViewModel: WalkingViewModel
) {
    val itemList = listOf(
        "Walking",
        "Stairs,Walk",
        "Treadmill",
        "Dog Walk",
        "Power Walk",
        "Running",
        "Trekking"
    )

    SelectItem(
        navController = navController,
        list = itemList
    ) { homeViewModel.onWalkTypesSelected(it) }

}


@Composable
fun SelectItem(navController: NavController, list: List<String>, onClick: (String) -> Unit) {

    val itemSelection = remember {
        mutableStateOf(-1)
    }
    AppScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NavigationBar(
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_exercise_back),
                                contentDescription = null,
                                Modifier.size(24.dp)
                            )
                        }
                        Text(
                            text = "Walking Type",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_physique),
                                contentDescription = null,
                                Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                tonalElevation = 10.dp,
                containerColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {  Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Select the Walking Type",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyLarge
            ) }
            items(count = list.size) { indexNumber ->
                Surface(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable {
                            onClick(list[indexNumber])
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
                            text = list[indexNumber],
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 25.sp
                        )

                        Icon(
                            modifier = Modifier.weight(0.5f),
                            imageVector = Icons.Default.AdsClick,
                            contentDescription = null
                        )
                    }
                }

            }
        }
    }
}



