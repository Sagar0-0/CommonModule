package fit.asta.health.navigation.today.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import fit.asta.health.R
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts

@Composable
fun NameAndMoodHomeScreenHeader(userName: String, onAlarm: () -> Unit) {

    val textToShow =
        stringResource(id = R.string.hello_Aastha) + " " + userName + " " + "\uD83D\uDC4B"

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeadingTexts.Level3(text = textToShow)
            AppTextButton(onClick = onAlarm, textToShow = stringResource(R.string.show_alarms))
        }
        CaptionTexts.Level1(text = stringResource(id = R.string.greeting))
    }
}
