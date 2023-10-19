package fit.asta.health.designsystem.molecular

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.CloudUpload
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.documentfile.provider.DocumentFile
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.modifier.dashedBorder
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UploadFiles(
    modifier: Modifier = Modifier,
    uriList: List<Uri>,
    isValid: Boolean,
    uploadLimit: Int,
    onItemAdded: (List<Uri>) -> Unit,
    onItemDeleted: (Uri) -> Unit
) {

    val context = LocalContext.current

    // This code would be executed when the File is selected from the memory
    val resultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { list ->
        onItemAdded(list)
    }

    // Parent Composable
    Column(verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level0)) {

        // All The Uploaded Uris are here with the upload Button with them
        Row(
            modifier
                .fillMaxWidth()
                .dashedBorder(
                    width = 1.dp,
                    radius = AppTheme.customSize.level1,
                    color = if (isValid)
                        AppTheme.colors.onBackground
                    else
                        AppTheme.colors.error
                )
                .clip(AppTheme.shape.level1)
                .padding(AppTheme.spacing.level1),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Upload Files Text to let the user know that they need to upload
            if (uriList.isEmpty())
                BodyTexts.Level2(
                    modifier = Modifier.padding(start = AppTheme.spacing.level1),
                    text = "Upload Files"
                )
            else {

                // This contains all the URIs uploaded by the User
                FlowRow(
                    modifier = Modifier.padding(AppTheme.spacing.level1),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
                ) {
                    uriList.forEach {

                        // Bordered Box for containing the URI and the delete button
                        Box(
                            modifier = Modifier
                                .border(
                                    width = 0.5.dp,
                                    color = AppTheme.colors.onSurfaceVariant,
                                    shape = AppTheme.shape.level0
                                )
                                .clip(AppTheme.shape.level1),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier.padding(AppTheme.spacing.level1),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
                            ) {

                                // URI Text
                                CaptionTexts.Level3(
                                    text = DocumentFile.fromSingleUri(context, it)?.name ?: "",
                                    maxLines = 1
                                )

                                // Delete Button
                                AppIconButton(
                                    imageVector = Icons.Default.Close,
                                    iconDesc = "Delete Icon Button",
                                    modifier = Modifier.size(AppTheme.iconSize.level3)
                                ) {
                                    onItemDeleted(it)
                                }
                            }
                        }
                    }
                }
            }

            // Upload Button
            AppIconButton(
                imageVector = Icons.Rounded.CloudUpload,
                iconDesc = "Upload Icon Button",
                iconTint = if (isValid)
                    AppTheme.colors.primary
                else
                    AppTheme.colors.error
            ) { resultLauncher.launch("*/*") }
        }

        // Error Text to be shown when the User crosses the Amount of Files he can Select
        if (!isValid) BodyTexts.Level3(
            text = "You cannot upload more than $uploadLimit files",
            color = AppTheme.colors.error
        )
    }
}