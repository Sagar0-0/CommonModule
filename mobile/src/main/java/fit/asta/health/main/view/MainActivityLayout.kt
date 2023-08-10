@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.main.view

import android.net.Uri
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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Celebration
import androidx.compose.material.icons.rounded.Home
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
import fit.asta.health.common.ui.components.generic.AppErrorScreen
import fit.asta.health.common.ui.components.generic.AppScaffold
import fit.asta.health.common.ui.components.generic.AppState
import fit.asta.health.common.ui.components.generic.AppTopBar
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.ui.theme.elevation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.MainTopBarActions
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.main.Graph
import fit.asta.health.main.sharedViewModel
import fit.asta.health.navigation.home.view.HomeContent
import fit.asta.health.navigation.today.view.TodayContent
import fit.asta.health.navigation.today.view.utils.HourMinAmPm
import fit.asta.health.navigation.today.viewmodel.TodayPlanViewModel
import fit.asta.health.navigation.track.view.TrackContent
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityLayout(
    currentAddressState: ResponseState<String>,
    profileImageUri: Uri?,
    isNotificationEnabled: Boolean,
    onClick: (key: MainTopBarActions) -> Unit,
    onNav: (String) -> Unit,
    onSchedule: (HourMinAmPm?) -> Unit
) {

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination

    AppScaffold(
        bottomBar = {
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
            AppTopBar(
                backIcon = null,
                actions = {
                    NewMainTopBarActions(
                        onClick = onClick,
                        isNotificationEnabled = isNotificationEnabled,
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
                        imageVector = item.icon, contentDescription = item.title
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
    profileImageUri: Uri?,
    currentAddressState: ResponseState<String>,
) {
    Row(Modifier.clickable { onClick(MainTopBarActions.Location) }) {
        Icon(
            modifier = Modifier.padding(start = spacing.small),
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location",
            tint = MaterialTheme.colorScheme.onBackground
        )

        when (currentAddressState) {
            is ResponseState.Loading -> {
                Text(
                    text = "Fetching...",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(spacing.minSmall),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            is ResponseState.Success -> {
                Text(
                    text = currentAddressState.data,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(spacing.minSmall),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            is ResponseState.Error -> {
                Text(
                    text = "Error fetching location",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(spacing.minSmall),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            else -> {}
        }
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
        BottomNavItem(BottomBarDestination.Home.route, Icons.Rounded.Home, "Home"),
        BottomNavItem(BottomBarDestination.Today.route, Icons.Rounded.Celebration, "Today"),
        BottomNavItem(BottomBarDestination.Track.route, Icons.Rounded.BarChart, "Track")
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
        route = Graph.Home.route,
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
                is AppState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        LoadingAnimation()
                    }
                }

                is AppState.Error -> AppErrorScreen(onTryAgain = {
                    todayPlanViewModel.retry()
                }, isInternetError = false)

                is AppState.Success -> {
                    TodayContent(
                        uiState = state.data!!,
                        listMorning = listMorning,
                        listAfternoon = listAfternoon,
                        listEvening = listEvening,
                        listNextDay = listNextDay,
                        hSEvent = todayPlanViewModel::hSEvent,
                        onNav = onNav,
                        onSchedule = onSchedule
                    )
                }

                is AppState.Empty -> {}

                is AppState.NetworkError -> AppErrorScreen(onTryAgain = {
                    todayPlanViewModel.retry()
                })
            }
        }

        composable(BottomBarDestination.Track.route) {
            TrackContent()
        }
    }
}


data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val title: String,
)
