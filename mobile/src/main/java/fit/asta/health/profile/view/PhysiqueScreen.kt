package fit.asta.health.profile.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.profile.model.domain.Physique
import fit.asta.health.profile.view.PhysiqueBottomSheetType.*
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun PhysiqueScreen(
    m: Physique,
    checkedState: MutableState<Boolean>,
) {

    var currentBottomSheet: PhysiqueBottomSheetType? by remember {
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

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = modalBottomSheetState,
        sheetContent = {
            Spacer(modifier = Modifier.height(1.dp))
            currentBottomSheet?.let {
                PhysiqueSheetLayout(sheetLayout = it, closeSheet = { closeSheet() })
            }
        }) {
        PhysiqueLayout(phy = m)
    }

}

enum class PhysiqueBottomSheetType {
    AGE, GENDER, HEIGHT, WEIGHT, BMI, PREGNANCYWEEK, BODYTYPE
}

@Composable
fun PhysiqueSheetLayout(
    sheetLayout: PhysiqueBottomSheetType,
    closeSheet: () -> Unit,
) {
    when (sheetLayout) {
        AGE -> Screen1(closeSheet)
        GENDER -> Screen1(closeSheet)
        HEIGHT -> Screen1(closeSheet)
        WEIGHT -> Screen1(closeSheet)
        BMI -> Screen1(closeSheet)
        PREGNANCYWEEK -> Screen1(closeSheet)
        BODYTYPE -> Screen1(closeSheet)
    }
}
