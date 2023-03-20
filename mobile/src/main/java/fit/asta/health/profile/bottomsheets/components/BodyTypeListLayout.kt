package fit.asta.health.profile.bottomsheets.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.common.ui.theme.spacing

@Composable
fun BodyTypeListLayout(
    listImg: Int,
    listType: String,
) {
    Box(modifier = Modifier.clickable { /*Todo*/ }, contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = listImg),
                contentDescription = null,
                alignment = Alignment.Center,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(spacing.small))
            Text(
                text = listType,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center
            )
        }
    }

}