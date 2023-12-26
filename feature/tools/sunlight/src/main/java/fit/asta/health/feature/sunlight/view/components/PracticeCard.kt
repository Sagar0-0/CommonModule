package fit.asta.health.feature.sunlight.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R

@Composable
fun PracticeCard() {
    AppCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp)) {
            Box(contentAlignment = Alignment.Center) {
                AppIcon(
                    painter = painterResource(id = R.drawable.ic_snowy),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                TitleTexts.Level2(text = "Sunscreen")
                Spacer(modifier = Modifier.height(8.dp))
                TitleTexts.Level2(text = "40 SPF")
            }
        }
    }
}