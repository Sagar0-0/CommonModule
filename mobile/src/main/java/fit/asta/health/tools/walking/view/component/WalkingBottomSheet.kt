package fit.asta.health.tools.walking.view.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WalkingBottomSheet(paddingValues: PaddingValues) {

    val scaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = rememberBottomSheetState(
            BottomSheetValue.Collapsed))

    BottomSheetScaffold(sheetContent = { WalkingBottomSheetGridView() },
        sheetPeekHeight = 40.dp,
        scaffoldState = scaffoldState) {
        WalkingTypeLayout(paddingValues = paddingValues)
    }

}