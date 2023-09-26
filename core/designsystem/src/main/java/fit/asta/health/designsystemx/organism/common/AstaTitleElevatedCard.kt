package fit.asta.health.designsystemx.organism.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystemx.molecular.cards.AstaElevatedCard
import fit.asta.health.designsystemx.molecular.texts.HeadlineTexts

/**
 * This function is the Card view Template used
 *
 * @param modifier To be passed by the Parent Class
 * @param title This is the title for the card
 * @param body The UI which will be drawn inside this card
 */
@Composable
fun AstaTitleElevatedCard(
    modifier: Modifier = Modifier,
    title: String? = null,
    body: @Composable () -> Unit
) {

    // This function draws an elevated Card View
    AstaElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {

        Column(modifier = Modifier.padding(8.dp)) {
            if (!title.isNullOrEmpty()) {

                HeadlineTexts.Small(
                    text = title,

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),

                    // Text Features
                    textAlign = TextAlign.Start
                )
            }

            // Graph Body Function
            body()
        }
    }
}