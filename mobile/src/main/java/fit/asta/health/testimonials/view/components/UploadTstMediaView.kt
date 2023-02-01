package fit.asta.health.testimonials.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import fit.asta.health.R
import fit.asta.health.testimonials.view.theme.cardHeight
import fit.asta.health.testimonials.view.theme.imageHeight
import fit.asta.health.ui.spacing
import fit.asta.health.ui.theme.TextLight04

@Composable
fun UploadTstMediaView(
    title: String? = null,
    onClick: (() -> Unit)? = null,
) {

    Box(
        Modifier
            .padding(spacing.minSmall)
            .fillMaxWidth(1f)
            .height(cardHeight.medium)
            .clickable { onClick?.let { it() } }
            .clip(MaterialTheme.shapes.medium)
            .background(color = MaterialTheme.colorScheme.onSurface),
        contentAlignment = Alignment.Center) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.upload),
                contentDescription = null,
                modifier = Modifier.size(imageHeight.small)
            )

            Spacer(modifier = Modifier.height(spacing.small))

            Text(text = "Browse to choose")

            title?.let {
                Text(
                    text = it,
                    color = TextLight04,
                    modifier = Modifier.padding(spacing.small),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}