package fit.asta.health.profile.feature.show

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.designsystem.components.generic.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTopBar
import fit.asta.health.profile.data.model.domain.UserProfile
import fit.asta.health.profile.feature.show.view.ContactLayout
import fit.asta.health.profile.feature.show.view.DietLayout
import fit.asta.health.profile.feature.show.view.HealthLayout
import fit.asta.health.profile.feature.show.view.LifeStyleLayout
import fit.asta.health.profile.feature.show.view.PhysiqueLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileReadyScreen(
    userProfile: UserProfile,
    onBack: () -> Unit,
    onEdit: () -> Unit,
) {
    var content by remember { mutableIntStateOf(1) }

    val navigationItemDetails = NavigationItem(
        icon = Icons.Outlined.AccountCircle,
        contentDescription = "Profile Screen 1",
        label = stringResource(id = R.string.details),
    )

    val navigationItemPhysique = NavigationItem(
        icon = Icons.Outlined.Face, contentDescription = "Profile Screen 2", label = stringResource(
            R.string.physique
        )
    )

    val navigationItemHealth = NavigationItem(
        icon = Icons.Outlined.Favorite,
        contentDescription = "Profile Screen 3",
        label = stringResource(
            R.string.health
        )
    )

    val navigationItemLifestyle = NavigationItem(
        icon = Icons.Default.Emergency,
        contentDescription = "Profile Screen 4",
        label = stringResource(
            R.string.lifestyle
        )
    )

    val navigationItemDiet = NavigationItem(
        icon = Icons.Outlined.Egg,
        contentDescription = "Profile Screen 2",
        label = stringResource(R.string.diet)
    )

    AppScaffold(topBar = {
        Column {
            AppTopBar(title = stringResource(R.string.profile_screen), onBack = onBack, actions = {
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            })

            val colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary
            )

            NavigationBar(
                containerColor = Color.Transparent, tonalElevation = 0.dp
            ) {
                val navigationItems = listOf(
                    navigationItemDetails,
                    navigationItemPhysique,
                    navigationItemHealth,
                    navigationItemLifestyle,
                    navigationItemDiet
                )

                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = content == index + 1,
                        onClick = { content = index + 1 },
                        icon = {
                            Icon(
                                item.icon, contentDescription = item.contentDescription
                            )
                        },
                        label = {
                            Text(
                                text = item.label, fontSize = 11.sp, maxLines = 1
                            )
                        },
                        colors = colors
                    )
                }
            }
        }
    }) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (content) {
                1 -> {
                    ContactLayout(basicDetails = userProfile.contact)
                }

                2 -> {
                    PhysiqueLayout(physique = userProfile.physique)
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

private data class NavigationItem(
    val icon: ImageVector,
    val contentDescription: String,
    val label: String,
)