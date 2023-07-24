package fit.asta.health.tools.sunlight.view.skincolor_selection

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
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
    val itemSelection = remember {
        mutableStateOf(-1)
    }
    Scaffold(
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
                            text = "Skin Color",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.leading_icon_sunlight_topbar),
                                contentDescription = "leadingIcon",
                                Modifier.size(26.dp)
                            )
                        }
                    }
                },
                tonalElevation = 10.dp,
                containerColor = MaterialTheme.colorScheme.onPrimary
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
                    color = list[indexNumber].bgColor!!,
                    shape = RoundedCornerShape(corner = CornerSize(15.dp)),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
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
                        if (itemSelection.value == indexNumber) {
                            Box(contentAlignment = Alignment.TopStart) {
                                Icon(
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