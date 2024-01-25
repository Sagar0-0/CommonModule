package fit.asta.health.designsystem.molecular

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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
                    modifier = Modifier
                        .imePadding()
                        .navigationBarsPadding(),
                    onDismissRequest = onClose,
                    sheetState = sheetState,
                    windowInsets = WindowInsets.ime,
                    dragHandle = dragHandle,
                    content = content,
                )

            }
        },
        label = "",
    )
}