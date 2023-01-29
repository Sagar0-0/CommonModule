package fit.asta.health.tools.water.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.R
import fit.asta.health.tools.water.model.network.UserBeverageData
import fit.asta.health.tools.water.viewmodel.WaterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun BeveragesComponentOutSide(beverageId: String,title: String, icon_code: String,index: String,viewModel: WaterViewModel = hiltViewModel()){
    Box(
        modifier = Modifier
            .padding(2.dp)
            .width(60.dp)
            .clickable {
                viewModel.updateTheChoosenIndex(icon_code)
            }
    ){
        BeveragesLayout(title,icon_code,null,index)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun BeveragesComponentInPopUp(beverageId: String,title: String, icon_code: String,isSelected : Boolean,viewModel: WaterViewModel = hiltViewModel()) {

    Box(
        modifier = Modifier
            .padding(2.dp)
            .width(60.dp)
            .clickable {
                viewModel.updateSelectBeveragesTool(beverageId)
            }
    ){
        BeveragesLayout(title,icon_code,isSelected,null)
    }
}

@Composable
fun BeveragesLayout(title: String, icon_code: String,isSelected : Boolean?,index: String?){
    if(isSelected !=null){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(5.dp).fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = beverageIcons(icon_code)),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if(isSelected == true) Color.Green else MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 12.sp,
                color = if(isSelected == true) Color.Green else MaterialTheme.colorScheme.onPrimary,
                lineHeight = 19.6.sp,
                textAlign = TextAlign.Center
            )
        }
    }else{
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(5.dp).fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = beverageIcons(icon_code)),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if(icon_code==index) Color.Green else Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 12.sp,
                color = if(icon_code==index) Color.Green else Color.Gray,
                lineHeight = 19.6.sp,
                textAlign = TextAlign.Center
            )
        }
    }

}

fun beverageIcons(code: String): Int {
    return when (code) {
        "W"->{
            R.drawable.sparkling_water
        }
        "FJ"->{
            R.drawable.fruit_juice
        }
        "SD"->{
            R.drawable.butter_milk_bottle
        }
        "M" -> {
            R.drawable.milk_bottle
        }
        "BM"->{
            R.drawable.butter_milk_bottle
        }
        "C"->{
            R.drawable.coconut_cocktail
        }
        else -> {
            R.drawable.ic_baseline_favorite_24
        }
    }
}