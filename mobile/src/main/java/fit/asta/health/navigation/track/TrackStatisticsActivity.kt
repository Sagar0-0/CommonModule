package fit.asta.health.navigation.track

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppScreen
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
import java.time.LocalDate

@AndroidEntryPoint
class TrackStatisticsActivity : ComponentActivity() {

    // Track View Model
    private lateinit var trackViewModel: TrackViewModel

    companion object {

        fun launch(context: Context, title: String, localDate: LocalDate) {
            val intent = Intent(context, TrackStatisticsActivity::class.java)
            intent.apply {
                putExtra("title", title)
                putExtra("date", localDate.dayOfMonth)
                putExtra("month", localDate.monthValue)
                putExtra("year", localDate.year)
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppScreen {

                // Track View Model
                trackViewModel = hiltViewModel()
                val title = intent.extras?.getString("title") ?: ""

                // Selected Date Data is received from the previous Intent
                val date = intent.extras?.getInt("date")
                val month = intent.extras?.getInt("month")
                val year = intent.extras?.getInt("year")

                // Setting the Date which was chosen in the Home Menu Screen
                if (date != null && month != null && year != null) {
                    val localDate = LocalDate.of(year, month, date)
                    trackViewModel.uiEventListener(TrackUiEvent.SetNewDate(localDate))
                }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),

                    topBar = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
                        ) {

                            Icon(
                                imageVector = Icons.Default.ArrowBackIos,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(18.dp)
                                    .clickable {
                                        finish()
                                    }
                            )

                            Text(
                                text = "${title[0].uppercase() + title.substring(1)} Statistics",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W500
                            )

                        }
                    }
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        ShowStatistics(title)
                    }
                }
            }
        }
    }

    @Composable
    private fun ShowStatistics(title: String) {

        // This checks the option chosen by the user and shows the UI Accordingly
        when (title) {

            "water" -> {

                trackViewModel.uiEventListener(
                    TrackUiEvent.SetTrackOption(TrackOption.WaterOption)
                )

                TrackWaterScreenControl(
                    waterTrackData = trackViewModel.waterDetails
                        .collectAsStateWithLifecycle().value,
                    calendarData = trackViewModel.calendarData
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
                    calendarData = trackViewModel.calendarData
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
                    calendarData = trackViewModel.calendarData
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
                    calendarData = trackViewModel.calendarData
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
                    calendarData = trackViewModel.calendarData
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
                    calendarData = trackViewModel.calendarData
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
                    calendarData = trackViewModel.calendarData
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
                    calendarData = trackViewModel.calendarData
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
                    calendarData = trackViewModel.calendarData
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
                    calendarData = trackViewModel.calendarData
                        .collectAsStateWithLifecycle().value,
                    setUiEvent = trackViewModel::uiEventListener
                )
            }
        }
    }
}