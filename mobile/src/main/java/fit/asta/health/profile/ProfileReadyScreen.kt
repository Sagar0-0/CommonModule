package fit.asta.health.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.profile.model.domain.UserProfile
import fit.asta.health.profile.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@Composable
fun ProfileReadyScreen(userProfile: UserProfile) {

    var content by remember { mutableStateOf(1) }

    val context = LocalContext.current

    Scaffold(topBar = {
        Column {
            TopAppBar(title = {
                Text(text = "Profile Screen")
            }, navigationIcon = {

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Outlined.NavigateBefore, "back", modifier = Modifier.size(48.dp))
                }

            }, actions = {
                IconButton(onClick = { CreateUserProfileActivity.launch(context) }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            })

            BottomNavigation(backgroundColor = Color.White) {
                BottomNavigationItem(selected = false, onClick = { content = 1 }, icon = {
                    Icon(Icons.Outlined.AccountCircle, contentDescription = "Profile Screen 1")
                }, label = {
                    Text(text = "Details", fontSize = 11.sp, maxLines = 1)
                }, selectedContentColor = MaterialTheme.colorScheme.primary)
                BottomNavigationItem(selected = false, onClick = { content = 2 }, icon = {
                    Icon(Icons.Outlined.Face, contentDescription = "Profile Screen 2")
                }, label = {
                    Text(text = "Physique", fontSize = 11.sp, maxLines = 1)
                }, selectedContentColor = MaterialTheme.colorScheme.primary)
                BottomNavigationItem(selected = false, onClick = { content = 3 }, icon = {
                    Icon(Icons.Outlined.Favorite, contentDescription = "Profile Screen 3")
                }, label = {
                    Text(text = "Health", fontSize = 11.sp, maxLines = 1)
                }, selectedContentColor = MaterialTheme.colorScheme.primary)
                BottomNavigationItem(selected = false, onClick = { content = 4 }, icon = {
                    Icon(Icons.Default.Emergency, contentDescription = "Profile Screen 4")
                }, label = {
                    Text(text = "Lifestyle", fontSize = 11.sp, maxLines = 1)
                }, selectedContentColor = MaterialTheme.colorScheme.primary)
                BottomNavigationItem(selected = true, onClick = { content = 5 }, icon = {
                    Icon(Icons.Outlined.Egg, contentDescription = "Profile Screen 2")
                }, label = {
                    Text(text = "Diet", fontSize = 11.sp, maxLines = 1)
                }, selectedContentColor = MaterialTheme.colorScheme.primary)
            }

        }
    }) { p ->
        Box(modifier = Modifier.padding(p)) {
            when (content) {
                1 -> {
                    ContactLayout(basicDetails = userProfile.contact)
                }
                2 -> {
                    PhysiqueLayout(phy = userProfile.physique)
                }
                3 -> {
                    HealthLayout(health = userProfile.health)
                }
                4 -> {
                    LifeStyleLayout(lifeStyle = userProfile.lifeStyle)
                }
                5 -> {
                    DietLayout(diet = userProfile.diet)
                }
            }
        }
    }
}
