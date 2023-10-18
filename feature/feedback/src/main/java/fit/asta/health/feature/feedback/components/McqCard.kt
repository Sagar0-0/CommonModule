package fit.asta.health.feature.feedback.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import fit.asta.health.designsystem.molecular.button.AppRadioButton
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun McqCard(
    ans: String,
    list: List<String>,
    updatedAns: (String) -> Unit
) {
    Column {
        list.forEach { text ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                AppRadioButton(selected = text == ans) {
                    updatedAns(text)
                }
                TitleTexts.Level4(text = text)
            }
        }
    }
}