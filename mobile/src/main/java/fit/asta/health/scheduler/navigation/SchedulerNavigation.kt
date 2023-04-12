package fit.asta.health.scheduler.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fit.asta.health.scheduler.compose.components.*
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.AlarmSettingScreen
import fit.asta.health.scheduler.compose.screen.tagscreen.TagsScreen
import fit.asta.health.scheduler.compose.screen.timesettingscreen.TimeSettingScreen
import fit.asta.health.scheduler.viewmodel.SchedulerViewModel

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun SchedulerNavigation(navController: NavHostController,schedulerViewModel: SchedulerViewModel) {

    NavHost(
        navController = navController,
        startDestination = AlarmSchedulerScreen.AlarmSettingHome.route
    ) {

        composable(route = AlarmSchedulerScreen.AlarmSettingHome.route) {
            AlarmSettingScreen(navController,schedulerViewModel)
        }

        composable(route = AlarmSchedulerScreen.TagSelection.route) {
            TagsScreen(navController,schedulerViewModel)
        }
        composable(route = AlarmSchedulerScreen.IntervalSettingsSelection.route) {
            TimeSettingScreen(navController,schedulerViewModel)
        }

    }
}