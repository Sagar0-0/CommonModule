package fit.asta.health.navigation.track

import androidx.annotation.DrawableRes
import fit.asta.health.R

/**
 * This contains all the tracking Options available in the App defined here
 *
 * @param uniqueId This is the unique Id and is unique for all the Tracking Option
 * @param displayImage This is the display Image for the specific Option
 * @param displayText This is the display text for the specific Option
 */
sealed class TrackingOptions(
    val uniqueId: Int,
    @DrawableRes val displayImage: Int,
    val displayText: String
) {

    // Water Option
    object WaterTrackingOption : TrackingOptions(
        uniqueId = 1,
        displayImage = R.drawable.heartrate,
        displayText = "Water Statistics"
    )

    // Breathing Option
    object BreathingTrackingOption : TrackingOptions(
        uniqueId = 2,
        displayImage = R.drawable.heartrate,
        displayText = "Breathing Statistics"
    )

    // Meditation Option
    object MeditationTrackingOption : TrackingOptions(
        uniqueId = 3,
        displayImage = R.drawable.heartrate,
        displayText = "Meditation Statistics"
    )

    // Sunlight Option
    object SunlightTrackingOption : TrackingOptions(
        uniqueId = 4,
        displayImage = R.drawable.heartrate,
        displayText = "Sunlight Statistics"
    )

    // Step Option
    object StepTrackingOption : TrackingOptions(
        uniqueId = 5,
        displayImage = R.drawable.heartrate,
        displayText = "Step Statistics"
    )

    // Sleep Option
    object SleepTrackingOption : TrackingOptions(
        uniqueId = 6,
        displayImage = R.drawable.heartrate,
        displayText = "Sleep Statistics"
    )

    companion object {

        // This variable contains all the tracking Options in a list which can be used by others
        val trackingOptionsList = listOf(
            WaterTrackingOption,
            BreathingTrackingOption,
            MeditationTrackingOption,
            SunlightTrackingOption,
            StepTrackingOption,
            SleepTrackingOption
        )
    }
}
