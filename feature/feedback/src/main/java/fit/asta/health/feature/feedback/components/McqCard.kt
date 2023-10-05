package fit.asta.health.feature.feedback.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import fit.asta.health.designsystem.components.generic.AppButtons
import fit.asta.health.designsystem.components.generic.AppTexts

@Composable
fun mcqCard(list: List<String>): MutableState<String> {

    val ans = rememberSaveable { mutableStateOf("") }

    Column {
        list.forEach { text ->
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppButtons.AppRadioButton(
                    selected = text == ans.value,
                    onClick = {
                        ans.value = text
                    },
                    colors = RadioButtonDefaults.colors(MaterialTheme.colorScheme.primary)
                )
                AppTexts.TitleMedium(text = text)
            }
        }
    }

    return ans
}