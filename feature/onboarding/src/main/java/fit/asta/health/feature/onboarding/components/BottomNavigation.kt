package fit.asta.health.feature.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.resources.strings.R

@Composable
fun BottomNavigationSection(lastPage: Boolean, onNextClick: () -> Unit, onSkipClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = AppTheme.spacing.level3),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = if (lastPage) Arrangement.End else Arrangement.SpaceBetween
    ) {
        if (lastPage) {
            OnBoardingButton(
                text = R.string.proceed.toStringFromResId(),
                modifier = Modifier
                    .padding(end = AppTheme.spacing.level3),
            ) {
                onSkipClick()
            }
        } else {
            OnBoardingButton(
                text = "Skip",
                modifier = Modifier
                    .padding(start = AppTheme.spacing.level3),
            ) {
                onSkipClick()
            }
            OnBoardingButton(
                text = "Next",
                modifier = Modifier
                    .padding(end = AppTheme.spacing.level3),
            ) {
                onNextClick()
            }
        }
    }
}

@Composable
fun OnBoardingButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit
) {
    AppTextButton(
        textToShow = text,
        modifier = modifier,
        onClick = onClick,
    )
}