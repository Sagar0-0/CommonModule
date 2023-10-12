package fit.asta.health.designsystem.molecular

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.CloudUpload
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.documentfile.provider.DocumentFile
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.modifier.dashedBorder
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun UploadFiles(modifier: Modifier = Modifier, updatedUriList: (List<Uri>) -> Unit) {
    val uriList = remember {
        mutableStateListOf<Uri>()
    }
    val context = LocalContext.current
    Column(modifier = modifier) {
        val resultLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetMultipleContents()) { list ->
                if (list.size > 5) {
                    Toast.makeText(
                        context, "You can upload maximum 5 files.", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    uriList.addAll(list)
                    updatedUriList(list.toSet().toList())
                }
            }

        Spacer(modifier = Modifier.height(AppTheme.spacing.level1))

        Box(
            modifier = Modifier.dashedBorder(
                width = 1.dp,
                radius = AppTheme.customSize.level1,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = AppTheme.spacing.level1,
                        vertical = AppTheme.spacing.level2
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (uriList.isEmpty()) {
                    Text(
                        text = "Upload Files",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                        uriList.forEach {
                            Row(
                                modifier = Modifier
                                    .padding(AppTheme.spacing.level4)
                                    .clip(AppTheme.shape.level1)
                                    .border(
                                        width = 0.4.dp,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        shape = AppTheme.shape.level0
                                    )
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(AppTheme.spacing.level2),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TitleTexts.Level2(
                                    modifier = Modifier.fillMaxWidth(0.7f),
                                    text = DocumentFile.fromSingleUri(context, it)?.name ?: "",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.width(1.dp))
                                AppIconButton(
                                    modifier = Modifier.size(AppTheme.iconSize.level3),
                                    imageVector = Icons.Default.Close,
                                    onClick = { uriList.remove(it) }
                                )
                            }
                        }
                    }
                }

                AppIconButton(
                    enabled = uriList.size < 5,
                    imageVector = Icons.Rounded.CloudUpload,
                ) {
                    resultLauncher.launch("*/*")
                }
            }
        }

        Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

        TitleTexts.Level3(
            text = "You can upload maximum 5 files*",
            color = Color.Black
        )
    }
}