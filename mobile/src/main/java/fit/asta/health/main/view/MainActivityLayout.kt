@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.main.view

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import fit.asta.health.common.ui.components.generic.AppScaffold
import fit.asta.health.common.ui.components.generic.AppTopBar
import fit.asta.health.common.ui.theme.elevation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.MainTopBarActions
import fit.asta.health.main.Graph
import fit.asta.health.main.sharedViewModel
import fit.asta.health.navigation.home.view.HomeContent
import fit.asta.health.navigation.today.view.TodayContent
import fit.asta.health.navigation.today.viewmodel.TodayPlanViewModel
import fit.asta.health.navigation.track.view.TrackContent
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityLayout(
    locationName: String,
    profileImageUri: Uri?,
    isNotificationEnabled: Boolean,
    onClick: (key: MainTopBarActions) -> Unit,
    onNav: (Graph) -> Unit,
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
            navController = navController, onNav = onNav, innerPadding = it
        )
    }, topBar = {
        AppTopBar(
            backIcon = null, actions = {
                NewMainTopBarActions(
                    onClick = onClick,
                    isNotificationEnabled = isNotificationEnabled,
                    profileImageUri = profileImageUri,
                    locationName = locationName
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
            NavigationBarItem(icon = {
                Icon(
                    painter = painterResource(id = item.icon), contentDescription = item.title
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
    locationName: String,
) {
    Row(Modifier.clickable { onClick(MainTopBarActions.Location) }) {
        Icon(
            Icons.Default.LocationOn,
            contentDescription = "Location",
            tint = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = locationName,
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
                painterResource(id = if (isNotificationEnabled) R.drawable.ic_notifications_on else R.drawable.ic_notifications_off),
                contentDescription = "Notifications",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        IconButton(onClick = { onClick(MainTopBarActions.Share) }) {
            Icon(
                painterResource(id = R.drawable.ic_share_app),
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
                painterResource(id = R.drawable.ic_settings),
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
        BottomNavItem(BottomBarDestination.Home.route, R.drawable.ic_home, "Home"),
        BottomNavItem(BottomBarDestination.Today.route, R.drawable.ic_today, "Today"),
        BottomNavItem(BottomBarDestination.Track.route, R.drawable.ic_track, "Track")
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
    onNav: (Graph) -> Unit,
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
            val uiState by todayPlanViewModel.todayUi.collectAsStateWithLifecycle()
            TodayContent(
                uiState = uiState,
                listMorning = listMorning,
                listAfternoon = listAfternoon,
                listEvening = listEvening,
                hSEvent = todayPlanViewModel::hSEvent,
                onNav = onNav
            )
        }

        composable(BottomBarDestination.Track.route) {
            TrackContent()
        }
    }
}


data class BottomNavItem(
    val route: String,
    val icon: Int,
    val title: String,
)
