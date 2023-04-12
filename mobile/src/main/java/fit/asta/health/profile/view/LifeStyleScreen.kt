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
import fit.asta.health.profile.model.domain.LifeStyle
import fit.asta.health.profile.view.LifeStyleBottomSheetType.*
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun LifeStyleScreen(
    lifeStyle: LifeStyle,
) {

    var currentBottomSheet: LifeStyleBottomSheetType? by remember {
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
                LifeStyleBottomSheetLayout(sheetLayout = it, closeSheet = { closeSheet() })
            }
        }) {

        LifeStyleLayout(lifeStyle = lifeStyle)
    }

}

enum class LifeStyleBottomSheetType {
    SLEEPSCHEDULE, WORKSTYLE, WORKSCHEDULE, PHYSICALLYACTIVE, CURRENTACTIVITIES, PREFERREDACTIVITIES, LIFESTYLETARGETS
}

@Composable
fun LifeStyleBottomSheetLayout(
    sheetLayout: LifeStyleBottomSheetType,
    closeSheet: () -> Unit,
) {
    when (sheetLayout) {
        SLEEPSCHEDULE -> Screen1(closeSheet)
        WORKSTYLE -> Screen1(closeSheet)
        WORKSCHEDULE -> Screen1(closeSheet)
        PHYSICALLYACTIVE -> Screen1(closeSheet)
        CURRENTACTIVITIES -> Screen1(closeSheet)
        PREFERREDACTIVITIES -> Screen1(closeSheet)
        LIFESTYLETARGETS -> Screen1(closeSheet)
    }
}