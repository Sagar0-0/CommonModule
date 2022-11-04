package fit.asta.health.tools.water.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.tools.sunlight.view.components.bottomsheet.collapsed.ui.DividerLineCenter
import fit.asta.health.tools.sunlight.view.components.bottomsheet.expanded.ui.AddMoreWater

@Composable
fun WaterBottomSheetContent() {

    Box {
        Column(Modifier
            .fillMaxWidth()
            .height(400.dp)
            .verticalScroll(rememberScrollState())) {

            Spacer(modifier = Modifier.height(16.dp))

            DividerLineCenter()

            Spacer(modifier = Modifier.height(32.dp))

            androidx.compose.material3.Text("BEVERAGES",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 22.4.sp,
                color = Color.White)

            Spacer(modifier = Modifier.height(16.dp))

            BeveragesLayout()

            Spacer(modifier = Modifier.height(16.dp))

            androidx.compose.material3.Text("QUANTITY",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 22.4.sp,
                color = Color.White)

            Spacer(modifier = Modifier.height(16.dp))

            QuantityLayout()

            Spacer(modifier = Modifier.height(16.dp))

            androidx.compose.material3.Text("PRACTICE",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 22.4.sp,
                color = Color.White)

            Spacer(modifier = Modifier.height(16.dp))

            WaterListLayout()

            Spacer(modifier = Modifier.height(16.dp))

            AddMoreWater()

            Spacer(modifier = Modifier.height(16.dp))

        }
    }

}

@Preview
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WaterBottomSheet() {

    val scaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = rememberBottomSheetState(
            BottomSheetValue.Collapsed))

    BottomSheetScaffold(sheetContent = { WaterBottomSheetContent() },
        scaffoldState = scaffoldState) {
        WaterHomeScreen()
    }

}