package fit.asta.health.feature.profile.profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppUiStateHandler
import fit.asta.health.designsystem.molecular.background.AppModalBottomSheet
import fit.asta.health.feature.profile.create.view.components.CreateProfileTwoButtonLayout
import fit.asta.health.feature.profile.profile.ui.state.UserProfileState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthScreen(
    userProfileState: UserProfileState
) {
    val bottomSheetState = rememberModalBottomSheetState()
    val bottomSheetVisible = rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier)
        userProfileState.healthScreenState.bottomSheets.forEachIndexed { index, type ->
            BottomSheetPickerCardItem(
                name = type.name,
                list = type.list,
                onRemove = {
                    userProfileState.healthScreenState.removeProperty(it)
                }
            ) {
                userProfileState
                    .healthScreenState
                    .openHealthBottomSheet(
                        bottomSheetState,
                        index,
                        bottomSheetVisible
                    )
            }
        }
        CreateProfileTwoButtonLayout(userProfileState)
        Spacer(modifier = Modifier)
        AppModalBottomSheet(
            sheetVisible = bottomSheetVisible.value,
            sheetState = bottomSheetState,
            dragHandle = null,
            onDismissRequest = {
                userProfileState.closeBottomSheet(bottomSheetState, bottomSheetVisible)
            }
        ) {
            AppUiStateHandler(
                uiState = userProfileState.healthScreenState.healthPropertiesState,
                isScreenLoading = false,
                onErrorMessage = {
                    userProfileState.closeBottomSheet(bottomSheetState, bottomSheetVisible)
                }
            ) {
                HealthPropertiesSearchSheet(
                    healthProperties = it,
                    searchQuery = userProfileState.bottomSheetSearchQuery,
                    onSearchQueryChange = { query ->
                        userProfileState.bottomSheetSearchQuery = query
                    },
                    isItemSelected = { prop ->
                        userProfileState.healthScreenState.isPropertySelected(prop)
                    },
                    onAdd = { prop ->
                        userProfileState.healthScreenState.addProperty(prop)
                    },
                    onRemove = { prop ->
                        userProfileState.healthScreenState.removeProperty(prop)
                    },
                )
            }
        }
    }
}
