package fit.asta.health.feature.profile.show

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.profile.remote.model.UserProfileResponse
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.DialogData
import fit.asta.health.designsystem.molecular.ShowCustomConfirmationDialog
import fit.asta.health.designsystem.molecular.background.AppNavigationBar
import fit.asta.health.designsystem.molecular.background.AppNavigationBarItem
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.pager.AppHorizontalPager
import fit.asta.health.feature.profile.create.view.DetailsCreateScreen
import fit.asta.health.feature.profile.create.view.DietCreateScreen
import fit.asta.health.feature.profile.create.view.HealthCreateScreen
import fit.asta.health.feature.profile.create.view.LifeStyleCreateScreen
import fit.asta.health.feature.profile.create.view.PhysiqueCreateScreen
import fit.asta.health.feature.profile.profile.ui.ProfileNavigationScreen
import fit.asta.health.feature.profile.profile.ui.UserProfileState
import fit.asta.health.resources.strings.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun UserProfileContent(
    userProfileState: UserProfileState,
    userProfileResponse: UserProfileResponse,
    isScreenLoading: Boolean = false,
    onBack: () -> Unit,
) {

    var showCustomDialogWithResult by remember { mutableStateOf(false) }

    AppScaffold(
        isScreenLoading = isScreenLoading,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.profile_screen),
                onBack = {
                    showCustomDialogWithResult = !showCustomDialogWithResult
                }
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
                userProfileState.profileDataPages.forEachIndexed { index, item ->
                    AppNavigationBarItem(
                        selected = userProfileState.currentPageIndex == index,
                        onClick = {
                            userProfileState.updateCurrentPageIndex(index)
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
                        DetailsCreateScreen {
                            userProfileState.updateCurrentPageIndex(userProfileState.currentPageIndex + 1)
                        }
                    }

                    ProfileNavigationScreen.Physique -> {
                        PhysiqueCreateScreen(
                            eventNext = {
                                userProfileState.updateCurrentPageIndex(userProfileState.currentPageIndex + 1)
                            },
                            eventPrevious = {
                                userProfileState.updateCurrentPageIndex(userProfileState.currentPageIndex - 1)
                            },
                        )
                    }

                    ProfileNavigationScreen.Health -> {
                        HealthCreateScreen(
                            eventNext = {
                                userProfileState.updateCurrentPageIndex(userProfileState.currentPageIndex + 1)
                            },
                            eventPrevious = {
                                userProfileState.updateCurrentPageIndex(userProfileState.currentPageIndex - 1)
                            },
                        )
                    }

                    ProfileNavigationScreen.Lifestyle -> {
                        LifeStyleCreateScreen(
                            eventNext = {
                                userProfileState.updateCurrentPageIndex(userProfileState.currentPageIndex + 1)
                            },
                            eventPrevious = {
                                userProfileState.updateCurrentPageIndex(userProfileState.currentPageIndex - 1)
                            },
                        )
                    }

                    ProfileNavigationScreen.Diet -> {
                        DietCreateScreen(
                            eventPrevious = {
                                userProfileState.updateCurrentPageIndex(userProfileState.currentPageIndex - 1)
                            },
                            navigateBack = {
                                userProfileState.onBackPressed()
                            }
                        )
                    }
                }
            }

        }

        if (userProfileState.isConfirmDialogVisible) {
            ShowCustomConfirmationDialog(
                onDismiss = { showCustomDialogWithResult = !showCustomDialogWithResult },
                onNegativeClick = onBack,
                onPositiveClick = { showCustomDialogWithResult = !showCustomDialogWithResult },
                dialogData = DialogData(
                    dialogTitle = stringResource(R.string.discard_profile_creation),
                    dialogDesc = stringResource(R.string.dialogDesc_profile_creation),
                    negTitle = stringResource(R.string.negativeTitle_profile_creation),
                    posTitle = stringResource(R.string.positiveTitle_profile_creation)
                ),
            )
        }
    }
}