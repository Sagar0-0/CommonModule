package fit.asta.health.tools.water.view

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import fit.asta.health.tools.water.view.component.WaterBottomSheetGridView



@Composable
fun VerticalLines(dates: List<String>,paddingValues: Dp) {
//    val textSize = with(LocalDensity.current) { 10.dp.toPx() }
//    val textPaint = android.graphics.Paint().apply {
//        color =  0xffff47586B.toInt()
//        textAlign = android.graphics.Paint.Align.CENTER
//        this.textSize = textSize
//    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .padding(paddingValues)
    ) {
        val drawPadding: Float = with(LocalDensity.current) { 10.dp.toPx() }
        Canvas(modifier = Modifier.fillMaxSize()) {
            val yStart = 0f
            val yEnd = size.height
            val distance: Float = (size.width.minus(2 * drawPadding)).div(dates.size.minus(1))
            dates.forEachIndexed { index, step ->
                drawLine(
                    color = Color.Black,
                    start = Offset(x = drawPadding + index.times(distance), y = yStart),
                    end = Offset(x = drawPadding + index.times(distance), y = yEnd)
                )
//                if (index.rem(2) == 1) {
//                    this.drawContext.canvas.nativeCanvas.drawText(
//                        step,
//                        drawPadding + index.times(distance) + 10f,
//                        size.height,
//                        textPaint
//                    )
//                }
            }
        }
    }
}

@Composable
fun ForecastSlider(dates: List<String>) {
    var sliderValue by remember{mutableStateOf(0f)}
    Surface(color = Color.Gray) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(10.dp)) {
            VerticalLines(dates, paddingValues = 10.dp)
            Slider(modifier = Modifier.fillMaxWidth(), value = sliderValue, onValueChange = {sliderValue=it;Log.i("Slider Value",it.toString())},valueRange = 0f..dates.size.minus(1).toFloat(),
                steps = dates.size.minus(2),)
        }
    }

}


@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Preview
@Composable
fun NewBottomSheet() {

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(
            initialValue = BottomSheetValue.Collapsed
        )
    )
    var s1 by remember{ mutableStateOf(false) }
    var s2 by remember{ mutableStateOf(false) }
    var s3 by remember{ mutableStateOf(false) }
    var s4 by remember{ mutableStateOf(false) }
    var open by remember{ mutableStateOf(false) }


    BottomSheetScaffold(
        sheetContent = { WaterBottomSheetGridView() },
        scaffoldState = scaffoldState, sheetPeekHeight = 180.dp
    ) {
        Surface(
            modifier = Modifier.padding(it).fillMaxWidth()
        )
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("HI")
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        Button(
                            onClick = {
                                if (open){
                                    s1=false;s2=false;s3=false;s4=false;open=false
                                }else{
                                    s1=true;s2=false;s3=false;s4=false;open=true
                                }

                                      },
                            modifier = Modifier
                                .clip(shape = CircleShape)
                                .size(72.dp)
                                .shadow(10.dp)
                                .border(1.dp, Color.Black, shape = CircleShape),
                            elevation = ButtonDefaults.elevation(defaultElevation = 100.dp),
                            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Gray)
                        ) {
                            Text(
                                text = "500 ml",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center
                            )
                        }
                        AnimatedVisibility(visible = s1) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(customTriangleShape())
                                    .background(Color.Gray)
                            ) {
                                //put your box content here

                            }
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        Button(
                            onClick = { s1=false;s2=true;s3=false;s4=false;open=true },
                            modifier = Modifier
                                .clip(shape = CircleShape)
                                .size(72.dp)
                                .shadow(10.dp)
                                .border(1.dp, Color.Black, shape = CircleShape),
                            elevation = ButtonDefaults.elevation(defaultElevation = 100.dp),
                            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Gray)
                        ) {
                            androidx.compose.material3.Text(
                                text = "500 ml",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center
                            )
                        }
                        AnimatedVisibility(visible = s2) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(customTriangleShape())
                                    .background(Color.Gray)
                            ) {
                                //put your box content here

                            }
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        Button(
                            onClick = { s1=false;s2=false;s3=true;s4=false;open=true },
                            modifier = Modifier
                                .clip(shape = CircleShape)
                                .size(72.dp)
                                .shadow(10.dp)
                                .border(1.dp, Color.Black, shape = CircleShape),
                            elevation = ButtonDefaults.elevation(defaultElevation = 100.dp),
                            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Gray)
                        ) {
                            androidx.compose.material3.Text(
                                text = "500 ml",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center
                            )
                        }
                        AnimatedVisibility(visible = s3) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(customTriangleShape())
                                    .background(Color.Gray)
                            ) {
                                //put your box content here

                            }
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        Button(
                            onClick = { s1=false;s2=false;s3=false;s4=true;open=true },
                            modifier = Modifier
                                .clip(shape = CircleShape)
                                .size(72.dp)
                                .shadow(10.dp)
                                .border(1.dp, Color.Black, shape = CircleShape),
                            elevation = ButtonDefaults.elevation(defaultElevation = 100.dp),
                            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Gray)
                        ) {
                            androidx.compose.material3.Text(
                                text = "500 ml",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center
                            )
                        }
                        AnimatedVisibility(visible = s4) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(customTriangleShape())
                                    .background(Color.Gray)
                            ) {
                                //put your box content here

                            }
                        }

                    }
                }
                AnimatedVisibility(visible = open) {
                    ForecastSlider(dates = listOf("hi", "hello","","","light"))
                }
            }
        }
    }
}

class customTriangleShape : Shape {

    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(size.width / 2f, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}

