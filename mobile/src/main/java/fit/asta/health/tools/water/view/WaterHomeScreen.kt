package fit.asta.health.tools.water.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.R
import fit.asta.health.tools.water.model.domain.WaterTool
import fit.asta.health.tools.water.model.network.ModifiedWaterTool
import fit.asta.health.tools.water.view.component.WaterBottomSheet
import fit.asta.health.tools.water.viewmodel.WaterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class,)
@Composable
fun WaterHomeScreen(viewModel:WaterViewModel= hiltViewModel()) {

    val wT = viewModel.waterTool.collectAsState().value!!

    val changed by remember{mutableStateOf(viewModel.changedTgt.value)}

    Scaffold(
        topBar = {
        BottomNavigation(content = {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_exercise_back),
                        contentDescription = null,
                        Modifier.size(24.dp))
                }
                Text(text = "Water Tool",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center)
                IconButton(onClick = {  }) {
                    Icon(painter = painterResource(id = R.drawable.ic_physique),
                        contentDescription = null,
                        Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.primary)
                }
            }
        }, elevation = 10.dp, backgroundColor = MaterialTheme.colorScheme.onPrimary)
        },
        floatingActionButton = {
            if(changed!=null) {
                ExtendedFloatingActionButton(
                    onClick = {
                        viewModel.updateWaterTool(
                            ModifiedWaterTool(
                                id = wT.waterToolData.id,
                                uid = wT.waterToolData.uid,
                                type = 1,
                                code = "water",
                                tgt = changed.toString(),
                                prc = null,
                                wea = false
                            )
                        )
                    },
                    modifier = Modifier.height(35.dp)
                ) {
                    Text(text = "SAVE")
                }
            }                 },
        floatingActionButtonPosition = FabPosition.Center,
        content = {
        WaterBottomSheet(paddingValues = it)
    })

}