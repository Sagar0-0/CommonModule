package fit.asta.health.tools.sunlight.view.components.bottomsheet.collapsed.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomSheetButtonLayout() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly) {
        Box(Modifier
            .fillMaxWidth()
            .weight(1f)) {
            BottomSheetButton(title = "Schedule")
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(Modifier
            .fillMaxWidth()
            .weight(1f)) {
            BottomSheetButton(title = "Start")
        }
    }
}

@Composable
@Preview
fun Preview2(){
    BottomSheetButtonLayout()
}