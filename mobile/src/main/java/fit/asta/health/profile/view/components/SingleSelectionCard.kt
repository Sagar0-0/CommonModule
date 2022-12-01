package fit.asta.health.profile.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
fun SingleSelectionCard(
    icon: Int,
    title: String,
    value: String,
    editState: MutableState<Boolean>,
) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = 5.dp, shape = RoundedCornerShape(8.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    androidx.compose.material3.Text(
                        text = title,
                        fontSize = 10.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 1.5.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xDE000000)
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    androidx.compose.material3.Text(
                        text = value,
                        fontSize = 20.sp,
                        lineHeight = 24.sp,
                        letterSpacing = 0.15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                }
            }

            Box(modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                if (editState.value) {
                    EditIcon()
                }
            }
        }
    }
}