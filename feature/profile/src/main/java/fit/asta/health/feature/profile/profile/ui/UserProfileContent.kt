package fit.asta.health.feature.profile.profile.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.DialogData
import fit.asta.health.designsystem.molecular.ImageCropperScreen
import fit.asta.health.designsystem.molecular.ShowCustomConfirmationDialog
import fit.asta.health.designsystem.molecular.background.AppNavigationBar
import fit.asta.health.designsystem.molecular.background.AppNavigationBarItem
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.pager.AppHorizontalPager
import fit.asta.health.feature.profile.profile.utils.ProfileNavigationScreen
import fit.asta.health.resources.strings.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun UserProfileContent(
    userProfileState: UserProfileState,
    isScreenLoading: Boolean = false,
) {

    AppScaffold(
        isScreenLoading = isScreenLoading,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.profile_screen),
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
                when (userProfileState.profileDataPages[userProfileState.currentPageIndex]) {
                    ProfileNavigationScreen.BASIC -> {
                        DetailsScreen(userProfileState)
                    }

                    ProfileNavigationScreen.Physique -> {
                        PhysiqueScreen(userProfileState)
                    }

                    ProfileNavigationScreen.Health -> {
//                        HealthCreateScreen(userProfileState)
                        HealthScreen(userProfileState)
                    }

                    ProfileNavigationScreen.Lifestyle -> {
                        LifestyleScreen(userProfileState)
                    }

                    ProfileNavigationScreen.Diet -> {
                        DietScreen(userProfileState)
                    }
                }
            }

        }

        if (userProfileState.isConfirmDialogVisible) {
            ShowCustomConfirmationDialog(
                onDismiss = {
                    userProfileState.isConfirmDialogVisible =
                        !userProfileState.isConfirmDialogVisible
                },
                onNegativeClick = {
                    userProfileState.forceBackPress()
                },
                onPositiveClick = {
                    userProfileState.saveData()
                },
                dialogData = DialogData(
                    dialogTitle = stringResource(R.string.discard_profile_creation),
                    dialogDesc = stringResource(R.string.dialogDesc_profile_creation),
                    negTitle = stringResource(R.string.negativeTitle_profile_creation),
                    posTitle = stringResource(R.string.positiveTitle_profile_creation)
                )
            )
        }
    }

    ImageCropperScreen(
        modifier = Modifier.fillMaxSize(),
        visible = userProfileState.isImageCropperVisible,
        uri = userProfileState.profileImageLocalUri,
        onCropClick = { croppedImage ->
            userProfileState.profileImageLocalUri = croppedImage
            userProfileState.isImageCropperVisible = false
        }
    )
}