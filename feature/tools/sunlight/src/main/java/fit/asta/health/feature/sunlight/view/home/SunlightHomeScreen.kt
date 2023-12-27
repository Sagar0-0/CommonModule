package fit.asta.health.feature.sunlight.view.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import fit.asta.health.data.sunlight.model.network.response.ResponseData.SunlightToolData
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBarWithHelp
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.sunlight.nav.SunlightScreen
import fit.asta.health.feature.sunlight.view.components.*
import fit.asta.health.feature.sunlight.viewmodel.SunlightViewModel
import fit.asta.health.resources.drawables.R
import fit.asta.health.ui.common.PracticeGridView
import fit.asta.health.ui.common.components.CardSunBurn
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun SunlightHomeScreen(navController: NavController, homeViewModel: SunlightViewModel) {

    AppScaffold(
        topBar = {
            AppTopBarWithHelp(
                title = "Sunlight",
                onBack = { navController.popBackStack() },
                onHelp = { /*TODO*/ }
            )
        }, content = {
            SunlightBottomSheet(paddingValues = it, navController = navController, homeViewModel)
        })
}

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SunlightBottomSheet(
    paddingValues: PaddingValues,
    navController: NavController = rememberNavController(),
    homeViewModel: SunlightViewModel = hiltViewModel()
) {
    var visible by remember { mutableStateOf(false) }
    val apiState = homeViewModel.apiState.value
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        sheetContent = {
            SunlightPracticeGridView(
                navController = navController,
                homeViewModel,
                visible,
                apiState
            )
        },
        sheetPeekHeight = 40.dp,
        scaffoldState = scaffoldState
    ) {
        visible = !scaffoldState.bottomSheetState.hasPartiallyExpandedState
        SunlightHomeScreenLayout(paddingValues, apiState)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun SunlightPracticeGridView(
    navController: NavController,
    homeViewModel: SunlightViewModel,
    visible: Boolean,
    apiState: SunlightToolData?
) {
    val selectedSunScreen by homeViewModel.seletedSpfSelection.collectAsState("")
    val selectedSkinExposure by homeViewModel.selectedSkinExposure.collectAsState("")
    val selectedSkinColor by homeViewModel.selectedSkincolorSelection.collectAsState("")
    val selectedAge by homeViewModel.selectedAgeSelection.collectAsState("")
    val startState = homeViewModel.startState

    if (apiState != null) {
        val cardList = listOf(
            PracticeGridView(
                onClick = { navController.navigate(route = SunlightScreen.SPFSelectionScreen.route) },
                cardTitle = apiState.sunlightTool.prc[0].ttl,
                cardValue = selectedSunScreen.let {
                    it.ifBlank { apiState.sunlightTool.prc[0].values[0].name }
                },
                cardImg = R.drawable.sunscreen_icon
            ),
            PracticeGridView(
                onClick = { navController.navigate(route = SunlightScreen.SkinExposureScreen.route) },
                cardTitle = apiState.sunlightTool.prc[1].ttl,
                cardValue = selectedSkinExposure.let { it.ifBlank { apiState.sunlightTool.prc[1].values[0].name } },
                cardImg = R.drawable.skinexposure_icon
            ),
            PracticeGridView(
                onClick = { navController.navigate(route = SunlightScreen.SkinColorScreen.route) },
                cardTitle = apiState.sunlightTool.prc[2].ttl,
                cardValue = selectedSkinColor.let { it.ifBlank { apiState.sunlightTool.prc[2].values[0].name } },
                cardImg = R.drawable.skincolor_icon
            ),
            PracticeGridView(
                onClick = {},
                cardTitle = "Music",
                cardValue = "Spotify",
                cardImg = R.drawable.music_icon
            ),
            PracticeGridView(
                onClick = { navController.navigate(route = SunlightScreen.AgeSelectionScreen.route) },
                cardTitle = apiState.sunlightTool.prc[5].ttl,
                cardValue = selectedAge.let { it.ifBlank { apiState.sunlightTool.prc[5].values[0].name } },
                cardImg = R.drawable.age_icon
            ),
        )
        SunlightBottomSheetGridView(
            cardList = cardList,
            startState = startState,
            navController = navController,
            homeViewModel
        )
    }

}

@Composable
fun SunlightHomeScreenLayout(
    paddingValues: PaddingValues,
    apiState: SunlightToolData?
) {

    Column(
        Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())

    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            TitleTexts.Level2(
                text = "Upcoming Slots",
                color = AppTheme.colors.onSurface
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (apiState != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    UpcomingSlotsCard(apiState)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    UpcomingSlotsCard(apiState)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            TitleTexts.Level2(
                text = "Total Duration",
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        CardSunBurn(
            cardTitle = "Duration",
            cardValue = "1 hr ",
            recommendedTitle = "Vitamin D\nRecommended",
            recommendedValue = "1hr 30 min",
            goalTitle = "Vitamin D\nDaily Goal",
            goalValue = "50 min",
            remainingTitle = "Sunburn\nTime Remaining",
            remainingValue = "30 min",
            valueChanged = null
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            TitleTexts.Level2(
                text = "Total Vitamin D ",
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        TotalVitaminDCard()

        Spacer(modifier = Modifier.height(35.dp))
    }
}

