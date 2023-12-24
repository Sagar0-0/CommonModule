package fit.asta.health.feature.walking.component

import android.graphics.drawable.Drawable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import java.time.ZonedDateTime

/**
 * Displays summary information about the [ExerciseSessionRecord]
 */

@Composable
fun ExerciseSessionInfoColumn(
    start: ZonedDateTime,
    end: ZonedDateTime,
    uid: String,
    name: String,
    sourceAppName: String,
    sourceAppIcon: Drawable?,
    onClick: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier.clickable {
            onClick(uid)
        }
    ) {
        CaptionTexts.Level2(
            text = "${start.toLocalTime()} - ${end.toLocalTime()}",
        )
        BodyTexts.Level2(name)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            BodyTexts.Level2(
                text = sourceAppName,
            )
        }
        BodyTexts.Level2(uid)
    }
}
