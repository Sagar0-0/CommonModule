package fit.asta.health.feature.testimonials.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts

@Composable
fun UserCard(user: String, userOrg: String, userRole: String, url: String) {

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                AppNetworkImage(
                    model = getImgUrl(url),
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(AppTheme.imageSize.level9),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(AppTheme.spacing.level3))
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                BodyTexts.Level1(text = user, color = AppTheme.colors.onSecondaryContainer)
                BodyTexts.Level3(
                    text = "$userRole, $userOrg", color = AppTheme.colors.surfaceVariant
                )
            }
        }
    }
}