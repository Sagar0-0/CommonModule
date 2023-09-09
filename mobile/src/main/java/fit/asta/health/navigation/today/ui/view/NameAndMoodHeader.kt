package fit.asta.health.navigation.today.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import fit.asta.health.R
import fit.asta.health.designsystem.components.generic.AppButtons
import fit.asta.health.designsystem.components.generic.AppTexts

@Composable
fun NameAndMoodHomeScreenHeader(userName: String, onAlarm: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppTexts.HeadlineSmall(
                text = stringResource(id = R.string.hello_Aastha) + " " + userName,
                color = MaterialTheme.colorScheme.onBackground
            )
            AppTexts.HeadlineSmall(text = "\uD83D\uDC4B")
            AppButtons.AppTextButton(onClick = onAlarm) {
                AppTexts.LabelLarge(text = "Show Alarms")
            }
        }
        AppTexts.LabelMedium(
            text = stringResource(id = R.string.greeting),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

}
