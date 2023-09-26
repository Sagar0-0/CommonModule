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
import androidx.compose.ui.res.painterResource
import fit.asta.health.R
import fit.asta.health.designsystem.components.generic.AppButtons
import fit.asta.health.designsystem.components.generic.AppDrawImg
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystemx.AstaThemeX

@Composable
fun ReferAndEarn() {

    Box(modifier = Modifier.aspectRatio(AstaThemeX.aspectRatioX.common), content = {

        AppDrawImg(
            painterResource(id = R.drawable.background_image),
            contentDescription = "Refer/Earn Img",
            modifier = Modifier.fillMaxSize()
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(AstaThemeX.spacingX.small)
        ) {

            AppDrawImg(
                painterResource(id = R.drawable.refer_image),
                contentDescription = "Refer/Earn Img",
                modifier = Modifier.aspectRatio(AstaThemeX.aspectRatioX.fullScreen),
            )

            Spacer(modifier = Modifier.width(AstaThemeX.spacingX.medium))

            Box {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    AppTexts.TitleMedium(text = "Refer and Earn")
                    Spacer(modifier = Modifier.height(AstaThemeX.spacingX.small))
                    AppTexts.BodySmall(text = "Send referral link to your friend to earn ₹100")
                    Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
                    AppButtons.AppStandardButton(onClick = {},
                        modifier = Modifier.height(AstaThemeX.buttonSizeX.large),
                        contentPadding = PaddingValues(
                            vertical = AstaThemeX.spacingX.minSmall,
                            horizontal = AstaThemeX.spacingX.small
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