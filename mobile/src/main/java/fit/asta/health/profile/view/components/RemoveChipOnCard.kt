package fit.asta.health.profile.view.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RemoveChipOnCard(
    textOnChip: String,
    checkedState: (MutableState<Boolean>)? = null,
    isEnabled: Boolean? = false,
    onClick: () -> Unit = {},
) {

    checkedState?.let {
        AssistChip(
            label = {
                Text(
                    text = textOnChip,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0x99000000)
                )
            },
            onClick = onClick,
            shape = RoundedCornerShape(32.dp),
            colors = AssistChipDefaults.assistChipColors(containerColor = Color(0x80D6D6D6)),
            enabled = it.value,
            trailingIcon = {
                if (checkedState.value) {
                    ProfileDeleteIcon()
                }
            }
        )
    }
}

@Composable
fun AddChipOnCard(
    textOnChip: String,
    onClick: () -> Unit,
) {

    AssistChip(
        label = {
            Text(
                text = textOnChip,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0x99000000)
            )
        },
        onClick = onClick,
        shape = RoundedCornerShape(32.dp),
        colors = AssistChipDefaults.assistChipColors(containerColor = Color(0x80D6D6D6)),
        trailingIcon = {
            ProfileOnlyAddIcon()
        }
    )
}

@Composable
fun ChipsForList(
    textOnChip: String,
) {
    AssistChip(
        label = {
            Text(
                text = textOnChip,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0x99000000)
            )
        },
        onClick = {},
        shape = RoundedCornerShape(32.dp),
        colors = AssistChipDefaults.assistChipColors(containerColor = Color(0x80D6D6D6)),
        trailingIcon = {
            ProfileOnlyAddIcon()
        }
    )
}

/*
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
}*/