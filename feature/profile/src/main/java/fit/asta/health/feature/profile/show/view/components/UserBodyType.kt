package fit.asta.health.feature.profile.show.view.components

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.designsystemx.AstaThemeX

@Composable
fun UserBodyType(
    bodyType: String,
    bodyImg: Int,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(AstaThemeX.elevationX.smallExtraMedium),
        shape = RoundedCornerShape(AstaThemeX.spacingX.small)
    ) {
        Column(modifier = Modifier.padding(vertical = AstaThemeX.spacingX.medium)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = AstaThemeX.spacingX.medium, end = AstaThemeX.spacingX.small),
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
            Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
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
            Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(end = AstaThemeX.spacingX.medium),
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