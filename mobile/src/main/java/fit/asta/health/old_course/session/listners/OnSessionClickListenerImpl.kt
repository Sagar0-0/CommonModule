package fit.asta.health.old_course.session.listners

import androidx.appcompat.app.AppCompatActivity
import fit.asta.health.ActivityLauncher
import fit.asta.health.old_course.session.viewmodel.SessionViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class OnSessionClickListenerImpl(
    private val context: AppCompatActivity,
    private val viewModel: SessionViewModel
) :
    OnSessionClickListener, KoinComponent {

    private val launcher: ActivityLauncher by inject()

    override fun onSessionPlayClick() {

        launcher.launchVideoPlayerActivity(context, ArrayList(viewModel.getExercises()))
    }
}