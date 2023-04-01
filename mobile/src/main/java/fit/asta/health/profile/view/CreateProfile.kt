@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import fit.asta.health.R
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.customSize
import fit.asta.health.common.ui.theme.imageSize
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.profile.model.domain.ThreeToggleSelections
import fit.asta.health.profile.model.domain.TwoToggleSelections
import fit.asta.health.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

data class ButtonListTypes(
    val buttonType: String,
)

@Composable
fun UserCircleImage(url: String, onClick: () -> Unit) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(start = spacing.extraSmall1, end = spacing.extraSmall1)
    ) {

        Image(
            painter = if (url.isEmpty()) {
                painterResource(id = R.drawable.userphoto)
            } else {
                rememberAsyncImagePainter(model = url)
            },
            contentDescription = null,
            modifier = Modifier
                .size(customSize.extraLarge5)
                .clip(shape = CircleShape)

        )

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.align(alignment = Alignment.BottomEnd)
        ) {
            Box {
                IconButton(onClick = onClick) {

                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = null,
                        modifier = Modifier
                            .size(customSize.largeSmall)
                            .clip(shape = CircleShape)
                    )

                }

            }
        }

    }
}

@Composable
fun MultiToggleWithTitle(
    modifier: Modifier = Modifier,
    selectionTypeText: (String)? = null,
    radioButtonList: List<ButtonListTypes>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(cardElevation.extraSmall)
    ) {
        MultiToggleLayout(selectionTypeText, radioButtonList, selectedOption, onOptionSelected)
    }

}

@Composable
fun MultiToggleLayout(
    selectionTypeText: String?,
    radioButtonList: List<ButtonListTypes>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
) {

    Column(Modifier.fillMaxWidth()) {

        if (!selectionTypeText.isNullOrEmpty()) {

            Spacer(modifier = Modifier.height(spacing.small))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.medium)
            ) {
                Text(
                    text = selectionTypeText,
                    color = Color(0x99000000),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }


        LazyVerticalGrid(
            columns = GridCells.Fixed(radioButtonList.size),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.small)
                .height(customSize.largeSmall),
            userScrollEnabled = false,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            radioButtonList.forEach { text ->
                item {
                    Row(verticalAlignment = CenterVertically, modifier = Modifier.weight(1f)) {
                        RadioButton(
                            selected = (text.buttonType == selectedOption),
                            onClick = { onOptionSelected(text.buttonType) },
                            modifier = Modifier.size(customSize.largeSmall)
                        )
                        Text(
                            text = text.buttonType,
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Right
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TwoTogglesGroup(
    viewModel: ProfileViewModel = hiltViewModel(),
    selectionTypeText: String?,
    selectedOption: TwoToggleSelections?,
    onStateChange: (TwoToggleSelections) -> Unit,
    firstOption: String = "Yes",
    secondOption: String = "No",
) {

    Column(Modifier.fillMaxWidth()) {

        if (!selectionTypeText.isNullOrEmpty()) {

            Spacer(modifier = Modifier.height(spacing.small))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.medium)
            ) {
                Text(
                    text = selectionTypeText,
                    color = Color(0x99000000),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.small)
                .height(customSize.extraLarge),
            userScrollEnabled = false,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            items(listOf(TwoToggleSelections.First, TwoToggleSelections.Second)) { option ->
                Row(verticalAlignment = CenterVertically, modifier = Modifier.weight(1f)) {

                    RadioButton(selected = selectedOption == option, onClick = {
                        onStateChange(option)
                    })
                    Text(
                        text = when (option) {
                            TwoToggleSelections.First -> firstOption
                            TwoToggleSelections.Second -> secondOption
                        }, style = MaterialTheme.typography.labelSmall, textAlign = TextAlign.Right
                    )

                }
            }

        }


    }

}

@Composable
fun ThreeTogglesGroups(
    viewModel: ProfileViewModel = hiltViewModel(),
    selectionTypeText: String?,
    selectedOption: ThreeToggleSelections?,
    onStateChange: (ThreeToggleSelections) -> Unit,
    firstOption: String = "Male",
    secondOption: String = "Female",
    thirdOption: String = "Others",
) {

    Column(Modifier.fillMaxWidth()) {

        if (!selectionTypeText.isNullOrEmpty()) {

            Spacer(modifier = Modifier.height(spacing.small))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.medium)
            ) {
                Text(
                    text = selectionTypeText,
                    color = Color(0x99000000),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.small)
                .height(customSize.extraLarge),
            userScrollEnabled = false,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            items(
                listOf(
                    ThreeToggleSelections.First,
                    ThreeToggleSelections.Second,
                    ThreeToggleSelections.Third
                )
            ) { option ->
                Row(verticalAlignment = CenterVertically, modifier = Modifier.weight(1f)) {

                    RadioButton(selected = selectedOption == option, onClick = {
                        onStateChange(option)
                    })
                    Text(
                        text = when (option) {
                            ThreeToggleSelections.First -> firstOption
                            ThreeToggleSelections.Second -> secondOption
                            ThreeToggleSelections.Third -> thirdOption
                        }, style = MaterialTheme.typography.labelSmall, textAlign = TextAlign.Right
                    )

                }
            }

        }


    }
}


@Composable
fun PrivacyStatement() {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.privacy),
                contentDescription = null,
                modifier = Modifier.size(imageSize.extraMedium)
            )
        }
        Spacer(modifier = Modifier.width(spacing.medium))
        Column {
            Text(
                text = "Privacy Statement",
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xDE000000)
            )
            Spacer(modifier = Modifier.height(spacing.extraSmall))
            Text(
                text = "We value your privacy. We are committed to protecting your privacy and ask for your consent for the use of your personal health information as required during you health care.",
                color = Color(0xDE000000),
                style = MaterialTheme.typography.bodySmall,
                softWrap = true
            )
        }
    }
}

@Composable
fun UserConsent() {

    val checkedState = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
                modifier = Modifier.size(imageSize.extraMedium)
            )
        }
        Spacer(modifier = Modifier.width(spacing.medium))
        Column {
            Text(
                text = "I CONSENT TO THE USE OF MY PERSONAL HEALTH INFORMATION AS REQUIRED DURING YOUR HEALTH CARE.",
                color = Color(0xFF375369),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
