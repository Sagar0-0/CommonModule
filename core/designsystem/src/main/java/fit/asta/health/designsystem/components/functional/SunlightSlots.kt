package fit.asta.health.designsystem.components.functional

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoNotDisturb
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.material.R
import fit.asta.health.designsystem.components.generic.AppButtons
import fit.asta.health.designsystem.components.generic.AppDefaultIcon
import fit.asta.health.designsystem.AppTheme

@Composable
fun SunlightSlotsCardLayout(modifier: Modifier = Modifier, time: String, temperature: String) {

    val superscript = SpanStyle(
        baselineShift = BaselineShift.Superscript,
        fontSize = 10.sp,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold
    )

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x1A959393))
    ) {
        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .padding(8.dp)
        ) {

            ScheduleButtonIcon(onButtonClick = { /*TODO Schedule Button Click*/ })

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )

                Text(fontSize = 12.sp, text = buildAnnotatedString {
                    append(temperature)
                    withStyle(superscript) {
                        append("o")
                    }
                    append("C")
                }, color = Color.Black, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.DoNotDisturb,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = "1 UVI",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clock_black_24dp),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = time, fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Bold
                )
            }

        }
    }
}

@Composable
fun ScheduleButtonIcon(
    onButtonClick: () -> Unit,
    imageVector: ImageVector = Icons.Filled.Schedule,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.small), horizontalArrangement = Arrangement.End
    ) {
        ScheduleIconLayout(onButtonClick, imageVector)
    }
}

@Composable
fun ScheduleIconLayout(
    onButtonClick: () -> Unit,
    imageVector: ImageVector = Icons.Filled.Schedule,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(AppTheme.iconSize.level4)
            .clip(RoundedCornerShape(AppTheme.spacing.small))
            .background(color = Color.White)
    ) {
        AppButtons.AppIconButton(
            onClick = onButtonClick,
        ) {
            AppDefaultIcon(
                imageVector = imageVector,
                contentDescription = "Schedule Icon",
                tint = Color.DarkGray
            )
        }
    }
}