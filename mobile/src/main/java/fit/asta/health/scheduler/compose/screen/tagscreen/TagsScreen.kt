package fit.asta.health.scheduler.compose.screen.tagscreen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.NavigateBefore
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.scheduler.compose.components.CustomTagBottomSheetLayout
import fit.asta.health.scheduler.compose.components.SwipeDemo
import fit.asta.health.scheduler.compose.screen.tagscreen.TagCreateBottomSheetTypes.CUSTOMTAGCREATION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
@Preview(device = "id:pixel_6_pro", showSystemUi = true, showBackground = true)
fun TagsScreen(
    onNavBack: () -> Unit = {},
    tagsEvent: (TagsEvent) -> Unit = {},
    tagsUiState: TagsUiState= TagsUiState()
) {

    var currentBottomSheet: TagCreateBottomSheetTypes? by remember {
        mutableStateOf(null)
    }

    var modalBottomSheetValue by remember {
        mutableStateOf(ModalBottomSheetValue.Hidden)
    }

    val modalBottomSheetState = rememberModalBottomSheetState(modalBottomSheetValue)

    val scope = rememberCoroutineScope()

    val closeSheet = {
        scope.launch {
            modalBottomSheetState.hide()
            if (modalBottomSheetValue == ModalBottomSheetValue.Expanded) {
                modalBottomSheetValue = ModalBottomSheetValue.Hidden
            }
        }
    }

    val openSheet = {
        scope.launch {
            modalBottomSheetState.show()
            if (modalBottomSheetValue == ModalBottomSheetValue.Hidden) {
                modalBottomSheetValue = ModalBottomSheetValue.Expanded
            }
        }
    }
    ModalBottomSheetLayout(modifier = Modifier
        .fillMaxSize()
        .wrapContentHeight(),
        sheetState = modalBottomSheetState,
        sheetContent = {
            Spacer(modifier = Modifier.height(1.dp))
            currentBottomSheet?.let {
                TagCreateBtmSheetLayout(
                    sheetLayout = it,
                    closeSheet = { closeSheet() },
                    tagsEvent = tagsEvent
                )
            }
        }) {

        val scaffoldState: ScaffoldState = rememberScaffoldState()
        val coroutineScope: CoroutineScope = rememberCoroutineScope()
        Scaffold(scaffoldState = scaffoldState, content = {
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .padding(it)
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                items(tagsUiState.tagsList) { data ->
                    SwipeDemo(data = data, onSwipe = {
                        val deletedTag = data
                        tagsEvent(TagsEvent.DeleteTag(deletedTag))
                        coroutineScope.launch {
                            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                                message = "Deleted ${deletedTag.meta.name}",
                                actionLabel = "Undo",
                                duration = SnackbarDuration.Long
                            )
                            when (snackbarResult) {
                                SnackbarResult.ActionPerformed -> {
                                    tagsEvent(TagsEvent.UndoTag(deletedTag))
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
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    currentBottomSheet = CUSTOMTAGCREATION
                    openSheet()
                },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                modifier = Modifier.size(80.dp),
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }, topBar = {
            TopAppBar(title = {
                Text(
                    text = "Tags",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp
                )
            }, navigationIcon = {
                IconButton(onClick = onNavBack) {
                    Icon(
                        Icons.Outlined.NavigateBefore,
                        "back",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            })
        })
    }

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
