package fit.asta.health.feature.scheduler.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.button.AppRadioButton
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.strings.R as StringR


@Composable
fun NotificationBottomSheetLayout(
    text: String,
    onNavigateBack: () -> Unit,
    onSave:(String)->Unit={}
) {

    val radioOptions =
        listOf(
            stringResource(id = StringR.string.notification),
            stringResource(id = StringR.string.splash)
        )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }


    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppIconButton(imageVector = Icons.Default.Close, onClick = onNavigateBack)
            TitleTexts.Level2(
                text = text,
                color = AppTheme.colors.onTertiaryContainer,
                textAlign = TextAlign.Center
            )
            AppIconButton(imageVector = Icons.Default.Check) { onSave(selectedOption) }
        }

        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                AppRadioButton(
                    selected = (text == selectedOption)
                ) {
                    onOptionSelected(text)
                }
                TitleTexts.Level2(
                    text = text,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
        }
    }
}