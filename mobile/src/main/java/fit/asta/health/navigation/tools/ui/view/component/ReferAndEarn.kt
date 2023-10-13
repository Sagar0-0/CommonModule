package fit.asta.health.navigation.tools.ui.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import fit.asta.health.R
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun ReferAndEarn() {

    Box(modifier = Modifier.aspectRatio(AppTheme.aspectRatio.common), content = {

        AppLocalImage(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = "Refer/Earn Img",
            modifier = Modifier.fillMaxSize()
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(AppTheme.spacing.level1)
        ) {

            AppLocalImage(
                painter = painterResource(id = R.drawable.refer_image),
                contentDescription = "Refer/Earn Img",
                modifier = Modifier.aspectRatio(AppTheme.aspectRatio.fullScreen),
            )

            Spacer(modifier = Modifier.width(AppTheme.spacing.level2))

            Box {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    TitleTexts.Level2(text = "Refer and Earn")
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
                    BodyTexts.Level3(text = "Send referral link to your friend to earn ₹100")
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                    AppFilledButton(textToShow = "Refer Us", onClick = {})
                }
            }
        }
    })
}