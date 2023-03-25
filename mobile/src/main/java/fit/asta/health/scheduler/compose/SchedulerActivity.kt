package fit.asta.health.scheduler.compose

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.scheduler.compose.components.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SchedulerActivity : AppCompatActivity() {

    private lateinit var navController: NavHostController

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            navController = rememberNavController()
        }
    }

}


@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun MyApp() {
    val navController = rememberNavController()
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    navController.navigatorProvider += bottomSheetNavigator

    ModalBottomSheetLayout(bottomSheetNavigator = bottomSheetNavigator) {

        NavHost(
            navController = navController,
            startDestination = SchedulerScreen.AlarmSettingHome.route
        ) {

            composable(route = SchedulerScreen.AlarmSettingHome.route) {
                AlarmSettingLayout(onNavigateToTag = { navController.navigate(route = SchedulerScreen.TagSelection.route) },
                    onNavigateToLabel = { navController.navigate(route = SchedulerScreen.LabelSelection.route) },
                    onNavigateToDesc = { navController.navigate(route = SchedulerScreen.DescSelection.route) },
                    onNavigateToIntervalSettings = { navController.navigate(route = SchedulerScreen.IntervalSettingsSelection.route) },
                    onNavigateToReminderMode = { navController.navigate(route = SchedulerScreen.ReminderModeSelection.route) },
                    onNavigateToVibration = { navController.navigate(route = SchedulerScreen.VibrationSelection.route) },
                    onNavigateToSound = { navController.navigate(route = SchedulerScreen.SoundSelection.route) })
            }

            composable(route = SchedulerScreen.TagSelection.route) {
                TagSelectionLayout(onNavigateBack = { navController.popBackStack() },
                    onNavigateCustomTag = { navController.navigate(route = SchedulerScreen.CustomTagCreation.route) })
            }

            bottomSheet(route = SchedulerScreen.CustomTagCreation.route) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    CustomTagBottomSheetLayout(onNavigateBack = { navController.popBackStack() })
                }
            }

            bottomSheet(route = SchedulerScreen.LabelSelection.route) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    CustomLabelBottomSheetLayout(text = "Labels",
                        label = "Enter your Label",
                        onNavigateBack = { navController.popBackStack() })
                }
            }

            bottomSheet(route = SchedulerScreen.DescSelection.route) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    CustomLabelBottomSheetLayout(text = "Add Description",
                        label = "Enter Description",
                        onNavigateBack = { navController.popBackStack() })
                }
            }

            composable(route = SchedulerScreen.IntervalSettingsSelection.route) {
                IntervalTimeLayout(onNavigateBack = { navController.navigate(SchedulerScreen.AlarmSettingHome.route) },
                    onNavigateSnooze = { navController.navigate(SchedulerScreen.SnoozeSelection.route) },
                    onNavigateRepetitiveInterval = { navController.navigate(SchedulerScreen.RepetitiveInterval.route) })
            }

            bottomSheet(route = SchedulerScreen.RepetitiveInterval.route) {
                TimePickerDemo {
                    navController.popBackStack()
                }
            }

            bottomSheet(route = SchedulerScreen.SnoozeSelection.route) {
                SnoozeBottomSheet {
                    navController.popBackStack()
                }
            }

            bottomSheet(route = SchedulerScreen.ReminderModeSelection.route) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    NotificationBottomSheetLayout(text = "Select Reminder Mode",
                        onNavigateBack = { navController.popBackStack() })
                }
            }

            bottomSheet(route = SchedulerScreen.VibrationSelection.route) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    VibrationBottomSheetLayout(text = "Select Vibration Intensity",
                        onNavigateBack = { navController.popBackStack() })
                }
            }

            bottomSheet(route = SchedulerScreen.SoundSelection.route) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    VibrationBottomSheetLayout(text = "Select Vibration Intensity",
                        onNavigateBack = { navController.popBackStack() })
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.N)
@Preview
@Composable
fun ScreenPreview() {
    MyApp()
}