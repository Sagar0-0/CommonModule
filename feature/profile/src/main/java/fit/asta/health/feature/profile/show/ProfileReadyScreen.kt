package fit.asta.health.feature.profile.show

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Egg
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
import fit.asta.health.data.profile.remote.model.UserProfile
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.feature.profile.show.view.DietLayout
import fit.asta.health.feature.profile.show.view.HealthLayout
import fit.asta.health.feature.profile.show.view.LifeStyleLayout
import fit.asta.health.feature.profile.show.view.PhysiqueLayout
import fit.asta.health.resources.strings.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileReadyScreen(
    userProfile: UserProfile,
    onBack: () -> Unit,
    onEdit: () -> Unit,
) {
    var content by remember { mutableIntStateOf(1) }

    val navigationItemDetails = NavigationItem(
        icon = Outlined.AccountCircle,
        contentDescription = "Profile Screen 1",
        label = stringResource(id = R.string.details),
    )

    val navigationItemPhysique = NavigationItem(
        icon = Outlined.Face, contentDescription = "Profile Screen 2", label = stringResource(
            R.string.physique
        )
    )

    val navigationItemHealth = NavigationItem(
        icon = Outlined.Favorite, contentDescription = "Profile Screen 3", label = stringResource(
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
        icon = Outlined.Egg,
        contentDescription = "Profile Screen 2",
        label = stringResource(R.string.diet)
    )

    AppScaffold(topBar = {
        Column {
            AppTopBar(title = stringResource(R.string.profile_screen), onBack = onBack, actions = {
                AppIconButton(
                    imageVector = Icons.Filled.Edit,
                    iconDesc = "Profile Edit",
                    iconTint = AppTheme.colors.primary,
                    onClick = onEdit
                )
            })

            val colors = NavigationBarItemDefaults.colors(
                selectedIconColor = AppTheme.colors.primary
            )

            NavigationBar(
                containerColor = Color.Transparent,
                tonalElevation = 0.dp,
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
                            AppIcon(
                                imageVector = item.icon,
                                contentDescription = item.contentDescription
                            )
                        },
                        label = { CaptionTexts.Level5(text = item.label) },
                        colors = colors
                    )
                }
            }
        }
    }) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (content) {
                1 -> {
//                    ContactLayout(basicDetails = userProfile.contact)
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