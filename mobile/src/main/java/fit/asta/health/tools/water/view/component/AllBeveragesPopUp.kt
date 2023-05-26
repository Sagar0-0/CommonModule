package fit.asta.health.tools.water.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.flowlayout.FlowRow
import fit.asta.health.tools.water.model.domain.WaterTool
import fit.asta.health.tools.water.viewmodel.WaterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun AllBeveragesPopUp(waterTool: WaterTool,viewModel: WaterViewModel = hiltViewModel()){

    val allBeveragesList by viewModel.modifiedWaterTool.collectAsStateWithLifecycle()
//    val saveButton by viewModel.somethingChanged.collectAsStateWithLifecycle()

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
                allBeveragesList?.beveragesDetails?.forEach {
//                    BeveragesComponentInPopUp(it.beverageId,it.title,it.code,it.isSelected)
                }
            }
//            if(saveButton) {
//                ElevatedButton(onClick = { }) {
//                    Text("save")
//                }
//            }
        }
    }
}