package fit.asta.health.tools.water.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.tools.sunlight.view.components.bottomsheet.expanded.ui.PracticeExpandedGridView


@Preview
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WaterBottomSheet() {

    val scaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = rememberBottomSheetState(
            BottomSheetValue.Collapsed))

    BottomSheetScaffold(sheetContent = { PracticeExpandedGridView() },
        scaffoldState = scaffoldState) {
        WaterHomeScreen()
    }

}