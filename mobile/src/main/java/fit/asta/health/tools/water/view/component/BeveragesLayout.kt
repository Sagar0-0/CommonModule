package fit.asta.health.tools.water.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.common.validation.util.TextFieldType
import fit.asta.health.tools.water.viewmodel.WaterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Preview
@Composable
fun BeveragesLayout(viewModel: WaterViewModel = hiltViewModel()) {


    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly) {

        //val list = listOf("Water", "Butter Milk", "Coconut", "Milk", "Fruit Juice")

        when(val value = viewModel.waterTool.collectAsState().value?.userBeverageInfo?.userBeverageList) {
            null -> Text("no beverages selected")
            else -> {
                println(value)
                value.forEach {
                    BeveragesComponent(title = it.title, icon_code = it.code)
                }
            }
        }
    }

}