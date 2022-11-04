package fit.asta.health.tools.water.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.tools.sunlight.view.components.bottomsheet.collapsed.ui.BottomSheetButtonLayout
import fit.asta.health.tools.sunlight.view.components.bottomsheet.expanded.ui.AddMoreWater


@Preview
@Composable
fun BottomSheetContent() {

    Column(Modifier.fillMaxWidth()) {

        Spacer(modifier = Modifier.height(32.dp))

        Text("BEVERAGES",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 22.4.sp,
            color = Color.White)

        Spacer(modifier = Modifier.height(16.dp))

        BeveragesLayout()

        Spacer(modifier = Modifier.height(16.dp))

        Text("QUANTITY",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 22.4.sp,
            color = Color.White)

        Spacer(modifier = Modifier.height(16.dp))

        QuantityLayout()

        Spacer(modifier = Modifier.height(16.dp))

        Text("PRACTICE",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 22.4.sp,
            color = Color.White)

        Spacer(modifier = Modifier.height(16.dp))

        WaterListLayout()

        Spacer(modifier = Modifier.height(16.dp))

        AddMoreWater()

        Spacer(modifier = Modifier.height(16.dp))

        BottomSheetButtonLayout()

        Spacer(modifier = Modifier.height(16.dp))
    }

}