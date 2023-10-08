package fit.asta.health.feature.profile.create.view.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppDivider
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.texts.CaptionTexts

@Composable
fun Stepper(
    modifier: Modifier = Modifier,
    step: Int,
    isCompete: Boolean,
    isCurrent: Boolean,
    isComplete: Boolean,
    isRainbow: Boolean,
    stepDescription: String,
    unSelectedColor: Color,
    selectedColor: Color?,
    icons: ImageVector,
    logic: () -> Unit,
    detailsColor: Color,
    phyColor: Color,
    healthColor: Color,
    lifeStyleColor: Color,
    dietColor: Color,
    isDetailValid: Boolean,
    isPhyValid: Boolean,
    isHealthValid: Boolean,
    isLSValid: Boolean,
    isDietValid: Boolean,
) {

    val rainBowColor = Brush.linearGradient(
        listOf(
            Color.Magenta,
            Color.Blue,
            Color.Cyan,
            Color.Green,
            Color.Yellow,
            Color.Red,
        )
    )

    val transition = updateTransition(isCompete, label = "")

    val innerCircleColor by transition.animateColor(label = "innerCircleColor") {
        if (it) selectedColor ?: AppTheme.colors.primary else unSelectedColor
    }

    val txtColor by transition.animateColor(label = "txtColor") {
        if (it || isCurrent) selectedColor ?: AppTheme.colors.primary else unSelectedColor
    }

    val color by transition.animateColor(label = "color") {
        if (it || isCurrent) selectedColor ?: AppTheme.colors.primary else Color.Gray
    }

    val borderStroke: BorderStroke = if (isRainbow) {
        BorderStroke(2.dp, rainBowColor)
    } else {
        BorderStroke(2.dp, color)
    }

    ConstraintLayout(modifier = modifier) {

        val (circle, txt, line) = createRefs()

        AppSurface(shape = CircleShape,
            border = borderStroke,
            modifier = Modifier
                .size(AppTheme.boxSize.level4)
                .constrainAs(circle) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }) {
            Box(contentAlignment = Alignment.Center) {
                AppIconButton(
                    imageVector = if (isCompete) {
                        icons
                    } else {
                        icons
                    }, onClick = logic, iconTint = if (isCompete) {
                        when (step) {
                            1 -> {
                                detailsColor
                            }

                            2 -> {
                                phyColor
                            }

                            3 -> {
                                healthColor
                            }

                            4 -> {
                                lifeStyleColor
                            }

                            else -> {
                                dietColor
                            }
                        }
                    } else {
                        AppTheme.colors.onSurface
                    }, modifier = modifier.padding(AppTheme.spacing.level1)
                )
            }
        }
        CaptionTexts.Level5(
            text = stepDescription, color = txtColor,
            modifier = Modifier.constrainAs(txt) {
                top.linkTo(circle.bottom, margin = 3.dp)
                start.linkTo(circle.start)
                end.linkTo(circle.end)
                bottom.linkTo(parent.bottom)
            },
        )
        if (!isComplete) {
            //Line
            if (isRainbow) {
                AppDivider(
                    modifier = Modifier
                        .constrainAs(line) {
                            top.linkTo(circle.top)
                            bottom.linkTo(circle.bottom)
                            start.linkTo(circle.end)
                        }
                        .background(rainBowColor),
                    thickness = 1.dp,
                )
            } else {
                AppDivider(
                    modifier = Modifier.constrainAs(line) {
                        top.linkTo(circle.top)
                        bottom.linkTo(circle.bottom)
                        start.linkTo(circle.end)
                    },
                    color = innerCircleColor,
                    thickness = 1.dp,
                )
            }
        }
    }
}