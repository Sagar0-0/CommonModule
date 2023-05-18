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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.spacing

@Composable
fun ProfileSingleSelectionCard(
    icon: Int,
    title: String,
    value: String,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = cardElevation.smallExtraMedium,
        shape = RoundedCornerShape(spacing.small)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(spacing.medium),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(spacing.medium))
                Column {
                    androidx.compose.material3.Text(
                        text = title,
                        fontSize = 10.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 1.5.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(spacing.minSmall))

                    androidx.compose.material3.Text(
                        text = value,
                        fontSize = 20.sp,
                        lineHeight = 24.sp,
                        letterSpacing = 0.15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                }
            }
        }
    }
}