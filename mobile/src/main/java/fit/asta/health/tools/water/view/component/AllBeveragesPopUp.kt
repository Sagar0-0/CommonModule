package fit.asta.health.tools.water.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import fit.asta.health.tools.water.model.network.AllBeverageData
import fit.asta.health.tools.water.viewmodel.WaterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun AllBeveragesPopUp(viewModel: WaterViewModel = hiltViewModel()){
   Popup {
       Column {
           FlowRow {
               viewModel.waterTool.collectAsState().value?.allBeveragesList?.forEach {
                   BeveragesComponent(it.title,it.code)
               }
           }
           ElevatedButton(onClick = { /*TODO*/ }) {
               Text("save")
           }
       }

   }
}