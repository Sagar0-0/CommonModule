package fit.asta.health.common.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomModelBottomSheet(
    targetState: Boolean,
    sheetState: SheetState,
    content: @Composable ColumnScope.() -> Unit,
    dragHandle: @Composable (() -> Unit)?,
    onClose: () -> Unit,
) {
    AnimatedContent(
        targetState = targetState,
        content = { isVisible ->
            if (isVisible) {
                ModalBottomSheet(
                    onDismissRequest = onClose,
                    sheetState = sheetState,
                    windowInsets = BottomSheetDefaults.windowInsets,
                    dragHandle = dragHandle,
                    content = content,
                )

            }
        },
        label = "",
    )
}