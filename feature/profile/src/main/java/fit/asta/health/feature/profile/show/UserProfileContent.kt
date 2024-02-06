package fit.asta.health.feature.profile.show

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Egg
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.profile.remote.model.UserProfileResponse
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppNavigationBar
import fit.asta.health.designsystem.molecular.background.AppNavigationBarItem
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.pager.AppHorizontalPager
import fit.asta.health.feature.profile.show.view.ContactLayout
import fit.asta.health.feature.profile.show.view.DietLayout
import fit.asta.health.feature.profile.show.view.HealthLayout
import fit.asta.health.feature.profile.show.view.LifeStyleLayout
import fit.asta.health.feature.profile.show.view.PhysiqueLayout
import fit.asta.health.resources.strings.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun UserProfileContent(
    userProfileResponse: UserProfileResponse,
    isScreenLoading: Boolean = false,
    onBack: () -> Unit,
) {
    val pagerState = rememberPagerState {
        ProfileNavigationScreen.entries.size
    }
    val scope = rememberCoroutineScope()

    AppScaffold(
        isScreenLoading = isScreenLoading,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.profile_screen),
                onBack = onBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
        ) {

            AppNavigationBar(
                tonalElevation = AppTheme.elevation.level0,
            ) {
                ProfileNavigationScreen.entries.forEachIndexed { index, item ->
                    AppNavigationBarItem(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        icon = item.icon,
                        label = item.labelId.toStringFromResId(),
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = if (pagerState.currentPage == index)
                                AppTheme.colors.primary
                            else
                                AppTheme.colors.onSecondaryContainer
                        )
                    )
                }
            }

            AppHorizontalPager(
                pagerState = pagerState,
                userScrollEnabled = false,
                enableCarouselSizeTransition = false
            ) { index ->
                when (ProfileNavigationScreen.entries[index]) {
                    ProfileNavigationScreen.BASIC -> {
                        ContactLayout(basicDetails = userProfileResponse.contact)
                    }

                    ProfileNavigationScreen.Physique -> {
                        PhysiqueLayout(physique = userProfileResponse.physique)
                    }

                    ProfileNavigationScreen.Health -> {
                        HealthLayout(health = userProfileResponse.health)
                    }

                    ProfileNavigationScreen.Lifestyle -> {
                        LifeStyleLayout(lifeStyle = userProfileResponse.lifeStyle)
                    }

                    ProfileNavigationScreen.Diet -> {
                        DietLayout(diet = userProfileResponse.diet)
                    }
                }
            }

        }
    }
}

enum class ProfileNavigationScreen(
    val icon: ImageVector,
    val contentDescription: String,
    val labelId: Int
) {
    BASIC(
        icon = Outlined.AccountCircle,
        contentDescription = "Profile Screen 1",
        labelId = R.string.details
    ),

    Physique(
        icon = Outlined.Face,
        contentDescription = "Profile Screen 2",
        labelId = R.string.physique
    ),

    Health(
        icon = Outlined.Favorite,
        contentDescription = "Profile Screen 3",
        labelId = R.string.health
    ),

    Lifestyle(
        icon = Icons.Default.Emergency,
        contentDescription = "Profile Screen 4",
        labelId = R.string.lifestyle
    ),

    Diet(
        icon = Outlined.Egg,
        contentDescription = "Profile Screen 2",
        labelId = R.string.diet
    )
}