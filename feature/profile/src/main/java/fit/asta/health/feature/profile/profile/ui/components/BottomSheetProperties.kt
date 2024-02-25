package fit.asta.health.feature.profile.profile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.common.utils.SnapshotStateListSaver
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.profile.remote.model.UserProperties
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppUiStateHandler
import fit.asta.health.designsystem.molecular.background.AppModalBottomSheet
import fit.asta.health.designsystem.molecular.button.AppIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetProperties(
    isVisible: Boolean,
    sheetState: SheetState,
    currentList: List<UserProperties>,
    onDismissRequest: () -> Unit,
    userPropertiesState: UiState<List<UserProperties>>,
    onSave: (List<UserProperties>) -> Unit
) {
    val selectedItems = rememberSaveable(
        isVisible,
        saver = SnapshotStateListSaver()
    ) {
        currentList.toMutableStateList()
    }
    val (searchQuery, onQueryChange) = rememberSaveable(isVisible) {
        mutableStateOf("")
    }

    AppModalBottomSheet(
        sheetVisible = isVisible,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        AppUiStateHandler(
            uiState = userPropertiesState,
            isScreenLoading = false,
            onErrorMessage = onDismissRequest
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level2),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
            ) {
                AppIconButton(
                    modifier = Modifier.align(Alignment.End),
                    imageVector = Icons.Default.Save,
                    onClick = { onSave(selectedItems.toList()) }
                )

                SearchBar(
                    searchQuery = searchQuery,
                    onSearchQueryChange = onQueryChange,
                )

                ChipRow(
                    userProperties = it,
                    searchQuery = searchQuery,
                    isItemSelected = { prop ->
                        selectedItems.contains(prop)
                    },
                    onAdd = { prop ->
                        selectedItems.add(prop)
                    },
                    onRemove = { prop ->
                        selectedItems.remove(prop)
                    }
                )
            }
        }
    }
}