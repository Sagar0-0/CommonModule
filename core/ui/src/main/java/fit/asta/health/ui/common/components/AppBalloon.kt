package fit.asta.health.ui.common.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.BalloonWindow
import com.skydoves.balloon.compose.rememberBalloonBuilder
import com.skydoves.balloon.compose.setBackgroundColor
import fit.asta.health.designsystem.AppTheme


@Composable
fun AppBalloon(
    content: @Composable (click: () -> Unit) -> Unit,
    balloonContent: @Composable () -> Unit,
    modifier : Modifier = Modifier,
    time:Long=1000L
) {
    val onClick: (BalloonWindow) -> Unit = {
        it.showAlignBottom()
    }
    val color=AppTheme.colors.primaryContainer
    // create and remember a builder of Balloon.
    val builder = rememberBalloonBuilder {
        setArrowSize(10)
        setArrowPosition(0.5f)
        setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
        setHeight(BalloonSizeSpec.WRAP)
        setPadding(12)
        setMarginHorizontal(12)
//        setWidthRatio(0.8f)
        setBackgroundColor(color)
        setCornerRadius(8f)
        setBalloonAnimation(BalloonAnimation.ELASTIC)
        setAutoDismissDuration(time)
    }
    Balloon(
        modifier = modifier,
        builder = builder,
        balloonContent = balloonContent,
        content = {
            content {
                onClick.invoke(it)
            }
        }
    )
}

