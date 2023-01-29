package fit.asta.health.tools.water.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fit.asta.health.tools.water.viewmodel.WaterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalMaterialApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun WaterBottomSheet(paddingValues: PaddingValues) {

    val scaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = rememberBottomSheetState(
            BottomSheetValue.Collapsed))

    BottomSheetScaffold(
        sheetContent = { WaterBottomSheetGridView(
           scaffoldState =  scaffoldState
        ) },
        sheetPeekHeight = 160.dp,
        scaffoldState = scaffoldState,
    ) {
        WaterHomeScreen(paddingValues)
    }

}