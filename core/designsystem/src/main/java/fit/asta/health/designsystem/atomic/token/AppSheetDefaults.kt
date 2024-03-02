@file:OptIn(ExperimentalMaterial3Api::class)

package fit.asta.health.designsystem.atomic.token

import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHostState


/**
 * Possible values of [SheetState] used in [AppBottomSheet].
 */
@OptIn(ExperimentalMaterial3Api::class)
object AppSheetValue {
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

/**
 * State of the [BottomSheetScaffold] composable.
 *
 * @param bottomSheetState the state of the persistent bottom sheet
 * @param snackbarHostState the [SnackbarHostState] used to show snackbars inside the scaffold
 */

fun checkState(state : BottomSheetScaffoldState) : Boolean{
    return state.bottomSheetState.currentValue == AppSheetValue.Expanded
}

//object AppStates{
//    val state = BottomSheetScaffoldState
//}
//@ExperimentalMaterial3Api
//@Stable
//class AppBottomSheetScaffoldState(
//) {
//    val bottomSheetState: SheetState = rememberStandardBottomSheetState(),
//    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
//    @Composable
//    fun appBottomSheetScaffoldState(
//        bottomSheetState: SheetState,
//        snackbarHostState: SnackbarHostState
//    ): BottomSheetScaffoldState {
//        return rememberSaveable(bottomSheetState, snackbarHostState) {
//            BottomSheetScaffoldState(bottomSheetState, snackbarHostState)
//        }
//    }
//}
