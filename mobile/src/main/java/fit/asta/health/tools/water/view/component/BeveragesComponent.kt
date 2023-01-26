package fit.asta.health.tools.water.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.tools.water.model.network.UserBeverageData

@Composable
fun BeveragesComponent(title: String, icon_code: String) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = beverageIcons(icon_code)),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            lineHeight = 19.6.sp
        )
    }

}

fun beverageIcons(code: String): Int {
    return when (code) {
        "M" -> {
            R.drawable.ic_baseline_favorite_24
        }
        else -> {
            R.drawable.ic_baseline_favorite_24
        }
    }
}