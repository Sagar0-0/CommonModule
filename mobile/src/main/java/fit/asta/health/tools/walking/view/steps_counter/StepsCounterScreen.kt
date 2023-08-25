package fit.asta.health.tools.walking.view.steps_counter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RunCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fit.asta.health.designsystem.components.*
import fit.asta.health.designsystem.components.generic.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTopBarWithHelp
import fit.asta.health.designsystem.theme.spacing
import fit.asta.health.tools.walking.view.home.StepCounterUIEvent
import fit.asta.health.tools.walking.viewmodel.WalkingViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun StepsCounterScreen(navController: NavController, homeViewModel: WalkingViewModel) {
    val state = homeViewModel.uiStateStep.value
    AppScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBarWithHelp(
                title = "Steps Counter",
                onBack = { navController.popBackStack() },
                onHelp = { /*TODO*/ }
            )
        }
    ) {
        StepsItem(
            modifier = Modifier.padding(it),
            state,
            viewModel = homeViewModel,
            navController = navController
        )
    }

}


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun StepsItem(
    modifier: Modifier = Modifier,
    state: StepCounterUIState,
    viewModel: WalkingViewModel,
    navController: NavController
) {

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
                style = MaterialTheme.typography.headlineSmall
            )
        }
        Text(
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
            text = "Session Details",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )

        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(spacing.small),
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
                SessionCard(type = "Calories burned", count = "%.2f Cal".format(state.calories)) {
                }
            }
            item {
                SessionCard(type = "Avg.Pace(min/Km)", count = "${state.time} Min") {
                }
            }
            item {
                SessionCard(type = "Steps", count = "${state.steps}") {
                }
            }
            item {
                SessionCard(type = "Avg.Speed(Km/Hr)", count = "%.2f Km/Hr".format(state.speed)) {
                }
            }
            item {
                SessionCard(type = "Avg.Heart.Rt(BPM)", count = "${state.heartRate}") {
                }
            }
            item {
                SessionCard(type = "Weight Loosed", count = "%.2f grams".format(state.weightLoosed)) {
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
                viewModel.onUIEvent(StepCounterUIEvent.EndButtonClicked)
            }
            ButtonWithColor(
                modifier = Modifier
                    .weight(0.5f), color = Color.Blue, text = "RESUME"
            ) {
                navController.popBackStack()
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
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(Color.LightGray),
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
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = count,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}