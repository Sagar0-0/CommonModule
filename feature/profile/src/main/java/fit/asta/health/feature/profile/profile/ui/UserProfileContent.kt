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
import fit.asta.health.common.utils.SubmitProfileResponse
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.profile.remote.model.UserProperties
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.DialogData
import fit.asta.health.designsystem.molecular.ImageCropperScreen
import fit.asta.health.designsystem.molecular.ShowCustomConfirmationDialog
import fit.asta.health.designsystem.molecular.background.AppNavigationBar
import fit.asta.health.designsystem.molecular.background.AppNavigationBarItem
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.pager.AppHorizontalPager
import fit.asta.health.feature.profile.profile.ui.state.UserProfileState
import fit.asta.health.feature.profile.profile.utils.ProfileNavigationScreen
import fit.asta.health.resources.strings.R
import fit.asta.health.subscription.remote.model.UserSubscribedPlan
import fit.asta.health.wallet.remote.model.WalletResponse

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun UserProfileContent(
    userProfileState: UserProfileState,
    submitProfileState: UiState<SubmitProfileResponse>,
    walletDataState: UiState<WalletResponse>,
    subscriptionDataState: UiState<UserSubscribedPlan>,
    userPropertiesState: UiState<List<UserProperties>>
) {
    AppScaffold(
        isScreenLoading = submitProfileState is UiState.Loading,
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
                when (userProfileState.profileDataPages[it]) {
                    ProfileNavigationScreen.BASIC -> {
                        BasicDetailsScreen(
                            userProfileState = userProfileState,
                            walletDataState = walletDataState,
                            subscriptionDataState = subscriptionDataState,
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
        uri = userProfileState.basicDetailScreenState.profileImageLocalUri,
        onCropClick = { croppedImage ->
            userProfileState.basicDetailScreenState.profileImageLocalUri = croppedImage
            userProfileState.isImageCropperVisible = false
        }
    )
}