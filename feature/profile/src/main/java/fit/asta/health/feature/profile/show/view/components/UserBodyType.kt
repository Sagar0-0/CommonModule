package fit.asta.health.feature.profile.show.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.components.generic.AppCard
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts

@Composable
fun UserBodyType(
    bodyType: String,
    bodyImg: Int,
) {
    AppCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(vertical = AppTheme.spacing.level3)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = AppTheme.spacing.level3, end = AppTheme.spacing.level2),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BodyTexts.Level1(text = bodyType)
            }
            Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 55.dp)
            ) {
                AppLocalImage(
                    painter = painterResource(id = bodyImg),
                    contentDescription = null,
                    modifier = Modifier.size(width = 70.dp, height = 109.dp)
                )
            }
            Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(end = AppTheme.spacing.level3),
                horizontalArrangement = Arrangement.End
            ) {
                BodyTexts.Level2(text = "Body Status")
            }
        }
    }
}