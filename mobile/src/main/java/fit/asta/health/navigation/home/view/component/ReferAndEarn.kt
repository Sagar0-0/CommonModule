package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.R
import fit.asta.health.common.ui.components.generic.AppButton
import fit.asta.health.common.ui.components.generic.AppDrawImg
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.theme.aspectRatio
import fit.asta.health.common.ui.theme.buttonSize
import fit.asta.health.common.ui.theme.spacing

@Composable
fun ReferAndEarn() {

    Box(modifier = Modifier.aspectRatio(aspectRatio.common), content = {

        AppDrawImg(
            imgId = R.drawable.background_image,
            contentDescription = "Refer/Earn Img",
            modifier = Modifier.fillMaxSize()
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(spacing.small)
        ) {

            AppDrawImg(
                imgId = R.drawable.refer_image,
                contentDescription = "Refer/Earn Img",
                modifier = Modifier.aspectRatio(aspectRatio.fullScreen),
            )

            Spacer(modifier = Modifier.width(spacing.medium))

            Box {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    AppTexts.TitleMedium(text = "Refer and Earn")
                    Spacer(modifier = Modifier.height(spacing.small))
                    AppTexts.BodySmall(text = "Send referral link to your friend to earn ₹100")
                    Spacer(modifier = Modifier.height(spacing.medium))
                    AppButton(onClick = {},
                        modifier = Modifier.height(buttonSize.large),
                        contentPadding = PaddingValues(
                            vertical = spacing.minSmall, horizontal = spacing.small
                        ),
                        content = {
                            AppTexts.LabelLarge(
                                text = "Refer Us", color = MaterialTheme.colorScheme.onPrimary
                            )
                        })
                }
            }
        }
    })

}