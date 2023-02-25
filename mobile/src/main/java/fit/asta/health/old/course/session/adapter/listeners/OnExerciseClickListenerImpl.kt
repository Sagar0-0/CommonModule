package fit.asta.health.old.course.session.adapter.listeners

import androidx.appcompat.app.AppCompatActivity
import fit.asta.health.ActivityLauncher
import fit.asta.health.old.course.session.viewmodel.SessionViewModel
import javax.inject.Inject


class OnExerciseClickListenerImpl(
    private val context: AppCompatActivity,
    private val viewModel: SessionViewModel
) : OnExerciseClickListener {

    @Inject
    lateinit var launcher: ActivityLauncher

    override fun onExerciseClick(position: Int) {

        launcher.launchVideoPlayerActivity(context, arrayListOf(viewModel.getExercise(position)))
    }
}