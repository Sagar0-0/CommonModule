package fit.asta.health.feature.walking.component

import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.icon.AppIcon
import java.time.ZonedDateTime


@Composable
fun ExerciseSessionRow(
    start: ZonedDateTime,
    end: ZonedDateTime,
    uid: String,
    name: String,
    sourceAppName: String,
    sourceAppIcon: Drawable?,
    onDeleteClick: (String) -> Unit = {},
    onDetailsClick: (String) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ExerciseSessionInfoColumn(
            start = start,
            end = end,
            uid = uid,
            name = name,
            sourceAppName = sourceAppName,
            sourceAppIcon = sourceAppIcon,
            onClick = onDetailsClick
        )
        AppIconButton(
            onClick = { onDeleteClick(uid) },
        ) {
            AppIcon(imageVector = Icons.Default.Delete)
        }
    }
}

