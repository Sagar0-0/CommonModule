@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.main.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Celebration
import androidx.compose.material.icons.outlined.Handyman
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import coil.compose.rememberAsyncImagePainter
import fit.asta.health.R
import fit.asta.health.common.utils.HourMinAmPm
import fit.asta.health.common.utils.MainTopBarActions
import fit.asta.health.common.utils.PrefManager
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.sharedViewModel
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppNavigationBar
import fit.asta.health.designsystem.molecular.background.AppNavigationBarItem
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.scheduler.services.SchedulerWorker
import fit.asta.health.navigation.today.ui.view.HomeEvent
import fit.asta.health.navigation.today.ui.view.TodayContent
import fit.asta.health.navigation.today.ui.vm.TodayPlanViewModel
import fit.asta.health.navigation.tools.ui.view.HomeContent
import fit.asta.health.navigation.tools.ui.view.HomeScreenUiEvent
import fit.asta.health.navigation.tools.ui.viewmodel.HomeViewModel
import fit.asta.health.navigation.track.TrackMenuScreenControl
import fit.asta.health.subscription.remote.model.SubscriptionResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityLayout(
    currentAddressState: UiState<String>,
    refCode: String,
    profileImageUri: String?,
    notificationState: Boolean,
    sessionState: Boolean,
    onClick: (key: MainTopBarActions) -> Unit,
    onNav: (String) -> Unit,
    subscriptionResponse: SubscriptionResponse?,
    onEvent: (HomeScreenUiEvent) -> Unit,
    onSchedule: (HourMinAmPm?) -> Unit,
    onLocation: () -> Unit,
    onWalkingTool: () -> Unit
) {

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination

    AppScaffold(
        bottomBar = {
            MainBottomAppBar(
                navController = navController, currentDestination = currentDestination
            )
        },
        content = {
            MainNavHost(
                navController = navController,
                refCode = refCode,
                onNav = onNav,
                subscriptionResponse = subscriptionResponse,
                onEvent = onEvent,
                onSchedule = onSchedule,
                onLocation = onLocation,
                innerPadding = it
            )
        },
        topBar = {
            AppTopBar(
                backIcon = null,
                actions = {
                    NewMainTopBarActions(
                        onClick = onClick,
                        notificationState = notificationState,
                        profileImageUri = profileImageUri,
                        currentAddressState = currentAddressState,
                        sessionState = sessionState,
                        onSession = onWalkingTool
                    )
                }
            )
        }
    )
}

@Composable
private fun BottomAppBarLayout(
    items: List<BottomNavItem>,
    currentRoute: String,
    onNavigate: (route: String) -> Unit,
) {

    AppNavigationBar(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = AppTheme.elevation.level4
    ) {
        items.forEach { item ->
            AppNavigationBarItem(
                selected = currentRoute == item.route,
                icon = {
                    AppIcon(
                        imageVector = if (currentRoute == item.route) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                label = item.title
            ) { onNavigate(item.route) }
        }
    }
}

@Composable
private fun RowScope.SessionTopBarActions(
    onClick: () -> Unit,
) {

}

@Composable
private fun RowScope.NewMainTopBarActions(
    onClick: (key: MainTopBarActions) -> Unit,
    notificationState: Boolean,
    profileImageUri: String?,
    currentAddressState: UiState<String>,
    sessionState: Boolean,
    onSession: () -> Unit
) {
    if (sessionState) {
        Row(
            modifier = Modifier
                .weight(1f)
                .clickable { onSession() },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppIcon(
                modifier = Modifier.padding(start = AppTheme.spacing.level1),
                imageVector = Icons.Default.DirectionsWalk,
                contentDescription = "walking"
            )

            TitleTexts.Level2(
                text = "Tap to open Walking Session",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .weight(1f)
                    .padding(AppTheme.spacing.level0),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
    } else {
        Row(
            modifier = Modifier
                .weight(1f)
                .clickable { onClick(MainTopBarActions.Location) },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppIcon(
                modifier = Modifier.padding(start = AppTheme.spacing.level1),
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location"
            )

            TitleTexts.Level2(
                text =
                when (currentAddressState) {
                    UiState.Idle -> {
                        R.string.select_location.toStringFromResId()
                    }

                    UiState.Loading -> {
                        R.string.fetching_location.toStringFromResId()
                    }

                    is UiState.Success -> {
                        currentAddressState.data
                    }

                    is UiState.ErrorMessage -> {
                        currentAddressState.resId.toStringFromResId()
                    }

                    else -> {
                        ""
                    }
                },
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .weight(1f)
                    .padding(AppTheme.spacing.level0),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }

        Row(horizontalArrangement = Arrangement.End) {
            AppIconButton(
                imageVector = if (notificationState) Icons.Default.NotificationsActive else Icons.Default.NotificationsOff
            ) { onClick(MainTopBarActions.Notification) }
            AppIconButton(
                imageVector = Icons.Default.Share
            ) { onClick(MainTopBarActions.Share) }
            if (profileImageUri != null) {
                AppIconButton(onClick = { onClick(MainTopBarActions.Profile) }) {
                    Image(
                        modifier = Modifier.clip(CircleShape),
                        painter = rememberAsyncImagePainter(
                            model = profileImageUri,
                            placeholder = painterResource(id = R.drawable.ic_person)
                        ), contentDescription = "Profile"
                    )
                }
            }
            AppIconButton(
                imageVector = Icons.Default.Settings
            ) { onClick(MainTopBarActions.Settings) }
        }
    }
}

@Composable
private fun MainBottomAppBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
) {

    val currentRoute = navController.currentDestination?.route ?: BottomBarDestination.Today.route

    BottomAppBarLayout(items = listOf(
        BottomNavItem(
            BottomBarDestination.Today.route,
            Icons.Filled.Celebration,
            Icons.Outlined.Celebration,
            "Today"
        ),
        BottomNavItem(
            BottomBarDestination.Tools.route,
            Icons.Filled.Handyman, Icons.Outlined.Handyman,
            "Tools"
        ),
        BottomNavItem(
            BottomBarDestination.Track.route,
            Icons.Filled.BarChart,
            Icons.Outlined.BarChart,
            "Track"
        )
    ),
        currentRoute = currentRoute,
        onNavigate = { route -> onNavigate(navController, route, currentDestination) })
}

private fun onNavigate(
    navController: NavController,
    route: String,
    currentDestination: NavDestination?,
) {
    if (route != currentDestination?.route) {
        navController.navigate(route) {
            if (currentDestination != null) {
                popUpTo(currentDestination.id) {
                    saveState = true
                    inclusive = true
                }
            } else {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                    inclusive = true
                }
            }
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
private fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    refCode: String,
    subscriptionResponse: SubscriptionResponse?,
    onNav: (String) -> Unit,
    onEvent: (HomeScreenUiEvent) -> Unit,
    onSchedule: (HourMinAmPm?) -> Unit,
    onLocation: () -> Unit,
    innerPadding: PaddingValues,
) {
    NavHost(
        route = HOME_GRAPH_ROUTE,
        navController = navController,
        startDestination = BottomBarDestination.Today.route,
        modifier = modifier.padding(innerPadding)
    ) {
        composable(BottomBarDestination.Tools.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            LaunchedEffect(
                key1 = Unit,
                block = {
                    homeViewModel.loadHomeData()
                }
            )

            HomeContent(
                homeViewModel = homeViewModel,
                refCode = refCode,
                subscriptionResponse = subscriptionResponse,
                onNav = onNav,
                onEvent = onEvent
            )

        }

        composable(BottomBarDestination.Today.route) { navBackStackEntry ->
            val todayPlanViewModel: TodayPlanViewModel =
                navBackStackEntry.sharedViewModel(navController = navController)
            val calendarUiModel by todayPlanViewModel.calendarUiModel.collectAsStateWithLifecycle()
            val alarmList by todayPlanViewModel.alarmList.collectAsStateWithLifecycle()
            val listMorning by todayPlanViewModel.alarmListMorning.collectAsStateWithLifecycle()
            val listAfternoon by todayPlanViewModel.alarmListAfternoon.collectAsStateWithLifecycle()
            val listEvening by todayPlanViewModel.alarmListEvening.collectAsStateWithLifecycle()
            val listNextDay by todayPlanViewModel.alarmListNextDay.collectAsStateWithLifecycle()
            val state by todayPlanViewModel.todayState.collectAsStateWithLifecycle()
            val defaultScheduleVisibility by todayPlanViewModel.defaultScheduleVisibility.collectAsStateWithLifecycle()
            val context = LocalContext.current
            val notificationPermissionResultLauncher: ActivityResultLauncher<String> =
                rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) { perms ->
                    if (perms) {
                        Toast.makeText(
                            context,
                            "Notification is recommended for better functionality.",
                            Toast.LENGTH_SHORT
                        ).show()
                        PrefManager.setNotificationPermissionRejectedCount(context, 0)
                    } else {
                        Toast.makeText(
                            context,
                            "Notification is recommended for better functionality.",
                            Toast.LENGTH_SHORT
                        ).show()
                        PrefManager.setNotificationPermissionRejectedCount(
                            context,
                            PrefManager.getNotificationPermissionRejectedCount(context) + 1
                        )
                    }
                }

            val checkPermissionAndLaunchScheduler = {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    PrefManager.setNotificationPermissionRejectedCount(context, 0)
                } else {
                    if (PrefManager.getNotificationPermissionRejectedCount(context) > 2) {
                        Toast.makeText(
                            context,
                            "Please allow Notification permission access.",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        with(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)) {
                            data = Uri.fromParts("package", context.packageName, null)
                            addCategory(Intent.CATEGORY_DEFAULT)
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                            addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                            context.startActivity(this)
                        }
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            notificationPermissionResultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }
                }
            }
            LaunchedEffect(Unit) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    checkPermissionAndLaunchScheduler()
                }
            }
            LaunchedEffect(Unit) {
                setupWorker(context)
            }
            TodayContent(
                state = state,
                calendarUiModel = calendarUiModel,
                list = alarmList,
                onDateClickListener = { todayPlanViewModel.setWeekDate(it) },
                userName = todayPlanViewModel.getUserName(),
                defaultScheduleVisibility = defaultScheduleVisibility,
                listMorning = listMorning,
                listAfternoon = listAfternoon,
                listEvening = listEvening,
                listNextDay = listNextDay,
                onNav = onNav,
                hSEvent = { uiEvent ->
                    when (uiEvent) {
                        is HomeEvent.EditAlarm -> {
                            todayPlanViewModel.setAlarmPreferences(uiEvent.alarm.alarmId)
                        }

                        is HomeEvent.SetAlarm -> {
                            todayPlanViewModel.setAlarmPreferences(999)
                        }

                        is HomeEvent.DeleteAlarm -> {
                            todayPlanViewModel.deleteAlarm(uiEvent.alarm, uiEvent.context)
                        }

                        is HomeEvent.SkipAlarm -> {
                            todayPlanViewModel.skipAlarm(uiEvent.alarm, uiEvent.context)
                        }

                        is HomeEvent.SetDefaultSchedule -> {
                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.POST_NOTIFICATIONS
                                ) != PackageManager.PERMISSION_GRANTED
                            ) {
                                checkPermissionAndLaunchScheduler()
                            } else {
                                todayPlanViewModel.getDefaultSchedule(uiEvent.context)
                            }
                        }

                        is HomeEvent.NavSchedule -> {
                            onSchedule(uiEvent.hourMinAmPm)
                        }

                        is HomeEvent.Retry -> {
                            onLocation()
                        }
                    }
                },
            )
        }

        composable(BottomBarDestination.Track.route) {
            TrackMenuScreenControl()
        }
    }
}

data class BottomNavItem(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val title: String,
)

fun setupWorker(context: Context) {
    Log.d("TAGTAG", "app setupWorker")
    WorkManager.getInstance(context).apply {
        // Run sync on app startup and ensure only one sync worker runs at any time
        enqueueUniqueWork(
            "SyncWorkName",
            ExistingWorkPolicy.KEEP,
            SchedulerWorker.startUpSyncWork(),
        )
    }
//   WorkManager.getInstance(context).apply {
//       enqueueUniquePeriodicWork(
//           "worker_for_upload_data", ExistingPeriodicWorkPolicy.UPDATE,
//           SchedulerWorker.startUpSyncWork()
//       )
//   }
}