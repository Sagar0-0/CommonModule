package fit.asta.health

import android.app.Activity
import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.elevation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.MainTopBarActions
import fit.asta.health.navigation.home.view.HomeContent
import fit.asta.health.navigation.today.view.TodayContent
import fit.asta.health.navigation.track.view.TrackContent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Composable
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_UNDEFINED,
    showBackground = true, showSystemUi = true
)
fun MainLayoutPreviewB() {
    MainActivityLayout(
        location = "Home",
        profileImageUri = null,
        isNotificationEnabled = true,
        isInternetAvailable = true,
        onClick = {}
    )
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun MainLayoutPreviewW() {
    MainActivityLayout(
        location = "Home",
        profileImageUri = null,
        isNotificationEnabled = true,
        isInternetAvailable = true,
        onClick = {}
    )
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun MainActivityLayout(
    location: String,
    profileImageUri: Uri?,
    isNotificationEnabled: Boolean,
    isInternetAvailable: Boolean,
    onClick: (key: MainTopBarActions) -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.shadow(elevation = cardElevation.medium),
                backgroundColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Row(Modifier.clickable { onClick(MainTopBarActions.LOCATION) }) {
                    Icon(Icons.Default.LocationOn, contentDescription = "Location")
                    Text(
                        text = location, textAlign = TextAlign.Center,
                        modifier = Modifier.padding(spacing.minSmall)
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
                            contentDescription = "Notifications"
                        )
                    }
                    IconButton(onClick = { onClick(MainTopBarActions.SHARE) }) {
                        Icon(
                            painterResource(id = R.drawable.ic_share_app),
                            contentDescription = "Share"
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
                            contentDescription = "Settings"
                        )
                    }
                }
            }
        },
        bottomBar = {
            BottomAppBar(
                items = listOf(
                    BottomNavItem("home", R.drawable.ic_home, "Home"),
                    BottomNavItem("today", R.drawable.ic_today, "Today"),
                    BottomNavItem("track", R.drawable.ic_track, "Track")
                ),
                currentRoute = navController.currentDestination?.route ?: "home",
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
            navController, startDestination = "home",
            Modifier.padding(innerPadding)
        ) {
            composable("home") {
                val context = LocalContext.current
                HomeContent(context as Activity)
            }

            composable("today") {
                TodayContent()
            }

            composable("track") {
                TrackContent()
            }
        }
        if (!isInternetAvailable) {
            val msg = stringResource(id = R.string.OFFLINE_STATUS)
            LaunchedEffect(Unit) {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = msg,
                    duration = SnackbarDuration.Indefinite
                )
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
    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colorScheme.background,
        elevation = elevation.high
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                onClick = { onNavigate(item.route) },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

data class BottomNavItem(
    val route: String,
    val icon: Int,
    val title: String
)
