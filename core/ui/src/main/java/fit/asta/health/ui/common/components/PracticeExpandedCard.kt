@file:OptIn(ExperimentalMaterial3Api::class)

package fit.asta.health.ui.common.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun PracticeExpandedCard(
    cardTitle: String,
    cardImg: Int,
    cardValue: String,
    modifier: Modifier = Modifier,
    onclick: () -> Unit,
) {

    AppCard(
        modifier = modifier
            .blur(radius = 5.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = onclick
    ) {
        Row(modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp)) {
            Box(contentAlignment = Alignment.Center) {
                AppIcon(
                    painter = painterResource(id = cardImg),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                TitleTexts.Level2(text = cardTitle)
                Spacer(modifier = Modifier.height(8.dp))
                BodyTexts.Level2(text = cardValue)
            }
        }
    }

}