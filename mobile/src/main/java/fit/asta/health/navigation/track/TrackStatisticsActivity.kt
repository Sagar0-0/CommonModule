package fit.asta.health.navigation.track

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.navigation.track.ui.screens.TrackBreathingScreenControl
import fit.asta.health.navigation.track.ui.screens.TrackExerciseScreenControl
import fit.asta.health.navigation.track.ui.screens.TrackMeditationScreenControl
import fit.asta.health.navigation.track.ui.screens.TrackSleepScreenControl
import fit.asta.health.navigation.track.ui.screens.TrackStepsScreenControl
import fit.asta.health.navigation.track.ui.screens.TrackSunlightScreenControl
import fit.asta.health.navigation.track.ui.screens.TrackWaterScreenControl
import fit.asta.health.navigation.track.ui.util.TrackOption
import fit.asta.health.navigation.track.ui.util.TrackUiEvent
import fit.asta.health.navigation.track.ui.viewmodel.TrackViewModel

@AndroidEntryPoint
class TrackStatisticsActivity : ComponentActivity() {

    // Track View Model
    private lateinit var trackViewModel: TrackViewModel

    companion object {

        fun launch(context: Context, title: String) {
            val intent = Intent(context, TrackStatisticsActivity::class.java)
            intent.apply {
                putExtra("title", title)
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    // Track View Model
                    trackViewModel = hiltViewModel()

                    // This checks the option chosen by the user and shows the UI Accordingly
                    when (intent.extras?.getString("title") ?: "") {

                        "water" -> {

                            trackViewModel.uiEventListener(
                                TrackUiEvent.SetTrackOption(TrackOption.WaterOption)
                            )

                            TrackWaterScreenControl(
                                waterTrackData = trackViewModel.waterDetails
                                    .collectAsStateWithLifecycle().value,
                                setUiEvent = trackViewModel::uiEventListener
                            )
                        }

                        "meditation" -> {

                            trackViewModel.uiEventListener(
                                TrackUiEvent.SetTrackOption(TrackOption.MeditationOption)
                            )

                            TrackMeditationScreenControl(
                                meditationTrackData = trackViewModel.meditationDetails
                                    .collectAsStateWithLifecycle().value,
                                setUiEvent = trackViewModel::uiEventListener
                            )
                        }

                        "breathing" -> {

                            trackViewModel.uiEventListener(
                                TrackUiEvent.SetTrackOption(TrackOption.BreathingOption)
                            )

                            TrackBreathingScreenControl(
                                breathingTrackData = trackViewModel.breathingDetails
                                    .collectAsStateWithLifecycle().value,
                                setUiEvent = trackViewModel::uiEventListener
                            )
                        }

                        "sunlight" -> {

                            trackViewModel.uiEventListener(
                                TrackUiEvent.SetTrackOption(TrackOption.SunlightOption)
                            )

                            TrackSunlightScreenControl(
                                sunlightTrackData = trackViewModel.sunlightDetails
                                    .collectAsStateWithLifecycle().value,
                                setUiEvent = trackViewModel::uiEventListener
                            )
                        }

                        "sleep" -> {

                            trackViewModel.uiEventListener(
                                TrackUiEvent.SetTrackOption(TrackOption.SleepOption)
                            )

                            TrackSleepScreenControl(
                                sleepTrackData = trackViewModel.sleepDetails
                                    .collectAsStateWithLifecycle().value,
                                setUiEvent = trackViewModel::uiEventListener
                            )
                        }

                        "yoga" -> {

                            trackViewModel.uiEventListener(
                                TrackUiEvent.SetTrackOption(TrackOption.YogaOption)
                            )

                            TrackExerciseScreenControl(
                                exerciseTrackData = trackViewModel.exerciseDetails
                                    .collectAsStateWithLifecycle().value,
                                setUiEvent = trackViewModel::uiEventListener
                            )
                        }

                        "dance" -> {

                            trackViewModel.uiEventListener(
                                TrackUiEvent.SetTrackOption(TrackOption.DanceOption)
                            )

                            TrackExerciseScreenControl(
                                exerciseTrackData = trackViewModel.exerciseDetails
                                    .collectAsStateWithLifecycle().value,
                                setUiEvent = trackViewModel::uiEventListener
                            )
                        }

                        "workout" -> {

                            trackViewModel.uiEventListener(
                                TrackUiEvent.SetTrackOption(TrackOption.WorkoutOption)
                            )

                            TrackExerciseScreenControl(
                                exerciseTrackData = trackViewModel.exerciseDetails
                                    .collectAsStateWithLifecycle().value,
                                setUiEvent = trackViewModel::uiEventListener
                            )
                        }

                        "hiit" -> {

                            trackViewModel.uiEventListener(
                                TrackUiEvent.SetTrackOption(TrackOption.HiitOption)
                            )

                            TrackExerciseScreenControl(
                                exerciseTrackData = trackViewModel.exerciseDetails
                                    .collectAsStateWithLifecycle().value,
                                setUiEvent = trackViewModel::uiEventListener
                            )
                        }

                        "walking" -> {

                            trackViewModel.uiEventListener(
                                TrackUiEvent.SetTrackOption(TrackOption.StepsOption)
                            )

                            TrackStepsScreenControl(
                                stepsTrackData = trackViewModel.stepsDetails
                                    .collectAsStateWithLifecycle().value,
                                setUiEvent = trackViewModel::uiEventListener
                            )
                        }
                    }
                }
            }
        }
    }
}