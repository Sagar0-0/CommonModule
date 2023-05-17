package fit.asta.health.profile.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.spacing

@Composable
fun UserBodyType(
    bodyType: String,
    bodyImg: Int,
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
                    text = bodyType,
                    fontSize = 10.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 1.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.height(spacing.medium))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 55.dp)
            ) {
                Image(
                    painter = painterResource(id = bodyImg),
                    contentDescription = null,
                    modifier = Modifier.size(width = 70.dp, height = 109.dp)
                )
            }
            Spacer(modifier = Modifier.height(spacing.medium))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(end = spacing.medium),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "bodyStatus",
                    fontSize = 14.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 0.4.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}