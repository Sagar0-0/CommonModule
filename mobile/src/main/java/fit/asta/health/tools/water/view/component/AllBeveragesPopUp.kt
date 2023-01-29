package fit.asta.health.tools.water.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import fit.asta.health.tools.water.model.network.AllBeverageData
import fit.asta.health.tools.water.viewmodel.WaterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

import androidx.compose.runtime.*
import fit.asta.health.tools.water.model.domain.WaterTool

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun AllBeveragesPopUp(waterTool: WaterTool,viewModel: WaterViewModel = hiltViewModel()){

    val allBeveragesList by viewModel.modifiedWaterTool.collectAsState()
    val saveButton by viewModel.somethingChanged.collectAsState()

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondary)
            .padding(10.dp)
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            FlowRow {
                waterTool.beveragesDetails.forEach {
                    BeveragesComponentInPopUp(it.beverageId,it.title,it.code,it.isSelected)
                }
            }
            if(saveButton) {
                ElevatedButton(onClick = { }) {
                    Text("save")
                }
            }
        }
    }
}