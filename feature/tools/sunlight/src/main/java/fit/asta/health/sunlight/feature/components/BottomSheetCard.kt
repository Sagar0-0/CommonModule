package fit.asta.health.sunlight.feature.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.sunlight.feature.WhiteTransparent
import fit.asta.health.sunlight.feature.gridItems
import fit.asta.health.resources.drawables.R as DrawR


@Composable
fun BottomSheetCard(
    title: String,
    caption: String,
    icon: ImageVector,
    titleStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(
        color = Color.White
    ),
    captionStyle: TextStyle = MaterialTheme.typography.bodySmall.copy(
        color = Color.White,
        fontWeight = FontWeight.Bold
    ),
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(AppTheme.spacing.level2)
            .clickable {
                onClick.invoke()
            }
            .background(color = WhiteTransparent, shape = RoundedCornerShape(12.dp))
            .padding(AppTheme.spacing.level1)
    ) {
        Icon(
            imageVector = icon, contentDescription = null,
            tint = Color.White
        )
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = titleStyle
            )
            Text(
                text = caption,
                style = captionStyle
            )
        }
    }
}

@Composable
fun BottomSheetCardPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = DrawR.drawable.background_app),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
    LazyColumn {
        gridItems(10, 2) {
            BottomSheetCard(title = "Sun", caption = "90", icon = Icons.Default.Person) {}

        }
    }

}