package fit.asta.health.feature.sleep.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import fit.asta.health.data.sleep.model.db.SleepData
import fit.asta.health.data.sleep.model.network.common.Prc
import fit.asta.health.data.sleep.model.network.get.ProgressData
import fit.asta.health.data.sleep.utils.SleepNetworkCall
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.token.AppSheetValue
import fit.asta.health.designsystem.atomic.token.checkState
import fit.asta.health.designsystem.molecular.ProgressBarInt
import fit.asta.health.designsystem.molecular.background.AppBottomSheetScaffold
import fit.asta.health.designsystem.molecular.background.AppSheetState
import fit.asta.health.designsystem.molecular.background.AppTopBarWithHelp
import fit.asta.health.designsystem.molecular.background.appRememberBottomSheetScaffoldState
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.sleep.view.components.SleepBottomSheet
import fit.asta.health.feature.sleep.view.navigation.SleepToolNavRoutes
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun SleepHomeScreen(
    navController: NavHostController,
    progressData: ProgressData?,
    bottomSheetData: List<Prc>?,
    selectedDisturbances: Prc?,
    timerStatus: SleepNetworkCall<List<SleepData>>,
    onStartStopClick: () -> Unit,
    onBack: () -> Unit
) {
    val scaffoldState = appRememberBottomSheetScaffoldState(bottomSheetState = AppSheetState(
        initialValue = AppSheetValue.PartiallyExpanded
    ))
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentBackStackEntryRoute = backStackEntry.value?.destination?.route
    val shouldShowSheet = currentBackStackEntryRoute == SleepToolNavRoutes.SleepHomeRoute.routes

    AppBottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        sheetShape = RoundedCornerShape(16.dp),
        sheetContent = {
            if (shouldShowSheet) {
                SleepBottomSheet(
                    animatedState = checkState(scaffoldState),
                    navController = navController,
                    bottomSheetData = bottomSheetData,
                    selectedDisturbances = selectedDisturbances,
                    timerStatus = timerStatus,// sleepToolViewModel.timerStatus.collectAsState().value
                ) {
                    onStartStopClick()// sleepToolViewModel.setTimerStatus()
                }
            }
        },
        sheetPeekHeight = if (shouldShowSheet) 230.dp else 0.dp,
        scaffoldState = scaffoldState,
        topBar = {

            val topBarTitle = when (currentBackStackEntryRoute) {
                SleepToolNavRoutes.SleepHomeRoute.routes -> "Sleep Tool"
                SleepToolNavRoutes.SleepFactorRoute.routes -> "Sleep Factors"
                SleepToolNavRoutes.SleepDisturbanceRoute.routes -> "Sleep Disturbances"
                SleepToolNavRoutes.SleepJetLagTipsRoute.routes -> "Jet Lab Tips"
                SleepToolNavRoutes.SleepGoalsRoute.routes -> "Goals"
                else -> {
                    ""
                }
            }

            AppTopBarWithHelp(
                title = topBarTitle,
                onBack = {
                    onBack()
                },
                onHelp = { /*TODO*/ }
            )
        }
    ) {
        ScaffoldUI(progressData = progressData)
    }
}

@Composable
fun ScaffoldUI(
    progressData: ProgressData?
) {

    // State to hold the current time
    var currentTime by remember { mutableStateOf(LocalTime.now()) }

    // Format the time as "hh:mm a"
    val formattedTime = remember(currentTime) {
        DateTimeFormatter.ofPattern("hh:mm a").format(currentTime)
    }

    // Update the time using LaunchedEffect
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = LocalTime.now()
            delay(1000) // Delay for 1 second
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val color = AppTheme.colors.onBackground

        // This is the Clock
        Box(
            modifier = Modifier
                .size(200.dp)
                .drawBehind {

                    val center = Offset(size.width / 2, size.height / 2)
                    val radius = size.minDimension / 2

                    // Draw the clock face
                    drawCircle(color.copy(alpha = .3f), radius, center)
                },
            contentAlignment = Alignment.Center
        ) {

            // Timing of the clock
            TitleTexts.Level1(
                text = formattedTime.uppercase()
            )
        }

        // These are the variables which are used to
        val recommended = progressData?.rcm?.toFloat() ?: 8f
        val goal = progressData?.tgt?.toFloat() ?: 7f
        val achieved = progressData?.ach?.toFloat() ?: 0f

        AppCard {
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ProgressBarInt(
                    modifier = Modifier.weight(0.3f),
                    targetDistance = recommended,
                    progress = achieved / recommended,
                    name = "Recommended",
                    postfix = "Hrs"
                )
                ProgressBarInt(
                    modifier = Modifier.weight(0.3f),
                    targetDistance = goal,
                    progress = achieved / goal,
                    name = "Goal",
                    postfix = "Hrs"
                )
                ProgressBarInt(
                    modifier = Modifier.weight(0.3f),
                    targetDistance = goal,
                    progress = (goal - achieved) / goal,
                    name = "Remaining",
                    postfix = "Hrs"
                )
            }
        }
    }
}