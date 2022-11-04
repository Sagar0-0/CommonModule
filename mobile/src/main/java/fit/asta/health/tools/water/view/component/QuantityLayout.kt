package fit.asta.health.tools.water.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun QuantityLayout() {

    Row(Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly) {
        val list = listOf("200 ml", "500 ml", "1000 ml", "More")

        list.forEach {
            QuantityComponent(value = it)
        }
    }

}