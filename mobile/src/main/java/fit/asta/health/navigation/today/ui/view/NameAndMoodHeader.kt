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
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts

@Composable
fun NameAndMoodHomeScreenHeader(userName: String, onAlarm: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeadingTexts.Level3(
                text = stringResource(id = R.string.hello_Aastha) + " " + userName,
            )
            HeadingTexts.Level3(text = "\uD83D\uDC4B")
            AppTextButton(onClick = onAlarm, textToShow = stringResource(R.string.show_alarms))
        }
        CaptionTexts.Level2(
            text = stringResource(id = R.string.greeting),
            color = AppTheme.colors.onSurfaceVariant
        )
    }

}
