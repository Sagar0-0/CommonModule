package fit.asta.health.profile.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.profile.model.domain.Session


@Composable
fun ProfileSessionCard(
    title: String,
    session: Session,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = cardElevation.smallExtraMedium,
        shape = RoundedCornerShape(spacing.small)
    ) {

        Column(modifier = Modifier.padding(vertical = spacing.medium)) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = spacing.medium, end = spacing.small),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = title,
                    fontSize = 10.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 1.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

            }

            Spacer(modifier = Modifier.height(spacing.medium))

            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                UserSleepCycles(columnType = "BED TIME", columnValue = "${session.from}")
                Spacer(modifier = Modifier.width(40.dp))
                UserSleepCycles(columnType = "WAKE UP", columnValue = "${session.to}")
            }

        }
    }
}