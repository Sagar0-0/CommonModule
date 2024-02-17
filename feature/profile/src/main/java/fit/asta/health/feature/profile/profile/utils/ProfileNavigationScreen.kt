package fit.asta.health.feature.profile.profile.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Egg
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.ui.graphics.vector.ImageVector
import fit.asta.health.resources.strings.R

enum class ProfileNavigationScreen(
    val icon: ImageVector,
    val contentDescription: String,
    val labelId: Int
) {
    BASIC(
        icon = Icons.Outlined.AccountCircle,
        contentDescription = "Profile Screen 1",
        labelId = R.string.details
    ),

    Physique(
        icon = Icons.Outlined.Face,
        contentDescription = "Profile Screen 2",
        labelId = R.string.physique
    ),

    Health(
        icon = Icons.Outlined.Favorite,
        contentDescription = "Profile Screen 3",
        labelId = R.string.health
    ),

    Lifestyle(
        icon = Icons.Default.Emergency,
        contentDescription = "Profile Screen 4",
        labelId = R.string.lifestyle
    ),

    Diet(
        icon = Icons.Outlined.Egg,
        contentDescription = "Profile Screen 2",
        labelId = R.string.diet
    )
}