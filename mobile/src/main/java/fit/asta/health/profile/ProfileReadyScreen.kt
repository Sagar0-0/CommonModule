package fit.asta.health.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Egg
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.common.ui.components.generic.AppScaffold
import fit.asta.health.common.ui.components.generic.AppTopBar
import fit.asta.health.profile.model.domain.UserProfile
import fit.asta.health.profile.view.ContactLayout
import fit.asta.health.profile.view.DietLayout
import fit.asta.health.profile.view.HealthLayout
import fit.asta.health.profile.view.LifeStyleLayout
import fit.asta.health.profile.view.PhysiqueLayout

@Composable
fun ProfileReadyScreen(userProfile: UserProfile, onBack: () -> Unit, onEdit: () -> Unit) {

    var content by remember { mutableStateOf(1) }

    val context = LocalContext.current

    AppScaffold(topBar = {
        Column {

            AppTopBar(
                title = "Profile Screen",
                onBack = onBack,
                actions = {
                    IconButton(onClick = onEdit) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                })

            val colors =
                NavigationBarItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary)

            NavigationBar(containerColor = Color.White, tonalElevation = 0.dp) {
                NavigationBarItem(selected = false, onClick = { content = 1 }, icon = {
                    Icon(Icons.Outlined.AccountCircle, contentDescription = "Profile Screen 1")
                }, label = {
                    Text(text = "Details", fontSize = 11.sp, maxLines = 1)
                }, colors = colors)
                NavigationBarItem(selected = false, onClick = { content = 2 }, icon = {
                    Icon(Icons.Outlined.Face, contentDescription = "Profile Screen 2")
                }, label = {
                    Text(text = "Physique", fontSize = 11.sp, maxLines = 1)
                }, colors = colors)
                NavigationBarItem(selected = false, onClick = { content = 3 }, icon = {
                    Icon(Icons.Outlined.Favorite, contentDescription = "Profile Screen 3")
                }, label = {
                    Text(text = "Health", fontSize = 11.sp, maxLines = 1)
                }, colors = colors)
                NavigationBarItem(selected = false, onClick = { content = 4 }, icon = {
                    Icon(Icons.Default.Emergency, contentDescription = "Profile Screen 4")
                }, label = {
                    Text(text = "Lifestyle", fontSize = 11.sp, maxLines = 1)
                }, colors = colors)
                NavigationBarItem(selected = true, onClick = { content = 5 }, icon = {
                    Icon(Icons.Outlined.Egg, contentDescription = "Profile Screen 2")
                }, label = {
                    Text(text = "Diet", fontSize = 11.sp, maxLines = 1)
                }, colors = colors)
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
