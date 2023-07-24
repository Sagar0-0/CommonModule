package fit.asta.health.profile.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fit.asta.health.common.ui.theme.imageSize
import fit.asta.health.common.ui.theme.spacing

@Composable
fun GenderOptionsLayout(
    cardImg: Int,
    cardType: String,
    cardValue: String,
) {
    Column(modifier = Modifier.padding(spacing.medium)) {
        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = cardImg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(imageSize.largeMedium)
                )

                Spacer(modifier = Modifier.padding(spacing.small))

                Column {
                    Text(
                        text = cardType,
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        lineHeight = 16.sp,
                        letterSpacing = 1.5.sp
                    )
                    Spacer(modifier = Modifier.height(spacing.small))
                    Text(
                        text = cardValue,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        lineHeight = 24.sp,
                        letterSpacing = 0.15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}