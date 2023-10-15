package fit.asta.health.feature.feedback.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import fit.asta.health.designsystem.molecular.button.AppRadioButton
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun McqCard(list: List<String>, updatedAns: (String) -> Unit) {

    val ans = rememberSaveable { mutableStateOf("") }

    Column {
        list.forEach { text ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                AppRadioButton(selected = text == ans.value) {
                    ans.value = text
                    updatedAns(text)
                }
                TitleTexts.Level4(text = text)
            }
        }
    }
}