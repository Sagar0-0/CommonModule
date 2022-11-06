package fit.asta.health.tools.water.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WaterBottomSheet(paddingValues: PaddingValues) {

    val scaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = rememberBottomSheetState(
            BottomSheetValue.Collapsed))

    BottomSheetScaffold(sheetContent = { WaterBottomSheetGridView() },
        sheetPeekHeight = 32.dp,
        scaffoldState = scaffoldState) {
        WaterHomeScreen(paddingValues)
    }

}