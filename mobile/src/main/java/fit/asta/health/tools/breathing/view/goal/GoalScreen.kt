package fit.asta.health.tools.breathing.view.goal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdsClick
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.tools.walking.model.ListItem

@Composable
fun GoalsScreen(
    onClick: (List<String>) -> Unit,
    onBack: () -> Unit
) {
    val goals  = listOf("De-Stress", "Fall Asleep", "Anxiety", "Clear your Mind")
    var items by remember {
        mutableStateOf(goals.map {
            ListItem(title = it, isSelected = false)
        })
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BottomNavigation(
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(onClick = onBack) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_exercise_back),
                                contentDescription = null,
                                Modifier.size(24.dp)
                            )
                        }
                        Text(
                            text = "Goals",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_physique),
                                contentDescription = null,
                                Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                elevation = 10.dp,
                backgroundColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "Select goals for your breathing exercise",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    style = androidx.compose.material.MaterialTheme.typography.body1
                )
            }
            items(count = items.size) { indexNumber ->
                Surface(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable {
                            items = items.mapIndexed { j, item ->
                                if (indexNumber == j) {
                                    item.copy(isSelected = !item.isSelected)
                                } else item
                            }
                            onClick(items.filter { it.isSelected }.map { it.title })
                        },
                    color =if (!items[indexNumber].isSelected) {
                        Color(0xFFE9D7F7)
                    } else {
                        Color(0xFF7415BD)
                    },
                    shape = RoundedCornerShape(corner = CornerSize(15.dp)),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .weight(0.5f),
                            text = items[indexNumber].title,
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 25.sp
                        )

                        Icon(
                            modifier = Modifier.weight(0.5f),
                            imageVector = Icons.Default.AdsClick,
                            contentDescription = null
                        )
                    }
                }

            }
        }
    }

}