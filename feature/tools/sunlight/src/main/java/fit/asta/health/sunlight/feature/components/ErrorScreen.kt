package fit.asta.health.sunlight.feature.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.resources.drawables.R as DrawR


@Composable
fun ErrorScreen(
    @StringRes errorTitle: Int,
    errorMessage: String,
    @DrawableRes errorIcon: Int = DrawR.drawable.ic_error_not_found,
    buttonTitle: String? = null,
    onButtonClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppTheme.spacing.level2),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppLocalImage(
            painter = painterResource(id = errorIcon),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
        HeadingTexts.Level2(
            text = errorTitle.toStringFromResId(),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = AppTheme.colors.primary
        )
        Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
        CaptionTexts.Level2(
            text = errorMessage,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
        if (!buttonTitle.isNullOrEmpty()) {
            MyAppButton(
                title = buttonTitle,
                modifier = Modifier.fillMaxWidth(0.5f),
                onClick = onButtonClick,
                backgroundColor = AppTheme.colors.primary
            )
        }

    }

}