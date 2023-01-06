package fit.asta.health.profile.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.profile.view.EditIcon

@Composable
fun UserBodyType(
    bodyType: String,
    bodyImg: Int,
    checkedState: MutableState<Boolean>,
    onBodyType: () -> Unit,
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(8.dp)) {
        Column(modifier = Modifier.padding(vertical = 16.dp)) {
            Row(Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = bodyType,
                    fontSize = 10.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 1.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground)

                Box(modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                    if (checkedState.value) {
                        EditIcon(onBodyType)
                    }
                }


            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(Modifier
                .fillMaxWidth()
                .padding(start = 55.dp)) {
                Image(painter = painterResource(id = bodyImg),
                    contentDescription = null,
                    modifier = Modifier.size(width = 70.dp, height = 109.dp))
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
                horizontalArrangement = Arrangement.End) {
                Text(text = "bodyStatus",
                    fontSize = 14.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 0.4.sp,
                    color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}