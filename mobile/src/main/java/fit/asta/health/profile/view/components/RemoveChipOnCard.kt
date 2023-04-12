@file:OptIn(ExperimentalMaterialApi::class)

package fit.asta.health.profile.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RemoveChipOnCard(
    textOnChip: String,
    checkedState: (MutableState<Boolean>)? = null,
    isEnabled: Boolean? = false,
    onClick: () -> Unit = {},
) {

    checkedState?.let {
        Chip(
            onClick = onClick,
            shape = RoundedCornerShape(32.dp),
            colors = ChipDefaults.chipColors(backgroundColor = Color(0x80D6D6D6)),
            enabled = it.value
        ) {
            Text(
                text = textOnChip,
                style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
                color = Color(0x99000000)
            )

            if (checkedState.value) {
                Spacer(modifier = Modifier.width(4.dp))
                DeleteIcon()
            }

        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddChipOnCard(
    textOnChip: String,
    onClick: () -> Unit,
) {

    Chip(
        onClick = onClick,
        shape = RoundedCornerShape(32.dp),
        colors = ChipDefaults.chipColors(backgroundColor = Color(0x80D6D6D6)),
    ) {
        Text(
            text = textOnChip,
            style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
            color = Color(0x99000000)
        )

        Spacer(modifier = Modifier.width(4.dp))

        OnlyAddIcon()

    }

}

@Composable
fun ChipsForList(
    textOnChip: String,
) {
    CustomChip(
        shape = RoundedCornerShape(32.dp),
        colors = ChipDefaults.chipColors(backgroundColor = Color(0x80D6D6D6)),
    ) {
        Text(
            text = textOnChip,
            style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
            color = Color(0x99000000)
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun CustomChip(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
    border: BorderStroke? = null,
    colors: ChipColors = ChipDefaults.chipColors(),
    leadingIcon: @Composable (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {

    val horizontalPadding = 12.dp
    val leadingIconStartSpacing = 4.dp
    val leadingIconEndSpacing = 8.dp

    val contentColor by colors.contentColor(enabled)
    Surface(
        modifier = modifier.semantics { role = Role.Button },
        shape = shape,
        color = colors.backgroundColor(enabled).value,
        contentColor = contentColor.copy(1.0f),
        border = border,
    ) {
        CompositionLocalProvider(LocalContentAlpha provides contentColor.alpha) {
            ProvideTextStyle(
                value = MaterialTheme.typography.body2
            ) {
                Row(
                    Modifier
                        .defaultMinSize(
                            minHeight = ChipDefaults.MinHeight
                        )
                        .padding(
                            start = if (leadingIcon == null) {
                                horizontalPadding
                            } else 0.dp,
                            end = horizontalPadding,
                        ),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (leadingIcon != null) {
                        Spacer(Modifier.width(leadingIconStartSpacing))
                        val leadingIconContentColor by colors.leadingIconContentColor(enabled)
                        CompositionLocalProvider(
                            LocalContentColor provides leadingIconContentColor,
                            LocalContentAlpha provides leadingIconContentColor.alpha,
                            content = leadingIcon
                        )
                        Spacer(Modifier.width(leadingIconEndSpacing))
                    }
                    content()
                }
            }
        }
    }
}