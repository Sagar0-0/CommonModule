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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.feedback.view.SubmitButton
import fit.asta.health.ui.spacing
import fit.asta.health.ui.theme.ts

@Preview
@Composable
fun SuccessfulCard(modifier: Modifier = Modifier, onClick: (() -> Unit)? = null) {

    Box(contentAlignment = Alignment.TopCenter) {

        Card(
            modifier = modifier
                .padding(top = spacing.extraLarge)
                .heightIn(min = 252.dp)
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
                        text = "Your feedback has been submitted",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(spacing.large))

                SubmitButton(text = "Continue", onClick = onClick)

                Spacer(modifier = Modifier.height(spacing.medium))

            }

        }

        Box(
            modifier = Modifier
                .clip(shape = CircleShape)
//                .size(100.dp)
                .defaultMinSize(min(100.dp, 100.dp))
                .background(color = Color.Green),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_tick),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(80.dp)
            )
        }

    }


}