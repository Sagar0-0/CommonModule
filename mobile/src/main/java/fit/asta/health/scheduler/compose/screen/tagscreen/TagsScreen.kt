package fit.asta.health.scheduler.compose.screen.tagscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.NavigateBefore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import fit.asta.health.scheduler.compose.components.CustomTagBottomSheetLayout
import fit.asta.health.scheduler.compose.components.SwipeDemo
import fit.asta.health.scheduler.compose.screen.tagscreen.TagCreateBottomSheetTypes.CUSTOMTAGCREATION
import fit.asta.health.scheduler.viewmodel.SchedulerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun TagsScreen(navController: NavHostController, schedulerViewModel: SchedulerViewModel) {

    var currentBottomSheet: TagCreateBottomSheetTypes? by remember {
        mutableStateOf(null)
    }

    var modalBottomSheetValue by remember {
        mutableStateOf(ModalBottomSheetValue.Hidden)
    }

    val modalBottomSheetState = rememberModalBottomSheetState(modalBottomSheetValue)

    val scope = rememberCoroutineScope()

    val closeSheet = {
        scope.launch { modalBottomSheetState.hide()
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
                    closeSheet = { closeSheet() }
                )
            }
        }) {
        val tagsUiState=  schedulerViewModel.tagsUiState.value
        val scaffoldState: ScaffoldState = rememberScaffoldState()
        val coroutineScope: CoroutineScope = rememberCoroutineScope()
        Scaffold(scaffoldState = scaffoldState,content = {
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .padding(it)
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                items(tagsUiState.tagsList){data->
                    SwipeDemo(data=data,onSwipe = {
                        val deletedTag =data
                        schedulerViewModel.TagsEvent(TagsEvent.DeleteTag(deletedTag))
                        coroutineScope.launch {
                            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                                message = "Deleted ${deletedTag.meta.name}",
                                actionLabel = "Undo",
                                duration = SnackbarDuration.Long
                            )
                            when (snackbarResult) {
                                SnackbarResult.ActionPerformed -> {
                                    schedulerViewModel.TagsEvent(TagsEvent.UndoTag(deletedTag))
                                }
                                else -> {}
                            }
                        }
                    }, onClick = {
                        schedulerViewModel.TagsEvent(TagsEvent.SelectedTag(data))
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
                IconButton(onClick = { navController.popBackStack() }) {
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
    sheetLayout: TagCreateBottomSheetTypes,
    closeSheet: () -> Unit,

    ) {

    when (sheetLayout) {
        CUSTOMTAGCREATION -> {
            Column(modifier = Modifier.fillMaxWidth()) {
                CustomTagBottomSheetLayout(onNavigateBack = closeSheet)
            }
        }
    }

}
