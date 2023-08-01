package fit.asta.health.common.ui.components.generic

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
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
 * @param onDismissRequest Executes when the user clicks outside of the bottom sheet, after sheet
 * animates to [Hidden].
 * @param modifier Optional [Modifier] for the bottom sheet.
 * @param sheetState The state of the bottom sheet.
 * @param dragHandle Optional visual marker to swipe the bottom sheet.
 * @param windowInsets window insets to be passed to the bottom sheet window via [PaddingValues]
 * params.
 * @param content The content to be displayed inside the bottom sheet.
 * */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppModalBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    dragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    content: @Composable ColumnScope.() -> Unit,
    windowInsets: WindowInsets = BottomSheetDefaults.windowInsets,
) {

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = content,
        modifier = modifier.fillMaxSize(),
        sheetState = sheetState,
        dragHandle = dragHandle,
        windowInsets = windowInsets,
    )

}


//TODO Migrate to Material 3

/** [AppModalBottomSheetLayout] is a  Modal bottom sheets present a set of choices while blocking
 * interaction with the rest of the screen. They are an alternative to inline menus and
 * simple dialogs, providing additional room for content, iconography, and actions.
 * @param sheetContent The content of the bottom sheet.
 * @param modifier Optional [Modifier] for the entire component.
 * @param sheetState The state of the bottom sheet.
 * @param content The content of rest of the screen.
 * */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppModalBottomSheetLayout(
    modifier: Modifier = Modifier, sheetContent: @Composable ColumnScope.() -> Unit,
    sheetState: ModalBottomSheetState, content: @Composable () -> Unit,
) {

    ModalBottomSheetLayout(
        sheetContent = sheetContent,
        content = content,
        modifier = modifier
            .fillMaxSize()
            .wrapContentHeight(),
        sheetState = sheetState
    )

}
