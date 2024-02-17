package fit.asta.health.designsystem.molecular.background

import android.util.Log
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue.Hidden
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


/** [AppModalBottomSheet] is a composable function in Jetpack Compose that creates a modal
 * bottom sheet, which is a type of dialog that slides up from the bottom of the screen to
 * display content.
 * @param modifier Optional [Modifier] for the bottom sheet.
 * @param sheetState The state of the bottom sheet.
 * @param onDismissRequest Executes when the user clicks outside of the bottom sheet, after sheet
 * animates to [Hidden].
 * @param windowInsets window insets to be passed to the bottom sheet window via [PaddingValues]
 * params.
 * @param dragHandle Optional visual marker to swipe the bottom sheet.
 * @param content The content to be displayed inside the bottom sheet.
 * */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppModalBottomSheet(
    sheetVisible: Boolean,
    sheetState: SheetState,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    windowInsets: WindowInsets = WindowInsets.ime,
    dragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    content: @Composable ColumnScope.() -> Unit,
) {
    Log.d("SHEET", "AppModalBottomSheet: $sheetVisible")
    if (sheetVisible) {
        ModalBottomSheet(
            modifier = modifier.fillMaxSize(),
            sheetState = sheetState,
            onDismissRequest = onDismissRequest,
            windowInsets = windowInsets,
            dragHandle = dragHandle,
            content = content,
        )
    }
}


//TODO Migrate to Material 3
/** [AppModalBottomSheetLayout] is a  Modal bottom sheets present a set of choices while blocking
 * interaction with the rest of the screen. They are an alternative to inline menus and
 * simple dialogs, providing additional room for content, iconography, and actions.
 * @param modifier Optional [Modifier] for the entire component.
 * @param sheetState The state of the bottom sheet.
 * @param sheetContent The content of the bottom sheet.
 * @param content The content of rest of the screen.
 * */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppModalBottomSheetLayout(
    sheetState: ModalBottomSheetState,
    modifier: Modifier = Modifier,
    sheetContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit,
) {
    ModalBottomSheetLayout(
        modifier = modifier
            .fillMaxSize()
            .wrapContentHeight(),
        sheetState = sheetState,
        sheetContent = sheetContent,
        content = content,
    )
}