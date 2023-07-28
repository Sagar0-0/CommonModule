package fit.asta.health.common.ui.components

import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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
