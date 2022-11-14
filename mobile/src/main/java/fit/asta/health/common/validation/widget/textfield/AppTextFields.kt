package fit.asta.health.common.validation.widget.textfield

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.common.validation.state.ValidationState
import fit.asta.health.common.validation.util.TextFieldType
import fit.asta.health.ui.theme.ColorAmericanPurple
import fit.asta.health.ui.theme.IbarraNovaNormalGray14
import fit.asta.health.ui.theme.IbarraNovaSemiBoldPlatinum16

//import fit.asta.health.ui.theme.ColorAmericanPurple
//import fit.asta.health.ui.theme.IbarraNovaNormalGray14
//import fit.asta.health.ui.theme.IbarraNovaSemiBoldPlatinum16


@Composable
fun AuthenticationTextField(
    modifier: Modifier,
    state: ValidationState,
    @StringRes hint: Int,
    onValueChange: (String) -> Unit,
    type: TextFieldType,

    ) {

    CustomTextField(
        modifier = modifier,
        state = state,
        hint = hint,
        onValueChange = onValueChange,
        //textStyle = IbarraNovaSemiBoldPlatinum16,
        textStyle = IbarraNovaSemiBoldPlatinum16,
        hintTextStyle = IbarraNovaNormalGray14,
        //color = ColorAmericanPurple,
        color = ColorAmericanPurple,
        cornerRadius = 15.dp,
        type = type
    )

}