package fit.asta.health.old_course.session.adapter.listeners

import androidx.appcompat.app.AppCompatActivity
import fit.asta.health.ActivityLauncher
import fit.asta.health.old_course.session.viewmodel.SessionViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class OnExerciseClickListenerImpl(
    private val context: AppCompatActivity,
    private val viewModel: SessionViewModel
) :
    OnExerciseClickListener, KoinComponent {

    private val launcher: ActivityLauncher by inject()

    override fun onExerciseClick(position: Int) {

        launcher.launchVideoPlayerActivity(context, arrayListOf(viewModel.getExercise(position)))
    }
}