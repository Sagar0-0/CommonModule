package fit.asta.health.profile.bottomsheets.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import fit.asta.health.R


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
        FlowRow(Modifier
            .padding(16.dp)
            .fillMaxWidth(),
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 8.dp) {
            bodyTypeList.forEach {
                BodyTypeListLayout(listImg = it.bodyTypeImg, listType = it.bodyTypeTitle)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        DoneButton()
    }

}


