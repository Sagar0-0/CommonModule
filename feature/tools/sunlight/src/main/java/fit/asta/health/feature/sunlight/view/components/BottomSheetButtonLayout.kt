package fit.asta.health.feature.sunlight.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppFilledButton

@Composable
fun BottomSheetButtonLayout() {
    Row(
        modifier = Modifier
            .height(29.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .weight(1f)
        ) {
            AppFilledButton(
                textToShow = "Schedule",
                modifier = Modifier.fillMaxWidth(),
                shape = AppTheme.shape.level0
            ) {}
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .weight(1f)
        ) {
            AppFilledButton(
                textToShow = "Start",
                modifier = Modifier.fillMaxWidth(),
                shape = AppTheme.shape.level0
            ) {}
        }
    }
}

@Composable
fun Preview2() {
    BottomSheetButtonLayout()
}