package fit.asta.health.feature.profile.profile.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import fit.asta.health.common.utils.SubmitProfileResponse
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.profile.remote.model.UserProperties
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.ImageCropperScreen
import fit.asta.health.designsystem.molecular.background.AppNavigationBar
import fit.asta.health.designsystem.molecular.background.AppNavigationBarItem
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.pager.AppHorizontalPager
import fit.asta.health.feature.profile.profile.ui.state.UserProfileState
import fit.asta.health.feature.profile.profile.utils.ProfileNavigationScreen
import fit.asta.health.resources.strings.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserProfileContent(
    userProfileState: UserProfileState,
    submitProfileState: UiState<SubmitProfileResponse>,
    userPropertiesState: UiState<List<UserProperties>>
) {
    AppScaffold(
        isScreenLoading = submitProfileState is UiState.Loading,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.profile),
                onBack = {
                    userProfileState.onBackPressed()
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            AppNavigationBar(
                tonalElevation = AppTheme.elevation.level0,
            ) {
                userProfileState.profileDataPages.forEachIndexed { index, item ->
                    AppNavigationBarItem(
                        selected = userProfileState.currentPageIndex == index,
                        onClick = {
                            userProfileState.currentPageIndex = index
                        },
                        icon = item.icon,
                        label = item.labelId.toStringFromResId(),
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = if (userProfileState.currentPageIndex == index)
                                AppTheme.colors.primary
                            else
                                AppTheme.colors.onSecondaryContainer
                        )
                    )
                }
            }

            AppHorizontalPager(
                pagerState = userProfileState.pagerState,
                userScrollEnabled = false,
                enableCarouselSizeTransition = false
            ) {
                when (userProfileState.profileDataPages[it]) {
                    ProfileNavigationScreen.BASIC -> {
                        BasicDetailsScreen(
                            userProfileState = userProfileState,
                        )
                    }

                    ProfileNavigationScreen.Physique -> {
                        PhysiqueScreen(userProfileState)
                    }

                    ProfileNavigationScreen.Health -> {
                        HealthScreen(userProfileState, userPropertiesState)
                    }

                    ProfileNavigationScreen.Lifestyle -> {
                        LifestyleScreen(userProfileState, userPropertiesState)
                    }

                    ProfileNavigationScreen.Diet -> {
                        DietScreen(userProfileState, userPropertiesState)
                    }
                }
            }
        }
    }

    ImageCropperScreen(
        modifier = Modifier.fillMaxSize(),
        visible = userProfileState.basicDetailScreenState.isImageCropperVisible,
        uri = userProfileState.basicDetailScreenState.profileImageLocalUri,
        onCropClick = { croppedImage ->
            userProfileState.basicDetailScreenState.saveProfileImage(croppedImage)
        }
    )
}