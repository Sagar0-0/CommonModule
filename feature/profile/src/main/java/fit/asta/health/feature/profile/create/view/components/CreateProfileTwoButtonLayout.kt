package fit.asta.health.feature.profile.create.view.components

import androidx.compose.runtime.Composable
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.components.functional.TwoButtonLayout
import fit.asta.health.designsystem.molecular.texts.CaptionTexts

@Composable
fun CreateProfileTwoButtonLayout(
    eventPrevious: () -> Unit,
    eventNext: () -> Unit,
    titleButton2: String = "Next",
) {
    TwoButtonLayout(onClickButton1 = eventPrevious, contentButton1 = {
        CaptionTexts.Level3(text = "Previous", color = AppTheme.colors.onSurface)
    }, onClickButton2 = eventNext, contentButton2 = {
        CaptionTexts.Level3(text = titleButton2, color = AppTheme.colors.onPrimary)
    })
}