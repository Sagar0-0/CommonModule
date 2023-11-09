package fit.asta.health.ui.common.cards.small

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.cards.AppElevatedCard
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R
import fit.asta.health.ui.common.cards.small.canvas.CanvasCardParent

// Preview Function
@Preview("Light Button")
@Preview(
    name = "Dark Button",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    AppTheme {
        AppSurface {
            SmallCardDemo()
        }
    }
}

@Composable
fun SmallCardDemo() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SmallCard1(
                cardColor = CardDefaults.elevatedCardColors(containerColor = Color(0xFF90D39B))
            )
        }

        item {
            SmallCard2(
                cardColor = CardDefaults.elevatedCardColors(containerColor = Color(0xFF90D39B))
            )
        }

        item {
            SmallCard3(
                cardColor = CardDefaults.elevatedCardColors(containerColor = Color(0xFF90D39B))
            )
        }

        item {
            CanvasCardParent()
        }
    }
}

@Composable
private fun SmallCard1(
    cardColor: CardColors
) {
    AppElevatedCard(
        modifier = Modifier.padding(horizontal = 16.dp),
        colors = cardColor,
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = AppTheme.elevation.level2,
            pressedElevation = AppTheme.elevation.level2,
            focusedElevation = AppTheme.elevation.level2,
            hoveredElevation = AppTheme.elevation.level3,
            draggedElevation = AppTheme.elevation.level4,
            disabledElevation = AppTheme.elevation.level2
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AppLocalImage(painter = painterResource(id = R.drawable.star_foreground))

                Column {
                    TitleTexts.Level4(text = "Water")
                    CaptionTexts.Level1(text = "tags")
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                BodyTexts.Level2(text = "11.00AM")
                AppIconButton(imageVector = Icons.Default.ArrowDropDown) {
                    // TODO :- Collapsing / Expanding Logic
                }
            }
        }
    }
}


@Composable
private fun SmallCard2(
    cardColor: CardColors
) {
    AppElevatedCard(
        modifier = Modifier.padding(horizontal = 16.dp),
        colors = cardColor,
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = AppTheme.elevation.level2,
            pressedElevation = AppTheme.elevation.level2,
            focusedElevation = AppTheme.elevation.level2,
            hoveredElevation = AppTheme.elevation.level3,
            draggedElevation = AppTheme.elevation.level4,
            disabledElevation = AppTheme.elevation.level2
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AppLocalImage(painter = painterResource(id = R.drawable.star_foreground))

                Column {
                    CaptionTexts.Level1(text = "tags")
                    TitleTexts.Level4(text = "Water")
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                BodyTexts.Level2(text = "11.00AM")
                AppIconButton(imageVector = Icons.Default.Menu) {
                    // TODO :- Collapsing / Expanding Logic
                }
            }
        }
    }
}

@Composable
private fun SmallCard3(
    cardColor: CardColors
) {
    AppElevatedCard(
        modifier = Modifier.padding(horizontal = 32.dp),
        colors = cardColor,
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = AppTheme.elevation.level2,
            pressedElevation = AppTheme.elevation.level2,
            focusedElevation = AppTheme.elevation.level2,
            hoveredElevation = AppTheme.elevation.level3,
            draggedElevation = AppTheme.elevation.level4,
            disabledElevation = AppTheme.elevation.level2
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AppLocalImage(
                    painter = painterResource(id = R.drawable.star_foreground)
                )

                Column {
                    CaptionTexts.Level3(text = "tags")
                    TitleTexts.Level4(text = "Water")
                    BodyTexts.Level3(text = "Need to do this for 10 minutes xxxxxxxxxxxxxxxxxxxxx")
                }
            }

            Row(
//                modifier = Modifier.weight(.4f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                BodyTexts.Level3(text = "11.00AM")
                AppIconButton(imageVector = Icons.Default.Menu) {
                    // TODO :- Collapsing / Expanding Logic
                }
            }
        }
    }
}


@Composable
fun SmallCard4() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .height(100.dp)
    ) {

        // Radius of the Circle
        val circleDiameter = 48.dp.toPx()

        // Rectangle covering the area above the Arc
        drawRoundRect(
            color = Color(0xFF90D39B),
            cornerRadius = CornerRadius(8.dp.toPx()),
            topLeft = Offset.Zero,
            size = Size(
                width = size.width,
                height = size.height - circleDiameter
            )
        )

        // Rectangle covering the left side area of the Arc
        drawRoundRect(
            color = Color(0xFF90D39B),
            cornerRadius = CornerRadius(8.dp.toPx()),
            topLeft = Offset.Zero,
            size = Size(
                width = size.width - circleDiameter,
                height = size.height
            )
        )

        // Path variable to make the arc above the Circle
        val path = Path()

        // Defining the Path Variable path to be taken
        path.moveTo(8.dp.toPx(), 8.dp.toPx())
        path.lineTo(size.width, 8.dp.toPx())
        path.lineTo(
            x = size.width,
            y = size.height - circleDiameter
        )

        // This function makes the path curve in the Arc
        path.quadraticBezierTo(
            x1 = size.width - circleDiameter,
            y1 = size.height - circleDiameter,
            x2 = size.width - circleDiameter,
            y2 = size.height
        )

        // Soothing Path extra lines
        path.lineTo(
            x = 8.dp.toPx(),
            y = size.height
        )

        // Drawing the Path
        drawPath(
            path = path,
            color = Color(0xFF90D39B),
        )

        // This draws the Circle
        drawCircle(
            color = Color.Red,
            radius = circleDiameter / 2f,
            center = Offset(
                x = size.width - 8.dp.toPx(),
                y = size.height - 8.dp.toPx()
            )
        )
    }
}


@Composable
fun SmallCard5(cardColor: CardColors) {

    var pressed by remember { mutableIntStateOf(100) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .height(pressed.dp)
            .drawBehind {

                // Radius of the Circle
                val circleDiameter = 48.dp.toPx()
                drawElements(circleDiameter)
            },
        contentAlignment = Alignment.BottomEnd
    ) {
        Canvas(
            modifier = Modifier.clickable {
                pressed = if (pressed == 100) 200 else 100
            }
        ) {

            // Radius of the Circle
            val circleDiameter = 48.dp.toPx()
            drawCircle(circleDiameter)
        }
    }
}

fun DrawScope.drawElements(circleDiameter: Float) {

    // Rectangle covering the area above the Arc
    drawRoundRect(
        color = Color(0xFF90D39B),
        cornerRadius = CornerRadius(8.dp.toPx()),
        topLeft = Offset.Zero,
        size = Size(
            width = size.width,
            height = size.height - circleDiameter
        )
    )

    // Rectangle covering the left side area of the Arc
    drawRoundRect(
        color = Color(0xFF90D39B),
        cornerRadius = CornerRadius(8.dp.toPx()),
        topLeft = Offset.Zero,
        size = Size(
            width = size.width - circleDiameter,
            height = size.height
        )
    )

    // Path variable to make the arc above the Circle
    val path = Path()

    // Defining the Path Variable path to be taken
    path.moveTo(8.dp.toPx(), 8.dp.toPx())
    path.lineTo(size.width, 8.dp.toPx())
    path.lineTo(
        x = size.width,
        y = size.height - circleDiameter
    )

    // This function makes the path curve in the Arc
    path.quadraticBezierTo(
        x1 = size.width - circleDiameter,
        y1 = size.height - circleDiameter,
        x2 = size.width - circleDiameter,
        y2 = size.height
    )

    // Soothing Path extra lines
    path.lineTo(
        x = 8.dp.toPx(),
        y = size.height
    )

    // Drawing the Path
    drawPath(
        path = path,
        color = Color(0xFF90D39B),
    )
}

fun DrawScope.drawCircle(circleDiameter: Float) {

    // This draws the Circle
    drawCircle(
        color = Color.Red,
        radius = circleDiameter / 2f,
        center = Offset(
            x = size.width - 8.dp.toPx(),
            y = size.height - 8.dp.toPx()
        )
    )
}