package fit.asta.health.old.course.session.listners

import androidx.appcompat.app.AppCompatActivity
import fit.asta.health.ActivityLauncher
import fit.asta.health.old.course.session.viewmodel.SessionViewModel
import javax.inject.Inject


class OnSessionClickListenerImpl(
    private val context: AppCompatActivity,
    private val viewModel: SessionViewModel
) : OnSessionClickListener {

    @Inject
    lateinit var launcher: ActivityLauncher

    override fun onSessionPlayClick() {

        launcher.launchVideoPlayerActivity(context, ArrayList(viewModel.getExercises()))
    }
}