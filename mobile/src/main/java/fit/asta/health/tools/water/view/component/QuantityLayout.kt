package fit.asta.health.tools.water.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.tools.water.viewmodel.WaterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Preview
@Composable
fun QuantityLayout(viewModel: WaterViewModel = hiltViewModel()) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 0.dp),
        horizontalArrangement = Arrangement.SpaceEvenly) {
        val list  by viewModel.containerInCharge.collectAsState()

        list?.containers?.forEachIndexed {index,value->
            QuantityComponent(value = "$value ${list?.unit}",index=index)
        }
    }

}