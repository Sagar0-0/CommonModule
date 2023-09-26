package fit.asta.health.feature.profile.create.view.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import fit.asta.health.designsystem.components.functional.TwoButtonLayout
import fit.asta.health.designsystem.components.generic.AppTexts

@Composable
fun CreateProfileTwoButtonLayout(
    eventPrevious: () -> Unit,
    eventNext: () -> Unit,
    titleButton2: String = "Next",
) {
    TwoButtonLayout(onClickButton1 = eventPrevious, contentButton1 = {
        AppTexts.LabelLarge(
            text = "Previous", color = MaterialTheme.colorScheme.onSurface
        )
    }, onClickButton2 = eventNext, contentButton2 = {
        AppTexts.LabelLarge(
            text = titleButton2, color = MaterialTheme.colorScheme.onPrimary
        )
    })
}