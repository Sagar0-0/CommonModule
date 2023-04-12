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
import fit.asta.health.profile.model.domain.Diet
import fit.asta.health.profile.view.DietBottomSheetType.*
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun DietScreen(
    diet: Diet,
) {

    var currentBottomSheet: DietBottomSheetType? by remember {
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
                DietBottomSheetLayout(sheetLayout = it, closeSheet = { closeSheet() })
            }
        }) {

        DietLayout(diet = diet)

    }

}

enum class DietBottomSheetType {
    FOODALLERGIES, CUISINES, FOODRES
}

@Composable
fun DietBottomSheetLayout(
    sheetLayout: DietBottomSheetType,
    closeSheet: () -> Unit,
) {
    when (sheetLayout) {
        FOODALLERGIES -> Screen1(closeSheet)
        CUISINES -> Screen1(closeSheet)
        FOODRES -> Screen1(closeSheet)
    }
}
