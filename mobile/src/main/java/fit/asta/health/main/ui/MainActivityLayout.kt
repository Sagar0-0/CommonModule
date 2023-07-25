package fit.asta.health.main.ui

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import fit.asta.health.R
import fit.asta.health.common.ui.theme.cardElevation
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

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterial3Api::class)
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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.shadow(elevation = cardElevation.medium),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                title = {
                    Text(
                        text = "",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                actions = {
                    Row(Modifier.clickable { onClick(MainTopBarActions.LOCATION) }) {
                        Icon(
                            Icons.Default.LocationOn, contentDescription = "Location",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = locationName, textAlign = TextAlign.Center,
                            modifier = Modifier.padding(spacing.minSmall),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = { onClick(MainTopBarActions.NOTIFICATION) }) {
                            Icon(
                                painterResource(id = if (isNotificationEnabled) R.drawable.ic_notifications_on else R.drawable.ic_notifications_off),
                                contentDescription = "Notifications",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        IconButton(onClick = { onClick(MainTopBarActions.SHARE) }) {
                            Icon(
                                painterResource(id = R.drawable.ic_share_app),
                                contentDescription = "Share",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        if (profileImageUri != null) {
                            IconButton(onClick = { onClick(MainTopBarActions.PROFILE) }) {
                                Log.d("URI", profileImageUri.toString())
                                Image(
                                    modifier = Modifier.clip(CircleShape),
                                    painter = rememberAsyncImagePainter(
                                        model = profileImageUri, placeholder = painterResource(
                                            id = R.drawable.ic_person
                                        )
                                    ), contentDescription = "Profile"
                                )
                            }
                        }
                        IconButton(onClick = { onClick(MainTopBarActions.SETTINGS) }) {
                            Icon(
                                painterResource(id = R.drawable.ic_settings),
                                contentDescription = "Settings",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                items = listOf(
                    BottomNavItem(BottomBarScreens.Home.route, R.drawable.ic_home, "Home"),
                    BottomNavItem(BottomBarScreens.Today.route, R.drawable.ic_today, "Today"),
                    BottomNavItem(BottomBarScreens.Track.route, R.drawable.ic_track, "Track")
                ),
                currentRoute = navController.currentDestination?.route
                    ?: BottomBarScreens.Home.route,
                onNavigate = { route ->
                    if (route != currentDestination?.route) {
                        Log.d("Navigation", "Try to navigate")

                        navController.navigate(route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            // Avoid multiple copies of the same destination when

                            if (currentDestination != null) {
                                Log.d("Navigation", "Pop up current destination")
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
            )
        }
    ) { innerPadding ->
        NavHost(
            route = Graph.Home.route,
            navController = navController,
            startDestination = BottomBarScreens.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomBarScreens.Home.route) {
                HomeContent(
                    onNav = onNav
                )
            }
            composable(BottomBarScreens.Today.route) {
                val todayPlanViewModel: TodayPlanViewModel = it.sharedViewModel(navController = navController)
                val list by todayPlanViewModel.alarmList.collectAsStateWithLifecycle()
               TodayContent(list = list, hSEvent = todayPlanViewModel::hSEvent, onNav = onNav)
            }

            composable(BottomBarScreens.Track.route) {
                TrackContent()
            }
        }
    }
}


@Composable
fun BottomAppBar(
    items: List<BottomNavItem>,
    currentRoute: String,
    onNavigate: (route: String) -> Unit
) {
    val colors = NavigationBarItemDefaults.colors(
        selectedTextColor = MaterialTheme.colorScheme.primary,
        unselectedTextColor = MaterialTheme.colorScheme.onSurface
    )

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = elevation.high
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
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

data class BottomNavItem(
    val route: String,
    val icon: Int,
    val title: String
)
