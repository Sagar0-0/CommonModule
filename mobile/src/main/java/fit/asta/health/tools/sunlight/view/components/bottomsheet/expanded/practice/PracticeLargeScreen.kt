package fit.asta.health.tools.sunlight.view.components.bottomsheet.expanded.practice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.tools.sunlight.view.components.bottomsheet.collapsed.ui.BottomSheetButtonLayout
import fit.asta.health.tools.sunlight.view.components.bottomsheet.collapsed.ui.DividerLineCenter
import fit.asta.health.tools.sunlight.view.components.bottomsheet.collapsed.ui.PracticeHeader
import fit.asta.health.tools.sunlight.view.components.bottomsheet.expanded.ui.PracticeExpandedGridView

@Preview
@Composable
fun PracticeLargeScreen() {
    Column(Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(16.dp))
        DividerLineCenter()
        Spacer(modifier = Modifier.height(16.dp))
        PracticeHeader()
        Spacer(modifier = Modifier.height(16.dp))
        PracticeExpandedGridView()
        Spacer(modifier = Modifier.height(16.dp))
        BottomSheetButtonLayout()
        Spacer(modifier = Modifier.height(16.dp))
    }
}