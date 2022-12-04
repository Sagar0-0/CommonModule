package fit.asta.health.profile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fit.asta.health.profile.model.domain.Contact
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@Composable
fun ContactScreen(
    mainProfile: Contact,
    checkedState: MutableState<Boolean>,
) {

    var currentBottomSheet: BottomSheetType? by remember {
        mutableStateOf(null)
    }

    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val scope = rememberCoroutineScope()

    val closeSheet = {
        scope.launch { modalBottomSheetState.hide() }
    }

    val openSheet = {
        scope.launch { modalBottomSheetState.show() }
    }

    ModalBottomSheetLayout(modifier = Modifier.fillMaxSize(),
        sheetState = modalBottomSheetState,
        sheetContent = {
            Spacer(modifier = Modifier.height(1.dp))
            currentBottomSheet?.let {
                SheetLayout(closeSheet = {
                    closeSheet()
                }, bottomSheetType = it)
            }
        }) {

        ContactLayout(mainProfile = mainProfile, checkedState = checkedState, onClick = {
            currentBottomSheet = BottomSheetType.TYPE1
            openSheet()
        })

    }

}

enum class BottomSheetType {
    TYPE1, TYPE2, TYPE3
}

@Composable
fun Screen1(closeSheet: () -> Unit) {

    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)) {
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(text = "Bottom sheet type 1")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { closeSheet() }) {
                Text(text = "Close")
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }

}

@Composable
fun Screen2(closeSheet: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)) {
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(text = "Bottom sheet type 2")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { closeSheet() }) {
                Text(text = "Close")
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
fun Screen3(closeSheet: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)) {
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(text = "Bottom sheet type 2")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { closeSheet() }) {
                Text(text = "Close")
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
fun SheetLayout(
    bottomSheetType: BottomSheetType,
    closeSheet: () -> Unit,
) {

    when (bottomSheetType) {
        BottomSheetType.TYPE1 -> Screen1(closeSheet)
        BottomSheetType.TYPE2 -> Screen2(closeSheet)
        BottomSheetType.TYPE3 -> Screen3(closeSheet)
    }

}
