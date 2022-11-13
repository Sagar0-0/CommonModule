package fit.asta.health.tools.sunlight.view.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SunlightBottomSheet(paddingValues: PaddingValues) {

    val scaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = rememberBottomSheetState(
            BottomSheetValue.Collapsed))

    BottomSheetScaffold(sheetContent = { SunlightPracticeGridView() },
        sheetPeekHeight = 40.dp,
        scaffoldState = scaffoldState) {
        SunlightHomeScreenLayout(paddingValues)
    }

}

