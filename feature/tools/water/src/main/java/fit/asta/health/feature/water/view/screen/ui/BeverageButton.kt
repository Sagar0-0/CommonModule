package fit.asta.health.feature.water.view.screen.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.molecular.cards.AppElevatedCardWithColor
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts


@Composable
fun BeverageInfoCard(
    name: String,
    containerColor: Color,
    contentColor: Color,
    resId: Int,
    quantity: Int,
    onClick: () -> Unit,
    onClickBeverage: () -> Unit
) {
    var showUndoButton by remember {
        mutableStateOf(false)
    }
    AppElevatedCardWithColor(
        contentColor = contentColor,
        containerColor = containerColor,
        modifier = Modifier
            .scale(1.1f)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.clickable {
                onClick()
            },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppIcon(
                    painter = painterResource(id = resId),
                    tint = contentColor,
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(CircleShape)
                )
                BodyTexts.Level2(
                    text = " $quantity  ml",
                    color = contentColor,
                    modifier = Modifier.padding(
                        8.dp,end = 16.dp
                    )
                )
            }
            HeadingTexts.Level4(
                text = name,
                modifier = Modifier.padding()
            )
        }
        AppIcon(
            imageVector = Icons.Default.Edit, contentDescription = "Quantity",
            tint = containerColor,
            modifier = Modifier
                .scale(0.8f)
                .background(color = Color.White,shape = RoundedCornerShape(topEnd = 8.dp))
                .clickable {
                    showUndoButton = !showUndoButton
                }
        )
        AnimatedVisibility(visible = showUndoButton) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.clickable {

                    }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AppIcon(
                            imageVector = Icons.AutoMirrored.Filled.Undo,
                            modifier = Modifier
                                .padding()
                                .scale(0.5f),
                        )
                        CaptionTexts.Level4(
                            text = "Undo", textAlign = TextAlign.Center,
                            modifier = Modifier.padding(start = 6.dp,end = 6.dp)
                        )
                    }
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.clickable {
                        onClickBeverage()
                    }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AppIcon(
                            imageVector = Icons.Default.Add,
                            modifier = Modifier
                                .padding()
                                .scale(0.5f)
                        )
                        CaptionTexts.Level4(
                            text = "Add", textAlign = TextAlign.Center,
                            modifier = Modifier.padding(start = 4.dp,end = 4.dp)
                        )
                    }
                }
            }

        }
    }
}