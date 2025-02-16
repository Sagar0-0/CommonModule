package fit.asta.health.feature.profile.profile.ui.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.profile.remote.model.Gender
import fit.asta.health.data.profile.remote.model.GenderTypes
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppRadioButton
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.strings.R

@Composable
fun GenderSelector(
    selectedOption: Gender,
    onStateChange: (Gender) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.spacing.level2)
        ) {
            TitleTexts.Level3(
                text = R.string.gender.toStringFromResId(),
                color = AppTheme.colors.onTertiaryContainer
            )
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            GenderTypes.entries.forEach { option ->
                Row(
                    verticalAlignment = CenterVertically, modifier = Modifier.weight(1f)
                ) {
                    AppRadioButton(
                        selected = selectedOption == option.gender
                    ) {
                        onStateChange(option.gender)
                    }
                    CaptionTexts.Level3(
                        text = option.title,
                        color = AppTheme.colors.onPrimaryContainer
                    )
                }
            }
        }
    }
}