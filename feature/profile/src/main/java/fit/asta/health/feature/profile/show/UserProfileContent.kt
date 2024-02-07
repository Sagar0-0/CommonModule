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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
import fit.asta.health.resources.strings.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun UserProfileContent(
    userProfileResponse: UserProfileResponse,
    isScreenLoading: Boolean = false,
    onBack: () -> Unit,
) {

    var showCustomDialogWithResult by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState {
        ProfileNavigationScreen.entries.size
    }
    val scope = rememberCoroutineScope()

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
                        DetailsCreateScreen {
                            scope.launch {
                                pagerState.animateScrollToPage(index + 1)
                            }
                        }
//                        ContactLayout(basicDetails = userProfileResponse.contact)
                    }

                    ProfileNavigationScreen.Physique -> {
                        PhysiqueCreateScreen(
                            eventNext = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index + 1)
                                }
                            },
                            eventPrevious = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index - 1)
                                }
                            },
                        )
//                        PhysiqueLayout(physique = userProfileResponse.physique)
                    }

                    ProfileNavigationScreen.Health -> {
                        HealthCreateScreen(
                            eventNext = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index + 1)
                                }
                            },
                            eventPrevious = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index - 1)
                                }
                            }
                        )
//                        HealthLayout(health = userProfileResponse.health)
                    }

                    ProfileNavigationScreen.Lifestyle -> {
                        LifeStyleCreateScreen(
                            eventNext = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index + 1)
                                }
                            },
                            eventPrevious = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index - 1)
                                }
                            },
                        )
//                        LifeStyleLayout(lifeStyle = userProfileResponse.lifeStyle)
                    }

                    ProfileNavigationScreen.Diet -> {
                        DietCreateScreen(
                            eventPrevious = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index - 1)
                                }
                            },
                            navigateBack = onBack
                        )
//                        DietLayout(diet = userProfileResponse.diet)
                    }
                }
            }

        }
        if (showCustomDialogWithResult) {
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