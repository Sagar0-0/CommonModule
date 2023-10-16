package fit.asta.otpfield.configuration

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.otpfield.utils.EMPTY

private const val DEFAULT_PLACE_HOLDER = " "

/**
 * OTPConfigurations is a class that holds all the ui configurations for OhTeePeeInput.
 *
 * If you don't want to pass all the configurations, you can use [OTPConfigurations.withDefaults] to pass
 * only the configurations you want.
 *
 * @param cellModifier a [Modifier] for each cell.
 * @param elevation [Dp] applied for each cell.
 * @param cursorColor [Color] .
 * @param cellsCount [Int] number of cells to be shown.
 *
 * @param obscureText String when set, all the inputs will be visually replaces with this text.
 * It can be usefully when you have a Pin/Password View.
 *
 * Example when the [obscureText] = "`*`" the input will view "`****`"
 *
 * Note: it should be a single char.
 *
 * @param placeHolder String a string that will be used as a place holder for empty cells.
 * @param activeCellConfig [OTPCellConfiguration] cell ui configuration when it's focused.
 * @param errorCellConfig [OTPCellConfiguration] cell ui configuration when an error occurred.
 * @param emptyCellConfig [OTPCellConfiguration] cell ui configuration when it's empty & not focused.
 * @param filledCellConfig [OTPCellConfiguration] cell ui configuration when it's filled.
 * @param enableBottomLine when set to `true`, a bottom line will be drawn for each
 * cell instead of full shape using the other cell configurations.
 *
 * @see [OTPCellConfiguration.withDefaults]
 */
data class OTPConfigurations(
    val cellModifier: Modifier,
    val elevation: Dp,
    val cursorColor: Color,
    val cellsCount: Int,
    val obscureText: String,
    val placeHolder: String,
    val activeCellConfig: OTPCellConfiguration,
    val errorCellConfig: OTPCellConfiguration,
    val emptyCellConfig: OTPCellConfiguration,
    val filledCellConfig: OTPCellConfiguration,
    val enableBottomLine: Boolean,
) {

    companion object {
        @Composable
        fun withDefaults(
            cellsCount: Int,
            emptyCellConfig: OTPCellConfiguration,
            filledCellConfig: OTPCellConfiguration = emptyCellConfig,
            activeCellConfig: OTPCellConfiguration = emptyCellConfig.copy(
                borderColor = AppTheme.colors.primary
            ),
            errorCellConfig: OTPCellConfiguration = emptyCellConfig.copy(
                borderColor = AppTheme.colors.error
            ),
            cellModifier: Modifier = Modifier
                .padding(horizontal = AppTheme.spacing.level0)
                .size(AppTheme.customSize.level6),
            elevation: Dp = AppTheme.elevation.level0,
            cursorColor: Color = Color.Transparent,
            enableBottomLine: Boolean = false,
            obscureText: String = String.EMPTY,
            placeHolder: String = DEFAULT_PLACE_HOLDER,
        ) = OTPConfigurations(
            cellModifier = cellModifier,
            elevation = elevation,
            activeCellConfig = activeCellConfig,
            errorCellConfig = errorCellConfig,
            emptyCellConfig = emptyCellConfig,
            filledCellConfig = filledCellConfig,
            cursorColor = cursorColor,
            enableBottomLine = enableBottomLine,
            placeHolder = placeHolder,
            obscureText = obscureText,
            cellsCount = cellsCount
        )
    }
}