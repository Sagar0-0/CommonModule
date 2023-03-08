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
import com.google.accompanist.flowlayout.FlowRow
import fit.asta.health.profile.view.components.AddIcon
import fit.asta.health.profile.view.components.ChipsOnCards
import fit.asta.health.testimonials.view.theme.cardElevation
import fit.asta.health.ui.customSize
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
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(cardElevation.extraSmall)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Spacer(modifier = Modifier.height(spacing.small))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.medium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = cardType,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        style = MaterialTheme.typography.titleSmall
                    )
                }

                AddIcon(onClick = {})

            }

            MultiToggleLayout(
                selectionTypeText = null,
                radioButtonList = radioButtonList,
                selectedOption = selectedOption,
                onOptionSelected = onOptionSelected
            )

            if (selectedOption == radioButtonList[0]) {

                FlowRow(
                    mainAxisSpacing = spacing.minSmall,
                    modifier = Modifier.padding(start = spacing.medium),
                ) {
                    cardList.forEach {
                        ChipsOnCards(textOnChip = it, checkedState = checkedState)
                    }
                }


            }
            Spacer(modifier = Modifier.height(spacing.small))
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
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(cardElevation.extraSmall)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Spacer(modifier = Modifier.height(spacing.small))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.medium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = cardType,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                AddIcon(onClick = {/*Todo*/ })
            }

            Spacer(modifier = Modifier.height(spacing.small))

            FlowRow(
                mainAxisSpacing = spacing.minSmall,
                modifier = Modifier.padding(start = spacing.medium),
            ) {
                cardList.forEach {
                    ChipsOnCards(textOnChip = it, checkedState = checkedState)
                }
            }

            Spacer(modifier = Modifier.height(spacing.small))

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
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(cardElevation.extraSmall)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Spacer(modifier = Modifier.height(spacing.small))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.medium),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = cardType,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.titleSmall
                )

            }

            Spacer(modifier = Modifier.height(spacing.small))

            LazyVerticalGrid(
                columns = GridCells.Fixed(cardList.size),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.small)
                    .height(customSize.extraLarge),
                userScrollEnabled = false,
                horizontalArrangement = Arrangement.spacedBy(spacing.extraSmall),
            ) {
                cardList.forEach { text ->
                    item {
                        OutlineButton(textOnChip = text, modifier = Modifier.weight(1f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(spacing.small))
        }
    }
}

@Composable
fun OutlineButton(textOnChip: String, modifier: Modifier = Modifier) {

    OutlinedButton(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.outlinedButtonColors(disabledContentColor = Color(0xffE7EAED)),
        shape = RoundedCornerShape(customSize.extraLarge4),
        modifier = modifier
    ) {
        Text(
            text = textOnChip,
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xDE000000)
        )
    }

}
