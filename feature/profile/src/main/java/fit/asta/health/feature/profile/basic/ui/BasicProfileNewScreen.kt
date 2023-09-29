package fit.asta.health.feature.profile.basic.ui

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.components.generic.AppDefaultIcon
import fit.asta.health.designsystem.components.generic.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R


@Preview(
    "Light Basic", heightDp = 1100
)
@Preview(
    name = "Dark Basic",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    heightDp = 1100
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicProfileNewScreen() {

    var profileImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                profileImageUri = it
            }
        }

    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AppScaffold(topBar = {
                AppTopBar(
                    backIcon = null, title = "Create a Profile"
                )
            }) { pad ->
                Column(
                    modifier = Modifier
                        .padding(pad)
                        .verticalScroll(state = rememberScrollState())
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AppTheme.appSpacing.small)
                ) {
                    Surface(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(AppTheme.appBoxSize.medium)
                    ) {
                        AppLocalImage(
                            painter = painterResource(id = R.drawable.ic_person),
                            contentDescription = "Profile",
                        )
                        Box(contentAlignment = Alignment.BottomEnd) {
                            AppIconButton(
                                imageVector = Icons.Filled.CameraEnhance,
                                iconTint = AppTheme.colors.onPrimaryContainer
                            ) {
                                imagePickerLauncher.launch("image/*")
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppTheme.appSpacing.medium),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // User icon and name section
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            AppDefaultIcon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "User Name Icon"
                            )
                            Spacer(modifier = Modifier.width(AppTheme.appSpacing.extraMedium))
                            Column {
                                CaptionTexts.Level4(text = "Name")
                                Spacer(modifier = Modifier.height(AppTheme.appSpacing.extraSmall))
                                TitleTexts.Level1(text = "Name")
                            }
                        }

                        // Edit button
                        AppIconButton(imageVector = Icons.Default.Edit, onClick = {
                            // Add your edit button click action here
                        })
                    }

                }
            }
        }
    }
}