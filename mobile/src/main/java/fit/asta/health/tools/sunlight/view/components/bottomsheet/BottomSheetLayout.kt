package fit.asta.health.tools.sunlight.view.components.bottomsheet

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.tools.sunlight.view.components.bottomsheet.collapsed.SheetCollapsed
import fit.asta.health.tools.sunlight.view.components.bottomsheet.collapsed.practice.PracticeScreenSmall
import fit.asta.health.tools.sunlight.view.components.bottomsheet.expanded.SheetExpanded
import fit.asta.health.tools.sunlight.view.components.bottomsheet.expanded.practice.PracticeLargeScreen


@Preview
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen() {

    val scaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = rememberBottomSheetState(
            BottomSheetValue.Collapsed))


    BottomSheetScaffold(modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        sheetContent = {
            SheetContent {
                if (scaffoldState.bottomSheetState.isCollapsed) {
                    SheetCollapsed {
                        PracticeScreenSmall()
                    }
                } else {
                    SheetExpanded {
                        PracticeLargeScreen()
                    }
                }
            }
        }, sheetPeekHeight = 150.dp) {
        Text(text = "Hello World")
    }
}