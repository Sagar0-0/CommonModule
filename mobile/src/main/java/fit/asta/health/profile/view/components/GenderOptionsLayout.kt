package fit.asta.health.profile.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import fit.asta.health.designsystem.components.generic.AppCard
import fit.asta.health.designsystem.components.generic.AppDrawImg
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystem.theme.imageSize
import fit.asta.health.designsystem.theme.spacing

@Composable
fun GenderOptionsLayout(
    cardImg: Int,
    cardType: String,
    cardValue: String,
    modifier: Modifier = Modifier,
) {
    AppCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(spacing.medium)
                .fillMaxWidth() // Occupy the maximum available width
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing.medium)
            ) {
                AppDrawImg(
                    painter = painterResource(id = cardImg),
                    contentDescription = "Gender Images",
                    modifier = Modifier.size(imageSize.largeMedium)
                )
                Column {
                    AppTexts.BodySmall(text = cardType)
                    Spacer(modifier = Modifier.height(spacing.small))
                    AppTexts.BodyLarge(text = cardValue)
                }
            }
        }
    }
}
