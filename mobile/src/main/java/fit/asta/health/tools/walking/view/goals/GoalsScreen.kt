package fit.asta.health.tools.walking.view.goals

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdsClick
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
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
import fit.asta.health.tools.walking.model.ListItem
import fit.asta.health.tools.walking.viewmodel.WalkingViewModel
import fit.asta.health.common.ui.theme.spacing
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun GoalsScreen(navController: NavController, homeViewModel: WalkingViewModel) {
    val goals = listOf(
        "Walking",
        "Stairs,Walk",
        "Treadmill",
        "Dog Walk",
        "Power Walk",
        "Running",
        "Trekking"
    )
    var items by remember {
        mutableStateOf(goals.map {
            ListItem(title = it, isSelected = false)
        })
    }
    homeViewModel.onGoalSelected(items.filter { it.isSelected }.map { it.title })
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BottomNavigation(
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        androidx.compose.material3.IconButton(onClick = { navController.popBackStack() }) {
                            androidx.compose.material3.Icon(
                                painter = painterResource(id = R.drawable.ic_exercise_back),
                                contentDescription = null,
                                Modifier.size(24.dp)
                            )
                        }
                        androidx.compose.material3.Text(
                            text = "Goals",
                            fontSize = 20.sp,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                        androidx.compose.material3.IconButton(onClick = { /*TODO*/ }) {
                            androidx.compose.material3.Icon(
                                painter = painterResource(id = R.drawable.ic_physique),
                                contentDescription = null,
                                Modifier.size(24.dp),
                                tint = androidx.compose.material3.MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                elevation = 10.dp,
                backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
            )
        }
    ) {

        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "Select the Goals",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.body1
                )
            }
            items(items.size) { i ->
                Surface(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable {
                            items = items.mapIndexed { j, item ->
                                if (i == j) {
                                    item.copy(isSelected = !item.isSelected)
                                } else item
                            }
                        },
                    color = if (!items[i].isSelected) {
                        Color(0xFFE9D7F7)
                    } else {
                        Color(0xFF7415BD)
                    },
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
                            text = items[i].title,
                            style = MaterialTheme.typography.subtitle1,
                            fontSize = 25.sp
                        )
                        if (items[i].isSelected) {
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
}