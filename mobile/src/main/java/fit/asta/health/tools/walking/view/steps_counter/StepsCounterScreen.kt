package fit.asta.health.tools.walking.view.steps_counter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.RunCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fit.asta.health.R
import fit.asta.health.tools.walking.view.component.ButtonWithColor
import fit.asta.health.tools.walking.view.home.StepCounterUIEvent
import fit.asta.health.tools.walking.viewmodel.WalkingViewModel
import fit.asta.health.common.ui.theme.spacing
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun StepsCounterScreen(navController: NavController,homeViewModel:WalkingViewModel) {
    val state=homeViewModel.uiState.value
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
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
                            text = "Steps Counter",
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
        StepsItem(modifier = Modifier.padding(it),state, viewModel = homeViewModel)
    }

}


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun StepsItem(modifier: Modifier = Modifier,state: StepCounterUIState,viewModel:WalkingViewModel) {

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                modifier = Modifier
                    .size(35.dp)
                    .align(Alignment.CenterVertically),
                imageVector = Icons.Default.RunCircle,
                contentDescription = null
            )
            Text(
                text = "Continue running?",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h5
            )
        }
        Text(
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
            text = "Session Details",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h6
        )

        LazyVerticalGrid(
            horizontalArrangement =Arrangement.spacedBy(spacing.small),
            verticalArrangement = Arrangement.spacedBy(spacing.medium),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
               horizontal = 16.dp
            )
        ) {
            item {
                SessionCard(type = "Distance(Kms)", count = "${state.distance} Km") {
                }
            }
            item {
                SessionCard(type = "Calories burned", count = "500 Cal") {
                }
            }
            item {
                SessionCard(type = "Avg.Pace(min/Km)", count = "30 Min") {
                }
            }
            item {
                SessionCard(type = "Steps", count = "${state.steps}") {
                }
            }
            item {
                SessionCard(type = "Avg.Speed(Km/Hr)", count = "${state.speed} Km/Hr") {
                }
            }
            item {
                SessionCard(type = "Avg.Heart.Rt(BPM)", count = "85") {
                }
            }
            item {
                SessionCard(type = "Weight Loosed", count = "0.25 kg") {
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            ButtonWithColor(
                modifier = Modifier
                    .weight(0.5f), color = Color.Red, text = "END"
            ) {
                viewModel.onUIEvent(StepCounterUIEvent.StopButtonClicked)
            }
            ButtonWithColor(
                modifier = Modifier
                    .weight(0.5f), color = Color.Blue, text = "RESUME"
            ) {

            }
        }
    }
}

@Composable
@Preview
fun SessionCard(
    modifier: Modifier = Modifier,
    type: String = "Distance",
    count: String = "500Km",
    onClick: () -> Unit={}
) {
    Card(
        modifier = modifier
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.LightGray,
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(spacing.small),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = type,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = count,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}