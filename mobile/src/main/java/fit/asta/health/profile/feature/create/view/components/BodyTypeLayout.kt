package fit.asta.health.profile.feature.create.view.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.designsystem.theme.customSize
import fit.asta.health.designsystem.theme.spacing


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

        Spacer(modifier = Modifier.height(spacing.small))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = spacing.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Select the Body Type",
                color = Color(0x99000000),
                style = MaterialTheme.typography.titleSmall
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(bodyTypeList.size),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.small, vertical = spacing.medium)
                .height(customSize.extraLarge2),
            userScrollEnabled = false,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {


            bodyTypeList.forEach {
                item {
                    BodyTypeListLayout(listImg = it.bodyTypeImg, listType = it.bodyTypeTitle)
                }
            }

        }

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
            Image(
                painter = painterResource(id = listImg),
                contentDescription = null,
                alignment = Alignment.Center,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(spacing.small))
            Text(
                text = listType,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center
            )
        }
    }

}