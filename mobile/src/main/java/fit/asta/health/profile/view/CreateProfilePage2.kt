package fit.asta.health.profile.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import fit.asta.health.profile.view.components.AddIcon
import fit.asta.health.profile.view.components.ChipsOnCards
import fit.asta.health.ui.spacing

@Composable
fun SelectionCardCreateProfile(
    cardType: String,
    cardList: List<String>,
    radioButtonList: List<ButtonListTypes>,
    checkedState: (MutableState<Boolean>)? = null,
) {


    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioButtonList[0]) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.medium)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = cardType,
                        fontSize = 14.sp,
                        color = Color(0xff132839),
                        fontWeight = FontWeight.Medium
                    )
                }
                AddIcon(onClick = {/*Todo*/ })
            }

            Spacer(modifier = Modifier.height(spacing.medium))

            LazyVerticalGrid(
                columns = GridCells.Fixed(radioButtonList.size),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp),
                userScrollEnabled = false
            ) {
                radioButtonList.forEach { text ->
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            RadioButton(selected = (text == selectedOption),
                                onClick = { onOptionSelected(text) })
                            Text(
                                text = text.buttonType,
                                style = MaterialTheme.typography.labelSmall,
                                textAlign = TextAlign.Right
                            )
                        }
                    }
                }
            }

            if (selectedOption == radioButtonList[0]) {
                Spacer(modifier = Modifier.height(16.dp))
                FlowRow(mainAxisSpacing = 8.dp, crossAxisSpacing = 4.dp) {
                    cardList.forEach {
                        ChipsOnCards(textOnChip = it, checkedState = checkedState)
                    }
                }
            }

        }
    }
}

@Composable
fun OnlyChipSelectionCard(
    cardType: String,
    cardList: List<String>,
    checkedState: (MutableState<Boolean>)? = null,
) {


    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.medium)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = cardType,
                        fontSize = 14.sp,
                        color = Color(0xff132839),
                        fontWeight = FontWeight.Medium
                    )
                }
                AddIcon(onClick = {/*Todo*/ })
            }
            Spacer(modifier = Modifier.height(spacing.medium))

            FlowRow(mainAxisSpacing = 8.dp, crossAxisSpacing = 4.dp) {
                cardList.forEach {
                    ChipsOnCards(textOnChip = it, checkedState = checkedState)
                }
            }

        }
    }
}

@Composable
fun SelectionOutlineButton(
    cardType: String,
    cardList: List<String>,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.medium)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = cardType,
                        style = MaterialTheme.typography.titleSmall,
                        color = Color(0xff132839),
                    )
                }
                AddIcon()
            }

            Spacer(modifier = Modifier.height(spacing.medium))


            FlowRow(
                mainAxisSpacing = spacing.small,
                crossAxisSpacing = spacing.extraSmall,
                modifier = Modifier.fillMaxWidth()
            ) {
                cardList.forEach {
                    OutlineButton(textOnChip = it, modifier = Modifier.weight(1f))
                }
            }

        }
    }
}

@Composable
fun OutlineButton(textOnChip: String, modifier: Modifier = Modifier) {

    OutlinedButton(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.outlinedButtonColors(disabledContentColor = Color(0xffE7EAED)),
        shape = RoundedCornerShape(100.dp),
        modifier = modifier
    ) {
        Text(
            text = textOnChip,
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xDE000000)
        )
    }

}
