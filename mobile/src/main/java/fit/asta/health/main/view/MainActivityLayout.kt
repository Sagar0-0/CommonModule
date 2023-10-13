@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.main.view

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import fit.asta.health.R
import fit.asta.health.common.utils.HourMinAmPm
import fit.asta.health.common.utils.MainTopBarActions
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
import fit.asta.health.navigation.today.ui.view.HomeEvent
import fit.asta.health.navigation.today.ui.view.TodayContent
import fit.asta.health.navigation.today.ui.vm.TodayPlanViewModel
import fit.asta.health.navigation.tools.ui.view.HomeContent
import fit.asta.health.navigation.track.TrackMenuScreenControl
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityLayout(
    currentAddressState: UiState<String>,
    profileImageUri: String?,
    notificationState: Boolean,
    onClick: (key: MainTopBarActions) -> Unit,
    onNav: (String) -> Unit,
    onSchedule: (HourMinAmPm?) -> Unit,
    onLocation: () -> Unit
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
                onNav = onNav,
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
                        currentAddressState = currentAddressState
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
private fun RowScope.NewMainTopBarActions(
    onClick: (key: MainTopBarActions) -> Unit,
    notificationState: Boolean,
    profileImageUri: String?,
    currentAddressState: UiState<String>,
) {
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
            imageVector = if (notificationState) Icons.Default.NotificationsActive else Icons.Default.NotificationsOff,
            onClick = { onClick(MainTopBarActions.Notification) }
        )
        AppIconButton(
            imageVector = Icons.Default.Share,
            onClick = { onClick(MainTopBarActions.Share) }
        )
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
            imageVector = Icons.Default.Settings,
            onClick = { onClick(MainTopBarActions.Settings) }
        )
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
    onNav: (String) -> Unit,
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
            HomeContent(onNav = onNav)
        }

        composable(BottomBarDestination.Today.route) {
            val todayPlanViewModel: TodayPlanViewModel =
                it.sharedViewModel(navController = navController)
            val listMorning by todayPlanViewModel.alarmListMorning.collectAsStateWithLifecycle()
            val listAfternoon by todayPlanViewModel.alarmListAfternoon.collectAsStateWithLifecycle()
            val listEvening by todayPlanViewModel.alarmListEvening.collectAsStateWithLifecycle()
            val listNextDay by todayPlanViewModel.alarmListNextDay.collectAsStateWithLifecycle()
            val state by todayPlanViewModel.todayState.collectAsStateWithLifecycle()
            val defaultScheduleVisibility by todayPlanViewModel.defaultScheduleVisibility.collectAsStateWithLifecycle()
            TodayContent(
                state = state,
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
                            todayPlanViewModel.getDefaultSchedule(uiEvent.context)
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
