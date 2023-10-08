package fit.asta.health.feature.scheduler.ui.screen.tagscreen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fit.asta.health.data.scheduler.db.entity.TagEntity
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.components.*
import fit.asta.health.designsystem.components.generic.AppErrorScreen
import fit.asta.health.designsystem.molecular.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppFloatingActionButton
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.scheduler.ui.components.AlertDialogPopUp
import fit.asta.health.feature.scheduler.ui.components.CustomTagBottomSheetLayout
import fit.asta.health.feature.scheduler.ui.components.SwipeDemo
import kotlinx.coroutines.launch
import fit.asta.health.resources.strings.R as StringR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagsScreen(
    onNavBack: () -> Unit = {},
    tagsEvent: (TagsEvent) -> Unit = {},
    tagsList: SnapshotStateList<TagEntity>,
    customTagList: SnapshotStateList<TagEntity>
) {
    val bottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    val closeSheet = {
        scope.launch { bottomSheetState.hide() }
    }

    val openSheet = {
        scope.launch { bottomSheetState.show() }
    }
    var deleteDialog by rememberSaveable { mutableStateOf(false) }
    var deletedItem by remember { mutableStateOf<TagEntity?>(null) }
    if (deleteDialog) {
        AlertDialogPopUp(
            content = "Are you sure you want to delete this Tag?",
            actionButton = stringResource(id = StringR.string.delete),
            onDismiss = { deleteDialog = false },
            onDone = {
                deletedItem?.let { tagsEvent(TagsEvent.DeleteTag(it)) }
                deleteDialog = false
            })
    }
    val snackBarHostState = remember { SnackbarHostState() }
    AppScaffold(
        snackBarHostState = snackBarHostState,
        content = {
            if (tagsList.isEmpty()) {
                AppErrorScreen(onTryAgain = {
                    tagsEvent(TagsEvent.GetTag)
                }, isInternetError = false)
            } else {
                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .padding(it)
                        .background(color = AppTheme.colors.secondaryContainer)
                ) {
                    if (tagsList.isNotEmpty()) {
                        item {
                            TitleTexts.Level2(text = "Default Tag")
                        }
                        items(tagsList) { data ->
                            SwipeDemo(data = data, onSwipe = {}, delete = false,
                                onClick = {
                                    tagsEvent(TagsEvent.SelectedTag(data))
                                    onNavBack()
                                })
                        }
                    }
                    if (customTagList.isNotEmpty()) {
                        item {
                            TitleTexts.Level2(text = "User Tag")
                        }
                        items(customTagList) { data ->
                            SwipeDemo(data = data, onSwipe = {
                                deletedItem = data
                                deleteDialog = true
                            }, onClick = {
                                tagsEvent(TagsEvent.SelectedTag(data))
                                onNavBack()
                            })
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            AppFloatingActionButton(
                onClick = { openSheet() },
                shape = CircleShape,
                modifier = Modifier.size(50.dp),
            ) {
                AppIcon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        },
        topBar = {
            AppTopBar(
                title = stringResource(id = StringR.string.tags),
                onBack = onNavBack
            )
        })

    CustomModelBottomSheet(
        targetState = bottomSheetState.isVisible,
        sheetState = bottomSheetState,
        content = {
            TagCreateBtmSheetLayout(
                closeSheet = { closeSheet() },
                tagsEvent = tagsEvent
            )
        },
        dragHandle = {},
        onClose = { closeSheet() }
    )
}


@Composable
fun TagCreateBtmSheetLayout(
    tagsEvent: (TagsEvent) -> Unit,
    closeSheet: () -> Unit,
) {
    val selectedImage = remember {
        mutableStateOf("")
    }
    val label = remember {
        mutableStateOf("")
    }
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            selectedImage.value = uri.toString()
        }
    Column(modifier = Modifier.fillMaxWidth()) {
        CustomTagBottomSheetLayout(
            onNavigateBack = closeSheet,
            onImageSelect = { galleryLauncher.launch("image/*") },
            onValueChange = { label.value = it },
            onSave = {
                if (label.value.length < 2 && selectedImage.value.length < 2) {
                    return@CustomTagBottomSheetLayout
                }
                tagsEvent(
                    TagsEvent.UpdateTag(
                        label = label.value, url = selectedImage.value
                    )
                )
                closeSheet()
            },
            image = selectedImage.value
        )
    }

}
