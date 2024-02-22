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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.profile.remote.model.UserProperties
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppUiStateHandler
import fit.asta.health.designsystem.molecular.background.AppModalBottomSheet
import fit.asta.health.feature.profile.profile.ui.components.BottomSheetPickerCardItem
import fit.asta.health.feature.profile.profile.ui.components.CreateProfileTwoButtonLayout
import fit.asta.health.feature.profile.profile.ui.components.PropertiesSearchSheet
import fit.asta.health.feature.profile.profile.ui.state.UserProfileState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietScreen(
    userProfileState: UserProfileState,
    userPropertiesState: UiState<List<UserProperties>>
) {
    val bottomSheetState = rememberModalBottomSheetState()
    val bottomSheetVisible = rememberSaveable { mutableStateOf(false) }
    val currentBottomSheetIndex = rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier)
        userProfileState.dietScreenState.bottomSheets.forEachIndexed { index, type ->
            BottomSheetPickerCardItem(
                name = type.name,
                list = type.list,
                onRemove = {
                    type.list.remove(it)
                }
            ) {
                currentBottomSheetIndex.intValue = index
                userProfileState.dietScreenState.openPropertiesBottomSheet(
                    bottomSheetState,
                    currentBottomSheetIndex,
                    bottomSheetVisible
                )
            }
        }
        CreateProfileTwoButtonLayout(
            userProfileState,
            isLastButtonSubmit = true
        ) {
            userProfileState.saveData()
        }
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
                uiState = userPropertiesState,
                isScreenLoading = false,
                onErrorMessage = {
                    userProfileState.closeBottomSheet(bottomSheetState, bottomSheetVisible)
                }
            ) {
                PropertiesSearchSheet(
                    userProperties = it,
                    searchQuery = userProfileState.bottomSheetSearchQuery,
                    onSearchQueryChange = { query ->
                        userProfileState.bottomSheetSearchQuery = query
                    },
                    isItemSelected = { prop ->
                        userProfileState.dietScreenState.isPropertySelected(
                            currentBottomSheetIndex.intValue,
                            prop
                        )
                    },
                    onAdd = { prop ->
                        userProfileState.dietScreenState.addProperty(
                            currentBottomSheetIndex.intValue,
                            prop
                        )
                    },
                    onRemove = { prop ->
                        userProfileState.dietScreenState.removeProperty(
                            currentBottomSheetIndex.intValue,
                            prop
                        )
                    }
                )
            }
        }
    }
}