package fit.asta.health.scheduler.compose

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.navigation.SchedulerNavigation
import fit.asta.health.scheduler.viewmodel.SchedulerViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SchedulerActivity : AppCompatActivity() {

    private var alarmEntity: AlarmEntity? = null
    private lateinit var schedulerViewModel: SchedulerViewModel

    companion object {

        fun launch(context: Context) {
            Log.d("sj", "launch: Start ${context}")
            val intent = Intent(context, SchedulerActivity::class.java)
            intent.apply {
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    SchedulerNavigation(
                        navController = rememberNavController(),
                        hiltViewModel<SchedulerViewModel>()
                    )
                }
            }
        }
        schedulerViewModel = ViewModelProvider(this)[SchedulerViewModel::class.java]
        schedulerViewModel.context = this

        if (intent.getParcelableExtra<AlarmEntity>("alarmItem") != null) {
            alarmEntity = intent.getParcelableExtra("alarmItem")
            schedulerViewModel.setAlarmEntityIntent(alarmEntity)
        }
    }

}

@Composable
fun MyApp(context: @Composable () -> Unit) {
    AppTheme {
        context()
    }
}
//
//@RequiresApi(Build.VERSION_CODES.N)
//@OptIn(ExperimentalMaterialNavigationApi::class)
//@Composable
//fun MainCompose(navController: NavHostController, bottomSheetNavigator: BottomSheetNavigator) {
//
//    navController.navigatorProvider.addNavigator(bottomSheetNavigator)
//
//    ModalBottomSheetLayout(bottomSheetNavigator = bottomSheetNavigator) {
//        NavHost(
//            navController = navController,
//            startDestination = SchedulerScreen.AlarmSettingHome.route
//        ) {
//
//            composable(route = SchedulerScreen.AlarmSettingHome.route) {
//                AlarmSettingLayout(onNavigateToTag = { navController.navigate(route = SchedulerScreen.TagSelection.route) },
//                    onNavigateToLabel = { navController.navigate(route = SchedulerScreen.LabelSelection.route) },
//                    onNavigateToDesc = { navController.navigate(route = SchedulerScreen.DescSelection.route) },
//                    onNavigateToIntervalSettings = { navController.navigate(route = SchedulerScreen.IntervalSettingsSelection.route) },
//                    onNavigateToReminderMode = { navController.navigate(route = SchedulerScreen.ReminderModeSelection.route) },
//                    onNavigateToVibration = { navController.navigate(route = SchedulerScreen.VibrationSelection.route) },
//                    onNavigateToSound = { navController.navigate(route = SchedulerScreen.SoundSelection.route) })
//            }
//
//            composable(route = SchedulerScreen.TagSelection.route) {
//                TagSelectionLayout(onNavigateBack = { navController.popBackStack() },
//                    onNavigateCustomTag = { navController.navigate(route = SchedulerScreen.CustomTagCreation.route) })
//            }
//
//            bottomSheet(route = SchedulerScreen.CustomTagCreation.route) {
//                Column(modifier = Modifier.fillMaxWidth()) {
//                    CustomTagBottomSheetLayout(onNavigateBack = { navController.popBackStack() })
//                }
//            }
//
//            bottomSheet(route = SchedulerScreen.LabelSelection.route) {
//                Column(modifier = Modifier.fillMaxWidth()) {
//                    CustomLabelBottomSheetLayout(text = "Labels",
//                        label = "Enter your Label",
//                        onNavigateBack = { navController.popBackStack() })
//                }
//            }
//
//            bottomSheet(route = SchedulerScreen.DescSelection.route) {
//                Column(modifier = Modifier.fillMaxWidth()) {
//                    CustomLabelBottomSheetLayout(text = "Add Description",
//                        label = "Enter Description",
//                        onNavigateBack = { navController.popBackStack() })
//                }
//            }
//
//            composable(route = SchedulerScreen.IntervalSettingsSelection.route) {
//                IntervalTimeLayout(onNavigateBack = { navController.navigate(SchedulerScreen.AlarmSettingHome.route) },
//                    onNavigateSnooze = { navController.navigate(SchedulerScreen.SnoozeSelection.route) },
//                    onNavigateRepetitiveInterval = { navController.navigate(SchedulerScreen.RepetitiveInterval.route) })
//            }
//
//            bottomSheet(route = SchedulerScreen.RepetitiveInterval.route) {
//                TimePickerDemo {
//                    navController.popBackStack()
//                }
//            }
//
//            bottomSheet(route = SchedulerScreen.SnoozeSelection.route) {
//                SnoozeBottomSheet {
//                    navController.popBackStack()
//                }
//            }
//
//            bottomSheet(route = SchedulerScreen.ReminderModeSelection.route) {
//                Column(modifier = Modifier.fillMaxWidth()) {
//                    NotificationBottomSheetLayout(text = "Select Reminder Mode",
//                        onNavigateBack = { navController.popBackStack() })
//                }
//            }
//
//            bottomSheet(route = SchedulerScreen.VibrationSelection.route) {
//                Column(modifier = Modifier.fillMaxWidth()) {
//                    VibrationBottomSheetLayout(text = "Select Vibration Intensity",
//                        onNavigateBack = { navController.popBackStack() })
//                }
//            }
//
//            bottomSheet(route = SchedulerScreen.SoundSelection.route) {
//                Column(modifier = Modifier.fillMaxWidth()) {
//                    VibrationBottomSheetLayout(text = "Select Vibration Intensity",
//                        onNavigateBack = { navController.popBackStack() })
//                }
//            }
//        }
//    }
//}
//
//
//@RequiresApi(Build.VERSION_CODES.N)
//@Preview
//@Composable
//fun ScreenPreview() {
//
//}