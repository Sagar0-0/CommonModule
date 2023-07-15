package fit.asta.health.testimonials.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import fit.asta.health.common.ui.theme.cardHeight
import fit.asta.health.common.ui.theme.imageHeight
import fit.asta.health.common.ui.theme.spacing

@Composable
fun UploadTstMediaView(
    title: String? = null,
    onUploadClick: () -> Unit = {},
) {

    Box(
        Modifier
            .padding(spacing.minSmall)
            .fillMaxWidth(1f)
            .height(cardHeight.medium)
            .clickable { onUploadClick() }
            .clip(MaterialTheme.shapes.medium)
            .background(color = MaterialTheme.colorScheme.tertiaryContainer),
        contentAlignment = Alignment.Center) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = Icons.Filled.UploadFile,
                contentDescription = "Upload Image",
                modifier = Modifier.size(imageHeight.small),
                tint = MaterialTheme.colorScheme.onTertiaryContainer
            )

            Spacer(modifier = Modifier.height(spacing.small))

            Text(text = "Browse to choose", style = MaterialTheme.typography.bodyMedium)

            title?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier.padding(spacing.small),
                    style = MaterialTheme.typography.headlineSmall
                )
            }

        }
    }
}