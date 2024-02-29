package fit.asta.health.designsystem.atomic.modifier

import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun appRememberBottomSheetScaffoldState(bottomSheetState: SheetState): BottomSheetScaffoldState {
    return rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
object AppSheetStateValue {
    /**
     * The sheet is not visible.
     */
    val Hidden = SheetValue.Hidden

    /**
     * The sheet is visible at full height.
     */
    val Expanded = SheetValue.Expanded

    /**
     * The sheet is partially visible.
     */
    val PartiallyExpanded = SheetValue.PartiallyExpanded
}

@OptIn(ExperimentalMaterial3Api::class)
fun AppSheetState(
    skipPartialExpanded: Boolean = false,
    initialValue: SheetValue = AppSheetStateValue.Hidden,
    confirmValueChange: (SheetValue) -> Boolean = { true },
    skipHiddenState: Boolean = false
): SheetState {

    return SheetState(
        skipPartiallyExpanded = skipPartialExpanded,
        initialValue = initialValue,
        confirmValueChange = confirmValueChange,
        skipHiddenState = skipHiddenState
    )
}

//use case

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyUse() {
    val sheetState = appRememberBottomSheetScaffoldState(bottomSheetState = AppSheetState())//pass the all param you want here
}