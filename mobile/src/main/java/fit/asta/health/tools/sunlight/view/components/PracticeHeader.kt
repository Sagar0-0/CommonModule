package fit.asta.health.tools.sunlight.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.molecular.texts.HeadingTexts

@Composable
@Preview
fun PracticeHeader() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
            HeadingTexts.Level2(text = "PRACTICE")
        }
    }
}