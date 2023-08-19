package fit.asta.health.navigation.track.view.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fit.asta.health.navigation.track.view.screens.TrackBreathingScreenControl
import fit.asta.health.navigation.track.view.screens.TrackExerciseScreenControl
import fit.asta.health.navigation.track.view.screens.TrackMeditationScreenControl
import fit.asta.health.navigation.track.view.screens.TrackMenuScreenControl
import fit.asta.health.navigation.track.view.screens.TrackSleepScreenControl
import fit.asta.health.navigation.track.view.screens.TrackStepsScreenControl
import fit.asta.health.navigation.track.view.screens.TrackSunlightScreenControl
import fit.asta.health.navigation.track.view.screens.TrackWaterScreenControl
import fit.asta.health.navigation.track.viewmodel.TrackViewModel

/**
 * This is the navigation Host function for the Tracking Feature
 *
 * @param navController This is the navController for the Tracking Screens
 * @param trackViewModel This is the View Model for all the Tracking Screen
 */
@Composable
fun TrackNavGraph(
    navController: NavHostController,
    trackViewModel: TrackViewModel
) {

    NavHost(
        navController = navController,
        startDestination = TrackNavRoute.TrackMenu.route,
        builder = {

            // Menu Screen for Tracking Stats
            composable(
                TrackNavRoute.TrackMenu.route,
                content = {

                    val homeMenuState = trackViewModel.homeScreenDetails
                        .collectAsStateWithLifecycle().value

                    TrackMenuScreenControl(
                        homeMenuState = homeMenuState,
                        loadHomeData = trackViewModel::getHomeDetails,
                        setUiEvent = trackViewModel::uiEventListener,
                        navigator = { navController.navigate(it) }
                    )
                }
            )

            // Detail Screen for Water Tracking Details
            composable(
                TrackNavRoute.WaterTrackDetail.route,
                content = {

                    val waterTrackData = trackViewModel.waterDetails
                        .collectAsStateWithLifecycle().value

                    TrackWaterScreenControl(
                        waterTrackData = waterTrackData,
                        setUiEvent = trackViewModel::uiEventListener
                    )
                }
            )

            // Detail Screen for Steps Tracking Details
            composable(
                TrackNavRoute.StepsTrackDetail.route,
                content = {

                    val stepsTrackData = trackViewModel.stepsDetails
                        .collectAsStateWithLifecycle().value

                    TrackStepsScreenControl(
                        stepsTrackData = stepsTrackData,
                        setUiEvent = trackViewModel::uiEventListener
                    )
                }
            )

            // Detail Screen for Sleep Tracking Details
            composable(
                TrackNavRoute.SleepTrackDetail.route,
                content = {

                    val sleepTrackData = trackViewModel.sleepDetails
                        .collectAsStateWithLifecycle().value

                    TrackSleepScreenControl(
                        sleepTrackData = sleepTrackData,
                        setUiEvent = trackViewModel::uiEventListener
                    )
                }
            )

            // Detail Screen for Sunlight Tracking Details
            composable(
                TrackNavRoute.SunlightTrackDetail.route,
                content = {

                    val sunlightTrackData = trackViewModel.sunlightDetails
                        .collectAsStateWithLifecycle().value

                    TrackSunlightScreenControl(
                        sunlightTrackData = sunlightTrackData,
                        setUiEvent = trackViewModel::uiEventListener
                    )
                }
            )

            // Detail Screen for Breathing Tracking Details
            composable(
                TrackNavRoute.BreathingTrackDetail.route,
                content = {

                    val breathingTrackData = trackViewModel.breathingDetails
                        .collectAsStateWithLifecycle().value

                    TrackBreathingScreenControl(
                        breathingTrackData = breathingTrackData,
                        setUiEvent = trackViewModel::uiEventListener
                    )
                }
            )

            // Detail Screen for Meditation Tracking Details
            composable(
                TrackNavRoute.MeditationTrackDetail.route,
                content = {

                    val meditationTrackData = trackViewModel.meditationDetails
                        .collectAsStateWithLifecycle().value

                    TrackMeditationScreenControl(
                        meditationTrackData = meditationTrackData,
                        setUiEvent = trackViewModel::uiEventListener
                    )
                }
            )

            // Detail Screen for Exercise(Yoga , Dance , Hiit , workout) Tracking Details
            composable(
                TrackNavRoute.ExerciseTrackDetail.route,
                content = {

                    val exerciseTrackData = trackViewModel.exerciseDetails
                        .collectAsStateWithLifecycle().value

                    TrackExerciseScreenControl(
                        exerciseTrackData = exerciseTrackData,
                        setUiEvent = trackViewModel::uiEventListener
                    )
                }
            )
        }
    )
}