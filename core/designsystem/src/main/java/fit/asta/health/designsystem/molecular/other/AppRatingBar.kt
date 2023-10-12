package fit.asta.health.designsystem.molecular.other

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.smarttoolfactory.ratingbar.RatingBar
import com.smarttoolfactory.ratingbar.model.GestureStrategy
import com.smarttoolfactory.ratingbar.model.RateChangeStrategy
import com.smarttoolfactory.ratingbar.model.RatingInterval
import com.smarttoolfactory.ratingbar.model.ShimmerEffect
import fit.asta.health.core.designsystem.R
import fit.asta.health.designsystem.AppTheme


// Preview Function
@Preview("Light Rating")
@Preview(
    name = "Dark Rating",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    AppTheme {
        Surface {
            Column {
                AppRatingBar(
                    rating = 5f,
                    onRatingChange = {}
                )
            }
        }
    }
}


/** [AppRatingBar] that can be used for setting rating by passing a fixed value or using gestures
 * to change current [rating]
 * Available ImgSources = ImageBitmap/ImageVector/Painter
 * @param rating value to be set on this rating bar
 * @param painterEmpty background for rating items. Item with borders to
 * show empty values
 * @param painterFilled foreground for rating items. Filled item to show percentage of rating
 * @param tintEmpty color for background and foreground items
 * @param itemSize size of the rating item to be displayed. This is intrinsic size of image
 * or vector file by default
 * @param rateChangeStrategy whether rating change should happen instantly or with an animation
 * @param gestureStrategy drag and touch, touch only or no gesture is used to change rating
 * @param shimmerEffect shimmer effect for having a glow
 * @param itemCount maximum number of items
 * @param space space between rating items in dp
 * [RatingInterval.Half] returns multiples of 0.5, and [RatingInterval.Unconstrained] returns
 * current value without any limitation up to [itemSize]
 * @param allowZeroRating when true [RatingInterval.Full] or [RatingInterval.Half] allows
 * user to set value zero
 * @param onRatingChange callback to notify user when rating has changed. This is helpful
 * for getting change after tap or drag gesture
 *
 */
@Composable
fun AppRatingBar(
    rating: Float,
    modifier: Modifier = Modifier,
    painterEmpty: Painter = painterResource(R.drawable.star_background),
    painterFilled: Painter = painterResource(R.drawable.star_foreground),
    tintEmpty: Color = AppTheme.colors.primary,
    tintFilled: Color = AppTheme.colors.primary,
    itemSize: Dp = Dp.Unspecified,
    rateChangeStrategy: RateChangeStrategy = RateChangeStrategy.AnimatedChange(),
    gestureStrategy: GestureStrategy = GestureStrategy.DragAndPress,
    shimmerEffect: ShimmerEffect? = null,
    itemCount: Int = 5,
    space: Dp = AppTheme.spacing.level1,
    ratingInterval: RatingInterval = RatingInterval.Full,
    allowZeroRating: Boolean = true,
    onRatingChangeFinished: ((Float) -> Unit)? = null,
    onRatingChange: (Float) -> Unit
) {
    RatingBar(
        rating = rating,
        modifier = modifier,
        painterEmpty = painterEmpty,
        painterFilled = painterFilled,
        onRatingChange = onRatingChange,
        tintEmpty = tintEmpty,
        tintFilled = tintFilled,
        itemCount = itemCount,
        itemSize = itemSize,
        gestureStrategy = gestureStrategy,
        rateChangeStrategy = rateChangeStrategy,
        shimmerEffect = shimmerEffect,
        space = space,
        ratingInterval = ratingInterval,
        allowZeroRating = allowZeroRating,
        onRatingChangeFinished = onRatingChangeFinished
    )
}