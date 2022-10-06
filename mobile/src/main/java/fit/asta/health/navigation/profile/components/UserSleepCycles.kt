package fit.asta.health.navigation.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserSleepCycles(
    columnType: String,
    columnValue: String,
) {
    Column {
        Text(text = columnType,
            fontSize = 10.sp,
            lineHeight = 16.sp,
            letterSpacing = 1.5.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xDE000000))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = columnValue,
            fontSize = 20.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xDE000000))
    }
}