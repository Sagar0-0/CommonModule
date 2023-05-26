package fit.asta.health.tools.water.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.R
import fit.asta.health.tools.water.view.component.WaterBottomSheet
import fit.asta.health.tools.water.viewmodel.WaterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@Preview
@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun WaterHomeScreen(viewModel: WaterViewModel = hiltViewModel()) {

//    val showSaveButton by viewModel.saveData.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            BottomNavigation(content = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_exercise_back),
                            contentDescription = null,
                            Modifier.size(24.dp)
                        )
                    }
                    Text(
                        text = "Water Tool",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_physique),
                            contentDescription = null,
                            Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }, elevation = 10.dp, backgroundColor = MaterialTheme.colorScheme.onPrimary)
        },
        floatingActionButton = {
//            if (showSaveButton) {
//                ExtendedFloatingActionButton(
//                    onClick = {
//                        //viewModel.onSaved()
//                    }, modifier = Modifier.height(35.dp)
//                ) {
//                    Text(text = "SAVE")
//                }
//            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = {
            WaterBottomSheet(paddingValues = it)
        }
    )

}