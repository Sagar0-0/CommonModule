package fit.asta.health.tools.sleep

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppTopBarWithHelp
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.tools.sleep.model.network.common.Prc
import fit.asta.health.tools.sleep.utils.SleepNetworkCall
import fit.asta.health.tools.sleep.view.components.SleepBottomSheet
import fit.asta.health.tools.sleep.view.navigation.SleepNavGraph
import fit.asta.health.tools.sleep.view.navigation.SleepToolNavRoutes
import fit.asta.health.tools.sleep.viewmodel.SleepToolViewModel

@AndroidEntryPoint
class SleepToolActivity : ComponentActivity() {

    private lateinit var sleepToolViewModel: SleepToolViewModel

    companion object {

        fun launch(context: Context, userId: String) {
            val intent = Intent(context, SleepToolActivity::class.java)
            intent.apply {
                putExtra("userId", userId)
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                AppSurface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppTheme.colors.background)
                ) {

                    sleepToolViewModel = hiltViewModel()
                    sleepToolViewModel.setUserId(intent.extras?.getString("userId") ?: "")

                    when (sleepToolViewModel.userUIDefaults.collectAsState().value) {
                        is SleepNetworkCall.Initialized<*> -> {
                            sleepToolViewModel.getUserData()
                        }

                        is SleepNetworkCall.Loading<*> -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is SleepNetworkCall.Success<*> -> {

                            val navController = rememberNavController()

                            // Bottom Sheet Details
                            val bottomSheetData =
                                sleepToolViewModel.userUIDefaults.collectAsState()
                                    .value.data?.sleepData?.toolData?.prc

                            // Selected Disturbances which will be shown in the bottom sheet
                            val selectedDisturbances =
                                sleepToolViewModel.userUIDefaults.collectAsState()
                                    .value.data?.sleepData?.toolData?.prc?.find {
                                        it.ttl == "disturbance"
                                    }

                            if (bottomSheetData != null) {
                                ScaffoldUI(
                                    navController = navController,
                                    bottomSheetData = bottomSheetData,
                                    selectedDisturbances = selectedDisturbances
                                )
                            } else {
                                Toast.makeText(
                                    this@SleepToolActivity,
                                    sleepToolViewModel.userUIDefaults.collectAsState().value.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        else -> {
                            Toast.makeText(
                                this@SleepToolActivity,
                                sleepToolViewModel.userUIDefaults.collectAsState().value.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    when (sleepToolViewModel.userValueUpdate.collectAsState().value) {
                        is SleepNetworkCall.Initialized<*> -> {}
                        is SleepNetworkCall.Loading<*> -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is SleepNetworkCall.Success<*> -> {
                            Toast.makeText(
                                this@SleepToolActivity,
                                "Operation Successful",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {
                            Toast.makeText(
                                this@SleepToolActivity,
                                sleepToolViewModel.userValueUpdate.collectAsState().value.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ScaffoldUI(
        navController: NavHostController,
        bottomSheetData: List<Prc>,
        selectedDisturbances: Prc?
    ) {

        val sheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded
        )
        val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
        val backStackEntry = navController.currentBackStackEntryAsState()
        val currentBackStackEntryRoute = backStackEntry.value?.destination?.route
        val shouldShowSheet = currentBackStackEntryRoute == SleepToolNavRoutes.SleepHomeRoute.routes

        BottomSheetScaffold(
            modifier = Modifier.fillMaxSize(),
            sheetShape = RoundedCornerShape(16.dp),
            sheetContent = {
                if (shouldShowSheet) {
                    SleepBottomSheet(
                        scaffoldState = sheetState,
                        navController = navController,
                        bottomSheetData = bottomSheetData,
                        selectedDisturbances = selectedDisturbances,
                        timerStatus = sleepToolViewModel.timerStatus.collectAsState().value
                    ) { sleepToolViewModel.setTimerStatus() }
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
                        if (navController.previousBackStackEntry != null)
                            navController.popBackStack()
                    },
                    onHelp = { /*TODO*/ }
                )
            }
        ) {
            SleepNavGraph(
                navController = navController,
                sleepToolViewModel = sleepToolViewModel
            )
        }
    }
}