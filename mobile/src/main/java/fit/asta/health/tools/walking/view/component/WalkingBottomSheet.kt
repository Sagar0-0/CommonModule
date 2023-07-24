package fit.asta.health.tools.walking.view.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalkingBottomSheet(paddingValues: PaddingValues) {

    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(sheetContent = { WalkingBottomSheetGridView() },
        sheetPeekHeight = 40.dp,
        scaffoldState = scaffoldState) {
        WalkingTypeLayout(paddingValues = paddingValues)
    }
}