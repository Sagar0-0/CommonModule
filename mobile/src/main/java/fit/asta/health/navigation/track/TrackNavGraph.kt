package fit.asta.health.navigation.track

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import fit.asta.health.feature.spotify.SpotifyLoginActivity

@Composable
fun TrackNavGraph() {

//    val trackViewModel: TrackViewModel = hiltViewModel()
//    val navController = rememberNavController()

    SpotifyLoginActivity.launch(LocalContext.current)


//    NavHost(
//        navController = navController,
//        startDestination = TrackDestination.TrackMenu.route,
//        builder = {
//
//            // Menu Screen for Tracking Stats
//            composable(
//                TrackDestination.TrackMenu.route,
//                content = {
//
//                    val homeMenuState = trackViewModel.homeScreenDetails
//                        .collectAsStateWithLifecycle().value
//
//                    TrackMenuScreenControl(
//                        homeMenuState = homeMenuState,
//                        loadHomeData = trackViewModel::getHomeDetails,
//                        setUiEvent = trackViewModel::uiEventListener,
//                        navigator = { navController.navigate(it) }
//                    )
//                }
//            )
//
//            // Detail Screen for Water Tracking Details
//            composable(
//                TrackDestination.WaterTrackDetail.route,
//                content = {
//
//                    val waterTrackData = trackViewModel.waterDetails
//                        .collectAsStateWithLifecycle().value
//
//                    TrackWaterScreenControl(
//                        waterTrackData = waterTrackData,
//                        setUiEvent = trackViewModel::uiEventListener
//                    )
//                }
//            )
//
//            // Detail Screen for Steps Tracking Details
//            composable(
//                TrackDestination.StepsTrackDetail.route,
//                content = {
//
//                    val stepsTrackData = trackViewModel.stepsDetails
//                        .collectAsStateWithLifecycle().value
//
//                    TrackStepsScreenControl(
//                        stepsTrackData = stepsTrackData,
//                        setUiEvent = trackViewModel::uiEventListener
//                    )
//                }
//            )
//
//            // Detail Screen for Sleep Tracking Details
//            composable(
//                TrackDestination.SleepTrackDetail.route,
//                content = {
//
//                    val sleepTrackData = trackViewModel.sleepDetails
//                        .collectAsStateWithLifecycle().value
//
//                    TrackSleepScreenControl(
//                        sleepTrackData = sleepTrackData,
//                        setUiEvent = trackViewModel::uiEventListener
//                    )
//                }
//            )
//
//            // Detail Screen for Sunlight Tracking Details
//            composable(
//                TrackDestination.SunlightTrackDetail.route,
//                content = {
//
//                    val sunlightTrackData = trackViewModel.sunlightDetails
//                        .collectAsStateWithLifecycle().value
//
//                    TrackSunlightScreenControl(
//                        sunlightTrackData = sunlightTrackData,
//                        setUiEvent = trackViewModel::uiEventListener
//                    )
//                }
//            )
//
//            // Detail Screen for Breathing Tracking Details
//            composable(
//                TrackDestination.BreathingTrackDetail.route,
//                content = {
//
//                    val breathingTrackData = trackViewModel.breathingDetails
//                        .collectAsStateWithLifecycle().value
//
//                    TrackBreathingScreenControl(
//                        breathingTrackData = breathingTrackData,
//                        setUiEvent = trackViewModel::uiEventListener
//                    )
//                }
//            )
//
//            // Detail Screen for Meditation Tracking Details
//            composable(
//                TrackDestination.MeditationTrackDetail.route,
//                content = {
//
//                    val meditationTrackData = trackViewModel.meditationDetails
//                        .collectAsStateWithLifecycle().value
//
//                    TrackMeditationScreenControl(
//                        meditationTrackData = meditationTrackData,
//                        setUiEvent = trackViewModel::uiEventListener
//                    )
//                }
//            )
//
//            // Detail Screen for Exercise(Yoga , Dance , Hiit , workout) Tracking Details
//            composable(
//                TrackDestination.ExerciseTrackDetail.route,
//                content = {
//
//                    val exerciseTrackData = trackViewModel.exerciseDetails
//                        .collectAsStateWithLifecycle().value
//
//                    TrackExerciseScreenControl(
//                        exerciseTrackData = exerciseTrackData,
//                        setUiEvent = trackViewModel::uiEventListener
//                    )
//                }
//            )
//        }
//    )
}