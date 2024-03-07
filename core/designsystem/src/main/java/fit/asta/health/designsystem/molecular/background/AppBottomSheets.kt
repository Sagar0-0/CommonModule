@file:OptIn(ExperimentalMaterialApi::class)

package fit.asta.health.designsystem.molecular.background

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SheetValue.Hidden
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

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
    sheetVisible : Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = appRememberModalBottomSheetState(),
    sheetMaxWidth: Dp = BottomSheetDefaults.SheetMaxWidth,
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    contentColor: Color = contentColorFor(containerColor),
    tonalElevation: Dp = BottomSheetDefaults.Elevation,
    scrimColor: Color = BottomSheetDefaults.ScrimColor,
    dragHandle: @Composable() (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    windowInsets: WindowInsets = WindowInsets.navigationBars,
    properties: ModalBottomSheetProperties = ModalBottomSheetDefaults.properties(),
    content: @Composable() (ColumnScope.() -> Unit)
) {
    if (sheetVisible) {
        ModalBottomSheet(
            modifier = modifier,
            sheetState = sheetState,
            onDismissRequest = onDismissRequest,
            dragHandle = dragHandle,
            sheetMaxWidth = sheetMaxWidth,
            shape = shape,
            contentColor = contentColor,
            tonalElevation = tonalElevation,
            scrimColor = scrimColor,
            properties = properties,
            windowInsets = windowInsets.add(WindowInsets.ime),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun appRememberModalBottomSheetState(
    skipPartiallyExpanded: Boolean = false,
    confirmValueChange: (SheetValue) -> Boolean = { true }
): SheetState {
    return rememberModalBottomSheetState(
        skipPartiallyExpanded,
        confirmValueChange
    )
}

