@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.main.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Celebration
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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
import fit.asta.health.common.utils.MainTopBarActions
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.components.generic.AppErrorScreen
import fit.asta.health.designsystem.components.generic.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTopBar
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.designsystem.theme.elevation
import fit.asta.health.designsystem.theme.spacing
import fit.asta.health.main.sharedViewModel
import fit.asta.health.navigation.home.view.HomeContent
import fit.asta.health.navigation.today.domain.model.TodayData
import fit.asta.health.navigation.today.ui.view.HomeEvent
import fit.asta.health.navigation.today.ui.view.TodayContent
import fit.asta.health.navigation.today.ui.view.utils.HourMinAmPm
import fit.asta.health.navigation.today.ui.vm.TodayPlanViewModel
import fit.asta.health.navigation.track.TrackNavGraph
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityLayout(
    currentAddressState: UiState<String>,
    profileImageUri: String?,
    isNotificationEnabled: Boolean,
    onClick: (key: MainTopBarActions) -> Unit,
    onNav: (String) -> Unit,
    onSchedule: (HourMinAmPm?) -> Unit
) {

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination

    AppScaffold(bottomBar = {
        MainBottomAppBar(
            navController = navController, currentDestination = currentDestination
        )
    }, content = {
        MainNavHost(
            navController = navController,
            onNav = onNav,
            onSchedule = onSchedule,
            innerPadding = it
        )
    }, topBar = {
        AppTopBar(backIcon = null, actions = {
            NewMainTopBarActions(
                onClick = onClick,
                isNotificationEnabled = isNotificationEnabled,
                profileImageUri = profileImageUri,
                currentAddressState = currentAddressState
            )
        })
    })
}


@Composable
private fun BottomAppBarLayout(
    items: List<BottomNavItem>,
    currentRoute: String,
    onNavigate: (route: String) -> Unit,
) {
    val colors = NavigationBarItemDefaults.colors(
        selectedTextColor = MaterialTheme.colorScheme.primary,
        unselectedTextColor = MaterialTheme.colorScheme.onSurface
    )

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = elevation.high
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (currentRoute == item.route) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                onClick = { onNavigate(item.route) },
                colors = colors
            )
        }
    }
}


@Composable
private fun NewMainTopBarActions(
    onClick: (key: MainTopBarActions) -> Unit,
    isNotificationEnabled: Boolean,
    profileImageUri: String?,
    currentAddressState: UiState<String>,
) {
    Row(Modifier.clickable { onClick(MainTopBarActions.Location) }) {
        Icon(
            modifier = Modifier.padding(start = spacing.small),
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location",
            tint = MaterialTheme.colorScheme.onBackground
        )

        Text(
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

                is UiState.Error -> {
                    currentAddressState.resId.toStringFromResId()
                }
            },
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(spacing.minSmall),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
    Row(
        Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = { onClick(MainTopBarActions.Notification) }) {
            Icon(
                imageVector = if (isNotificationEnabled) Icons.Default.NotificationsActive else Icons.Default.NotificationsOff,
                contentDescription = "Notifications",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        IconButton(onClick = { onClick(MainTopBarActions.Share) }) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        if (profileImageUri != null) {
            IconButton(onClick = { onClick(MainTopBarActions.Profile) }) {
                Image(
                    modifier = Modifier.clip(CircleShape), painter = rememberAsyncImagePainter(
                        model = profileImageUri,
                        placeholder = painterResource(id = R.drawable.ic_person)
                    ), contentDescription = "Profile"
                )
            }
        }
        IconButton(onClick = { onClick(MainTopBarActions.Settings) }) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


@Composable
private fun MainBottomAppBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
) {

    val currentRoute = navController.currentDestination?.route ?: BottomBarDestination.Home.route

    BottomAppBarLayout(items = listOf(
        BottomNavItem(
            BottomBarDestination.Home.route,
            Icons.Filled.Home, Icons.Outlined.Home,
            "Home"
        ), BottomNavItem(
            BottomBarDestination.Today.route,
            Icons.Filled.Celebration,
            Icons.Outlined.Celebration,
            "Today"
        ), BottomNavItem(
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
    innerPadding: PaddingValues,
) {
    NavHost(
        route = HOME_GRAPH_ROUTE,
        navController = navController,
        startDestination = BottomBarDestination.Home.route,
        modifier = modifier.padding(innerPadding)
    ) {
        composable(BottomBarDestination.Home.route) {
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
            when (state) {
                is UiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        LoadingAnimation()
                    }
                }

                is UiState.Error -> AppErrorScreen(onTryAgain = {
                    todayPlanViewModel.retry()
                }, isInternetError = false)

                is UiState.Success -> {
                    TodayContent(
                        uiState = (state as UiState.Success<TodayData>).data,
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

                                is HomeEvent.RemoveAlarm -> {
                                    todayPlanViewModel.removeAlarm(uiEvent.alarm, uiEvent.event)
                                }

                                is HomeEvent.UndoAlarm -> {
                                    todayPlanViewModel.undo(uiEvent.alarm, uiEvent.event)
                                }

                                is HomeEvent.SkipAlarm -> {
                                    todayPlanViewModel.skipAlarm(uiEvent.alarm, uiEvent.context)
                                }

                                is HomeEvent.SetDefaultSchedule -> {
                                    todayPlanViewModel.getDefaultSchedule()
                                }

                                is HomeEvent.NavSchedule -> {
                                    onSchedule(uiEvent.hourMinAmPm)
                                }
                            }
                        },
                    )
                }

                else -> {}
            }
        }

        composable(BottomBarDestination.Track.route) {
            TrackNavGraph()
        }
    }
}


data class BottomNavItem(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val title: String,
)
