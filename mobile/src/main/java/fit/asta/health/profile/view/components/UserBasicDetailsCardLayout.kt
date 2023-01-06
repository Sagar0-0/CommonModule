package fit.asta.health.profile.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.profile.view.EditIcon

@Composable
fun UserBasicDetailsCardLayout(
    cardImg: Int,
    cardType: String,
    cardValue: String,
    checkedState: MutableState<Boolean>,
    onClick: () -> Unit,
) {
    Column {
        Row(modifier = Modifier
            .padding(top = 8.dp, end = 8.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.End) {

            Box(modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                if (checkedState.value) {
                    EditIcon(onClick = onClick)
                }
            }


        }

        Spacer(modifier = Modifier.height(11.dp))

        Box(Modifier.padding(start = 16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = cardImg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(40.dp))

                Spacer(modifier = Modifier.padding(8.dp))

                Column {
                    Text(text = cardType,
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        lineHeight = 16.sp,
                        letterSpacing = 1.5.sp)
                    Spacer(modifier = Modifier.height(11.dp))
                    Text(text = cardValue,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        lineHeight = 24.sp,
                        letterSpacing = 0.15.sp,
                        fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(41.dp))

    }
}