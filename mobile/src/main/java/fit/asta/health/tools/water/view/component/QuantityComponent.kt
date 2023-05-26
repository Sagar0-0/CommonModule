package fit.asta.health.tools.water.view.component
//
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.combinedClickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.GenericShape
//import androidx.compose.material.Button
//import androidx.compose.material.ButtonDefaults
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.modifier.modifierLocalConsumer
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.lifecycle.viewmodel.compose.viewModel
//import fit.asta.health.tools.water.viewmodel.WaterViewModel
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//
//@OptIn(ExperimentalCoroutinesApi::class, ExperimentalFoundationApi::class)
//
//@Composable
//fun QuantityComponent(
//    value: String="200 ml",
//    index: Int,
//    viewModel: WaterViewModel = hiltViewModel()
//) {
//    val choosenContainerValue by viewModel.selectedContainer.collectAsStateWithLifecycle()
//    val showSlider by viewModel.showSlider.collectAsStateWithLifecycle()
//    val selectedIndex by viewModel.sliderIndex.collectAsStateWithLifecycle()
//
//    val TriangleShape = GenericShape { size, _ ->
//        // 1)
//        moveTo(size.width / 2f, 0f)
//
//        // 2)
//        lineTo(size.width, size.height)
//
//        // 3)
//        lineTo(0f, size.height)
//    }
//
//    Column ( horizontalAlignment = Alignment.CenterHorizontally){
//        Column(horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center) {
//            Box(
//                modifier = Modifier
//                    .clip(shape = CircleShape)
//                    .size(72.dp)
//                    .background(
//                        color = if (choosenContainerValue == value) Color.Green else Color.Gray
//                    )
//                    .combinedClickable(
//                        onClick = {
//                            viewModel.updateSelectedContainer(value)
//                        },
//                        onLongClick = {
//                            viewModel.updateSliderVisibitlity(index,value.split(" ")[0].toInt())
//                        }
//                    )
//            ) {
//                Column (
//                    modifier = Modifier.fillMaxSize(),
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ){
//                    Text(
//                        text = value,
//                        fontSize = 13.sp,
//                        fontWeight = FontWeight.Light,
//                        textAlign = TextAlign.Center,
//                        color = Color.White
//                    )
//                }
//            }
//        }
//        if(showSlider && index==selectedIndex) {
//                Box(
//                    modifier = Modifier
//                        .clip(shape = TriangleShape)
//                        .size(20.dp)
//                        .background(color=Color(0xffc3b3f5))
//                )
//        }
//    }
//
//
//
//}