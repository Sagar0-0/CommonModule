package fit.asta.health.feature.profile.profile.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.feature.profile.profile.ui.screens.DeleteImageButton
import fit.asta.health.feature.profile.profile.ui.screens.EditProfileImageButton

@Composable
fun ProfileImagePicker(
    modifier: Modifier = Modifier,
    model: String?,
    onLauncherResult: (Uri?) -> Unit,
    onProfilePicClear: () -> Unit,
) {
    val imgLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            onLauncherResult(uri)
        }

    val isImgNotAvail = remember(model) {
        model.isNullOrEmpty()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        AppNetworkImage(
            model = model,
            contentDescription = "User Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(AppTheme.customSize.level11)
                .clip(CircleShape)
                .border(
                    border = BorderStroke(
                        width = 4.dp, color = AppTheme.colors.primary
                    ), shape = CircleShape
                ),
            errorImage = rememberVectorPainter(image = Icons.Filled.Person)
        )
        if (!isImgNotAvail) {
            DeleteImageButton(onProfilePicClear, modifier = Modifier.align(Alignment.TopEnd))
        }
        EditProfileImageButton(
            isImgNotAvail,
            modifier = Modifier.align(alignment = Alignment.BottomEnd)
        ) {
            imgLauncher.launch("image/*")
        }
    }
}