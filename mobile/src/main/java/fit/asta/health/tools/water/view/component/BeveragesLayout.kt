package fit.asta.health.tools.water.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
        val beveragesist  by viewModel.modifiedWaterTool.collectAsStateWithLifecycle()
//        val chossenIndex by viewModel.choosenIndexCode.collectAsStateWithLifecycle()

        if(beveragesist?.selectedListId?.isEmpty() == true){
            Text(text = "no bevrages selected")
        }else{
//            beveragesist?.beveragesDetails?.forEach {
//                if(it.isSelected) {
//                    BeveragesComponentOutSide(
//                        title = it.title,
//                        icon_code = it.code,
//                        beverageId = it.beverageId,
//                        index = chossenIndex!!
//                    )
//                }
//            }
        }

    }

}