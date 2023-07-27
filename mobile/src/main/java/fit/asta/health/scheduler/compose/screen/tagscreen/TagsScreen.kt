package fit.asta.health.scheduler.compose.screen.tagscreen

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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.common.ui.components.*
import fit.asta.health.scheduler.compose.components.CustomTagBottomSheetLayout
import fit.asta.health.scheduler.compose.components.SwipeDemo
import fit.asta.health.scheduler.compose.screen.tagscreen.TagCreateBottomSheetTypes.CUSTOMTAGCREATION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(device = "id:pixel_6_pro", showSystemUi = true, showBackground = true)
fun TagsScreen(
    onNavBack: () -> Unit = {},
    tagsEvent: (TagsEvent) -> Unit = {},
    tagsUiState: TagsUiState = TagsUiState()
) {

    var currentBottomSheet: TagCreateBottomSheetTypes? by remember {
        mutableStateOf(null)
    }

    val bottomSheetState = androidx.compose.material3.rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    val closeSheet = {
        scope.launch { bottomSheetState.hide() }
    }

    val openSheet = {
        scope.launch { bottomSheetState.show() }
    }
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    AppScaffold(snackBarHostState = snackBarHostState,
        content = {
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .padding(it)
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                items(tagsUiState.tagsList) { data ->
                    SwipeDemo(data = data, onSwipe = {
                        tagsEvent(TagsEvent.DeleteTag(data))
                        coroutineScope.launch {
                            val snackBarResult = snackBarHostState.showSnackbar(
                                message = "Deleted ${data.meta.name}",
                                actionLabel = "Undo",
                                duration = SnackbarDuration.Long
                            )
                            when (snackBarResult) {
                                SnackbarResult.ActionPerformed -> {
                                    tagsEvent(TagsEvent.UndoTag(data))
                                }

                                else -> {}
                            }
                        }
                    }, onClick = {
                        tagsEvent(TagsEvent.SelectedTag(data))
                        onNavBack()
                    })
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    currentBottomSheet = CUSTOMTAGCREATION
                    openSheet()
                },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                modifier = Modifier.size(50.dp),
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        },
        topBar = { AppTopBar(title = "Tags", onBack = onNavBack) })

    CustomModelBottomSheet(
        targetState = bottomSheetState.isVisible,
        sheetState = bottomSheetState,
        content = {
            currentBottomSheet?.let {
                TagCreateBtmSheetLayout(
                    sheetLayout = it,
                    closeSheet = { closeSheet() },
                    tagsEvent = tagsEvent
                )
            }
        },
        dragHandle = {},
        onClose = { closeSheet() }
    )
}

enum class TagCreateBottomSheetTypes {
    CUSTOMTAGCREATION
}

@Composable
fun TagCreateBtmSheetLayout(
    tagsEvent: (TagsEvent) -> Unit,
    sheetLayout: TagCreateBottomSheetTypes,
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
    when (sheetLayout) {
        CUSTOMTAGCREATION -> {
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
    }

}
