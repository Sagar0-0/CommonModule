package fit.asta.health.common.validation.widget.button

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fit.asta.health.ui.theme.IbarraNovaNormalGray14

//import fit.asta.health.ui.theme.ColorVerdigris
//import fit.asta.health.ui.theme.IbarraNovaSemiBoldGraniteGray


@Composable
fun FormButton(
    modifier: Modifier,
    @StringRes textId: Int,
    onClick: () -> Unit,
) {

    CustomButton(
        modifier = modifier,
        // color = ColorVerdigris,
        color = Color.Red,
        onClick = onClick,
        textId = textId,
        //textStyle = IbarraNovaSemiBoldGraniteGray,
        textStyle = IbarraNovaNormalGray14,
        cornerRadius = 25.dp
    )
}


