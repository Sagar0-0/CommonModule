package fit.asta.health.profile.bottomsheets.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.feedback.view.SubmitButton


data class BodyTypes(
    val bodyTypeImg: Int,
    val bodyTypeTitle: String,
)

val bodyTypeList =
    mutableListOf(BodyTypes(bodyTypeImg = R.drawable.underweight, bodyTypeTitle = "Under Weight"),
        BodyTypes(bodyTypeImg = R.drawable.normal, bodyTypeTitle = "Normal"),
        BodyTypes(bodyTypeImg = R.drawable.overweight, bodyTypeTitle = "Over Weight"),
        BodyTypes(bodyTypeImg = R.drawable.obese, bodyTypeTitle = "Obese"))


@Preview
@Composable
fun BodyTypeBottomSheetLayout() {

    Column(Modifier
        .fillMaxWidth()
        .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            DividerLine()
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Select the Body Type", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))

        LazyVerticalGrid(columns = GridCells.Fixed(4),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(horizontal = 16.dp),
            userScrollEnabled = false) {
            bodyTypeList.forEach {
                item {
                    BodyTypeListLayout(listImg = it.bodyTypeImg, listType = it.bodyTypeTitle)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        SubmitButton(text = "Done")
    }

}