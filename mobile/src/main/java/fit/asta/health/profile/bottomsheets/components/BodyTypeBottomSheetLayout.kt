package fit.asta.health.profile.bottomsheets.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.R
import fit.asta.health.common.ui.theme.customSize
import fit.asta.health.common.ui.theme.spacing


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
fun BodyTypeBottomSheetLayout() {

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