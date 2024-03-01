package fit.asta.health.sunlight.feature.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.sunlight.feature.BlackTransparent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    showIcon: Boolean = false,
    onLeadingIconClick: () -> Unit,
    onTrailingIconClick: () -> Unit
) {
    val spacing = AppTheme.spacing
    TopAppBar(
        title = {
            BodyTexts.Level1(
                text = title,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = spacing.level2)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(AppTheme.customSize.level7),
//        colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = BlackTransparent),
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .clickable { onLeadingIconClick.invoke() }
                    .padding(spacing.level2),
                tint = Color.White
            )
        },
        actions = {
            if (showIcon) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { onTrailingIconClick() }
                            .padding(spacing.level2),
                        tint = Color.White
                    )
                }
            }
        }
    )
}