package fit.asta.health.profile.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.profile.model.domain.Health
import fit.asta.health.profile.view.HealthBottomSheetType.*
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun HealthScreen(
    health: Health,
    editState: MutableState<Boolean>,
) {

    var currentBottomSheet: HealthBottomSheetType? by remember {
        mutableStateOf(null)
    }

    var modalBottomSheetValue by remember {
        mutableStateOf(ModalBottomSheetValue.Hidden)
    }

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = modalBottomSheetValue
    )

    val scope = rememberCoroutineScope()

    val closeSheet = {
        scope.launch { modalBottomSheetState.hide() }
    }

    val openSheet = {
        scope.launch {
            modalBottomSheetState.show()
            modalBottomSheetValue = ModalBottomSheetValue.Expanded
        }
    }

    ModalBottomSheetLayout(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        sheetState = modalBottomSheetState,
        sheetContent = {
            Spacer(modifier = Modifier.height(1.dp))
            currentBottomSheet?.let {
                HealthSheetLayout(sheetLayout = it, closeSheet = { closeSheet() })
            }
        },
        sheetShape = MaterialTheme.shapes.medium
    ) {

        HealthLayout(health = health, editState = editState, onAilments = {
            currentBottomSheet = AILMENTS
            openSheet()
        }, onMedications = {
            currentBottomSheet = MEDICATIONS
            openSheet()
        }, onHealthTargets = {
            currentBottomSheet = HEALTHTARGETS
            openSheet()
        })

    }

}

enum class HealthBottomSheetType {
    AILMENTS, MEDICATIONS, HEALTHTARGETS
}

@Composable
fun HealthSheetLayout(
    sheetLayout: HealthBottomSheetType,
    closeSheet: () -> Unit,
) {

    when (sheetLayout) {
        AILMENTS -> Screen1(closeSheet)
        MEDICATIONS -> Screen1(closeSheet)
        HEALTHTARGETS -> Screen1(closeSheet)
    }

}
