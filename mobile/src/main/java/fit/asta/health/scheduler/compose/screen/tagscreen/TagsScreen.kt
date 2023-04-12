package fit.asta.health.scheduler.compose.screen.tagscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.NavigateBefore
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import fit.asta.health.scheduler.compose.components.CustomTagBottomSheetLayout
import fit.asta.health.scheduler.compose.components.SwipeDemo
import fit.asta.health.scheduler.compose.screen.tagscreen.TagCreateBottomSheetTypes.*
import fit.asta.health.scheduler.viewmodel.SchedulerViewModel
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
        scope.launch { modalBottomSheetState.hide() }
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
        Scaffold(content = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(it)
                    .verticalScroll(rememberScrollState())
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                for (i in 1..10) {
                    SwipeDemo()
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
