package fit.asta.health.testimonials.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.navigation.home.view.component.LoadingAnimation
import fit.asta.health.ui.components.NextButton
import fit.asta.health.ui.theme.*

@Composable
fun SuccessfulCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    underReview: Boolean,
) {

    Box(contentAlignment = Alignment.TopCenter) {

        Card(
            modifier = modifier
                .padding(top = spacing.extraLarge)
                .heightIn(min = cardHeight.large)
                .fillMaxWidth(), shape = MaterialTheme.shapes.medium
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.extraLarge3)
            ) {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.medium),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Thank You!",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = ts.successfulCardText
                    )
                }

                Spacer(modifier = Modifier.height(spacing.small))

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.medium),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (underReview) {
                            "Your feedback is under review. Please wait few seconds."
                        } else {
                            "Your feedback has been submitted"
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(spacing.large))

                if (underReview) {
                    LoadingAnimation()
                } else {
                    NextButton(text = "Done", event = onClick)
                }

                Spacer(modifier = Modifier.height(spacing.medium))

            }

        }



        Box(
            modifier = Modifier
                .clip(shape = CircleShape)
//                .size(100.dp)
                .defaultMinSize(minWidth = boxSize.medium, minHeight = boxSize.medium)
                .background(color = Color.Green), contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = if (underReview) {
                    painterResource(id = R.drawable.review)
                } else {
                    painterResource(id = R.drawable.ic_tick)
                },
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(iconSize.medium)
            )
        }

    }


}