package fit.asta.health.profile.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import fit.asta.health.profile.model.domain.HealthProperties


@Composable
fun ChipCard(
    icon: Int,
    title: String,
    list: List<HealthProperties>,
    editState: MutableState<Boolean>,
) {

    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp), elevation = 5.dp) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = title,
                        fontSize = 10.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 1.5.sp,
                        color = Color(0xDE000000)
                    )
                }
                if (editState.value) {
                    AddIcon()
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            FlowRow(mainAxisSpacing = 8.dp, crossAxisSpacing = 4.dp) {
                list.forEach {
                    ChipsOnCards(textOnChip = it.name, editState)
                }
            }
        }
    }
}