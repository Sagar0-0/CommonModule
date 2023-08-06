package fit.asta.health.common.ui.components

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import fit.asta.health.common.jetpack.dashedBorder
import fit.asta.health.common.ui.components.generic.AppDefaultIcon
import fit.asta.health.common.ui.theme.Shapes
import fit.asta.health.common.ui.theme.customSize
import fit.asta.health.common.ui.theme.iconButtonSize
import fit.asta.health.common.ui.theme.imageSize
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.getFileName


@Composable
fun uploadFiles(modifier: Modifier = Modifier): SnapshotStateList<Uri> {
    val uriList = remember {
        mutableStateListOf<Uri>()
    }
    val context = LocalContext.current
    Column(modifier = modifier) {
        val resultLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetMultipleContents()) { list ->
                kotlin.run lit@{
                    list.forEach {
                        if (uriList.size >= 5) {
                            Toast.makeText(
                                context, "You can upload maximum 5 files.", Toast.LENGTH_SHORT
                            ).show()
                            return@lit
                        } else {
                            if (!uriList.contains(it)) {
                                uriList.add(it)
                            } else {
                                Toast.makeText(
                                    context, "Duplicate files are ignored!", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

            }

        Spacer(modifier = Modifier.height(spacing.medium))

        Box(
            modifier = Modifier.dashedBorder(
                width = 1.dp,
                radius = customSize.small,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.medium, vertical = spacing.small),
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
                                    .padding(spacing.extraSmall)
                                    .clip(Shapes.extraLarge)
                                    .border(
                                        width = 0.4.dp,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        shape = Shapes.extraLarge
                                    )
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(spacing.small),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(0.7f),
                                    text = getFileName(context, it),
                                    maxLines = 1,
                                    softWrap = true,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.width(1.dp))
                                IconButton(modifier = Modifier.size(iconButtonSize.extraMedium),
                                    onClick = { uriList.remove(it) }) {
                                    Icon(
                                        imageVector = Icons.Default.Close, contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }

                Button(
                    enabled = uriList.size < 5,
                    onClick = {
                        resultLauncher.launch("*/*")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    AppDefaultIcon(
                        imageVector = Icons.Rounded.CloudUpload,
                        contentDescription = "Upload File",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(imageSize.standard)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(spacing.medium))

        Text(
            text = "You can upload maximum 5 files*",
            color = Color.Black,
            style = MaterialTheme.typography.bodySmall
        )
    }
    return uriList
}