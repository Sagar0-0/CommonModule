package fit.asta.health.feature.profile.create.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.scrollables.AppVerticalGrid
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R


data class BodyTypes(
    val bodyTypeImg: Int,
    val bodyTypeTitle: String,
)

val bodyTypeList = mutableListOf(
    BodyTypes(bodyTypeImg = R.drawable.underweight, bodyTypeTitle = "Under Weight"),
    BodyTypes(bodyTypeImg = R.drawable.normal, bodyTypeTitle = "Normal"),
    BodyTypes(bodyTypeImg = R.drawable.overweight, bodyTypeTitle = "Over Weight"),
    BodyTypes(bodyTypeImg = R.drawable.obese, bodyTypeTitle = "Obese")
)


@Preview
@Composable
fun BodyTypeLayout() {

    Column(Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = AppTheme.spacing.level2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TitleTexts.Level4(text = "Select the Body Type")
        }
        AppVerticalGrid(
            count = bodyTypeList.size,
            content = {
                bodyTypeList.forEach {
                    item {
                        BodyTypeListLayout(listImg = it.bodyTypeImg, listType = it.bodyTypeTitle)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppTheme.spacing.level1, vertical = AppTheme.spacing.level2
                )
                .height(AppTheme.customSize.level8),
            userScrollEnabled = false,
            horizontalArrangement = Arrangement.SpaceEvenly
        )

    }
}

@Composable
fun BodyTypeListLayout(
    listImg: Int,
    listType: String,
) {
    Box(modifier = Modifier.clickable { /*Todo*/ }, contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AppLocalImage(
                painter = painterResource(id = listImg),
                contentDescription = "BodyTypeImage",
                modifier = Modifier.size(AppTheme.imageSize.level5)
            )
            Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
            CaptionTexts.Level3(text = listType, textAlign = TextAlign.Center)
        }
    }

}