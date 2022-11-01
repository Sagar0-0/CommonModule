package fit.asta.health.tools.sunlight.view.components.bottomsheet.collapsed.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.tools.sunlight.view.components.bottomsheet.DividerLine

@Composable
fun DividerLineCenter() {
    Row(Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.Center) {
        DividerLine()

    }
}