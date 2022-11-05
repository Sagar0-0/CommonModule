package fit.asta.health.tools.water.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WaterBottomSheet(paddingValues: PaddingValues) {

    val scaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = rememberBottomSheetState(
            BottomSheetValue.Collapsed))

    BottomSheetScaffold(sheetContent = { WaterBottomSheetGridView(paddingValues) },
        scaffoldState = scaffoldState) {
        WaterHomeScreen(paddingValues)
    }

}