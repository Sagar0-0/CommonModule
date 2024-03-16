package fit.asta.health.feature.profile.profile.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.pager.AppHorizontalPager
import fit.asta.health.designsystem.molecular.tab.AppTab
import fit.asta.health.designsystem.molecular.tab.AppTabRow
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.feature.profile.profile.ui.components.ProfileCompletionBar
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
            Column(modifier = Modifier.fillMaxWidth()) {
                AppTopBar(
                    title = stringResource(R.string.profile),
                    onBack = {
                        userProfileState.onBackPressed()
                    }
                )
                ProfileCompletionBar(progress = userProfileState.profileCompletePercentage)
            }
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
                    AppTabRow(
                        selectedTabIndex = userProfileState.currentPageIndex,
                    ) {
                        AppTab(
                            selected = userProfileState.currentPageIndex == index,
                            onClick = {
                                userProfileState.currentPageIndex = index
                            },
                            selectedContentColor = AppTheme.colors.primary
                        ) {
                            AppIcon(imageVector = item.icon)
                            CaptionTexts.Level2(text = item.labelId.toStringFromResId())
                        }
                    }
                }
            }

            AppHorizontalPager(
                pagerState = userProfileState.pagerState,
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
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.surface),
        visible = userProfileState.basicDetailScreenState.isImageCropperVisible,
        uri = userProfileState.basicDetailScreenState.profileImageLocalUri,
        onCropClick = { croppedImage ->
            userProfileState.basicDetailScreenState.saveProfileImage(croppedImage)
        }
    )
}