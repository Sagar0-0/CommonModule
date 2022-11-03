package fit.asta.health.tools.sunlight.view.components.bottomsheet

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.tools.sunlight.view.components.SunlightLayout
import fit.asta.health.tools.sunlight.view.components.bottomsheet.collapsed.SheetCollapsed
import fit.asta.health.tools.sunlight.view.components.bottomsheet.collapsed.currentFraction
import fit.asta.health.tools.sunlight.view.components.bottomsheet.collapsed.practice.PracticeScreenSmall
import fit.asta.health.tools.sunlight.view.components.bottomsheet.expanded.SheetExpanded
import fit.asta.health.tools.sunlight.view.components.bottomsheet.expanded.practice.PracticeLargeScreen


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen() {

    val scaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = rememberBottomSheetState(
            BottomSheetValue.Collapsed))

    val radius = (30 * scaffoldState.currentFraction).dp

    BottomSheetScaffold(modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = radius, topEnd = radius),
        sheetContent = {

            if (scaffoldState.bottomSheetState.isCollapsed) {
                SheetCollapsed(isCollapsed = scaffoldState.bottomSheetState.isCollapsed) {
                    PracticeScreenSmall()
                }
            } else {
                SheetExpanded {
                    PracticeLargeScreen()
                }
            }

        },
        sheetPeekHeight = 250.dp) {
        SunlightLayout(it = it)
    }
}