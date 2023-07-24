package fit.asta.health.tools.sunlight.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fit.asta.health.tools.view.components.ItemData
import fit.asta.health.tools.view.components.ItemList

@Composable
fun SPFLevel(navController: NavController) {

    val itemDataListDemo = remember {
        mutableStateListOf(
            ItemData(1, "SPF 15", bgColor = Color(0x66959393)),
            ItemData(2, "SPF 20", bgColor = Color(0x66959393)),
            ItemData(3, "SPF 30", bgColor = Color(0x66959393)),
            ItemData(4, "SPF 40", bgColor = Color(0x66959393)),
            ItemData(5, "SPF 50", bgColor = Color(0x66959393)),
            ItemData(6, "SPF 70 ", bgColor = Color(0x66959393)),
            ItemData(7, "None", bgColor = Color(0x66959393))
        )
    }

    ItemList(list = itemDataListDemo,
        rowTitle = "Choose SPF level of your sunscreen",
        content = { SPFLevelContent() })
}


@Composable
fun SPFLevelContent() {

    Column(Modifier.fillMaxWidth()) {
        Card(modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(Color(0x66959593))
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Row(Modifier.fillMaxWidth()) {
                    Text(text = "Outdoor Timings",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 22.4.sp,
                        color = Color.White)
                }
                Spacer(modifier = Modifier.height(16.dp))
                TimeSelection()
                Spacer(modifier = Modifier.height(16.dp))
                Row(Modifier.fillMaxWidth()) {
                    Text(text = "Based on the outdoor timing, Clothing and Location inputs we recommend you sunscreen with SPF 40",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 19.6.sp,
                        color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(Color(0x66959593))
        ) {
            Text(text = "Wearing sunscreen is one of the best — and easiest — ways to protect your skin's appearance and health at any age.Sunscreen may help prevent the sun's rays from causing photographing and skin cancer.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun TimeSelection() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f)) {
            TimeSelectionLayout(title = "Start Time")
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f)) {
            TimeSelectionLayout(title = "End Time")
        }
    }
}

@Composable
fun TimeSelectionLayout(title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 19.6.sp,
            color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /*TODO*/ },
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(2.dp, color = Color(0x99E4E4E4))) {
            Text(text = "10:00 am",
                color = Color(0x99000000),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(vertical = 18.5.dp))
        }
    }
}