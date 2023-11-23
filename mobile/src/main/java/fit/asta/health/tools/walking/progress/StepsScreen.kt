package fit.asta.health.tools.walking.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.molecular.background.AppBottomSheetScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBarWithHelp
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.tools.walking.nav.DailyFitnessModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepsScreen(
    state: DailyFitnessModel,
    onStart: () -> Unit,
    list: SnapshotStateList<DailyFitnessModel>,
    onBack: () -> Unit
) {
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        skipHiddenState = true
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    AppBottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        sheetPeekHeight = 240.dp,
        topBar = {
            AppTopBarWithHelp(
                title = "Steps Tool",
                onBack = onBack,
                onHelp = { /*TODO*/ }
            )
        },
        sheetContent = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppFilledButton(textToShow = "Start") {
                    onStart()
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { TitleTexts.Level2(text = "Total Steps Data ") }
            item {
                AppCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            TitleTexts.Level2(text = "steps")
                            TitleTexts.Level2(text = state.stepCount.toString())
                        }
                        Column {
                            TitleTexts.Level2(text = "calorie")
                            TitleTexts.Level2(text = state.caloriesBurned.toString())
                        }
                        Column {
                            TitleTexts.Level2(text = "distance")
                            TitleTexts.Level2(text = state.distance.toString())
                        }
                    }
                }
            }
            item { TitleTexts.Level2(text = "Today Session Data ") }

            items(list) {
                AppCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            TitleTexts.Level2(text = "steps")
                            TitleTexts.Level2(text = it.stepCount.toString())
                        }
                        Column {
                            TitleTexts.Level2(text = "calorie")
                            TitleTexts.Level2(text = it.caloriesBurned.toString())
                        }
                        Column {
                            TitleTexts.Level2(text = "distance")
                            TitleTexts.Level2(text = it.distance.toString())
                        }
                    }
                }
            }
        }
    }
}