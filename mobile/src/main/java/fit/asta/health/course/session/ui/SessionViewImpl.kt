package fit.asta.health.course.session.ui

import android.app.Activity
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import fit.asta.health.R
import fit.asta.health.course.session.adapter.ExerciseAdapter
import fit.asta.health.course.session.adapter.listeners.OnExerciseClickListener
import fit.asta.health.course.session.data.SessionData
import fit.asta.health.course.session.listners.OnSessionClickListener
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl
import kotlinx.android.synthetic.main.course_session.view.*


class SessionViewImpl : SessionView {

    private var rootView: View? = null
    override fun setContentView(activity: Activity): View? {
        rootView = LayoutInflater.from(activity).inflate(
            R.layout.course_session, null,
            false
        )
        setupRecyclerView()
        return rootView
    }

    private fun setupRecyclerView() {
        rootView?.let {
            val adapter = ExerciseAdapter()
            it.recyclerExercise.layoutManager = LinearLayoutManager(it.context)
            it.recyclerExercise.adapter = adapter
        }
    }

    override fun setAdapterClickListener(listener: OnExerciseClickListener) {
        rootView?.let {
            (it.recyclerExercise.adapter as ExerciseAdapter).setAdapterClickListener(listener)
        }
    }

    override fun setSessionPlayClickListener(listener: OnSessionClickListener) {
        rootView?.let {
            it.exercisePlay.setOnClickListener {
                listener.onSessionPlayClick()
            }
        }
    }

    override fun changeState(state: SessionView.State) {
        when (state) {
            is SessionView.State.LoadSession -> updateSessionView(state.session)
            SessionView.State.Empty -> showEmpty()
            is SessionView.State.Error -> TODO()
        }
    }

    private fun showEmpty() {

    }

    private fun updateSessionView(session: SessionData) {
        rootView?.let {

            it.context.showImageByUrl(
                Uri.parse(getPublicStorageUrl(it.context, session.imgUrl)),
                it.imgExercise
            )
            it.txtWorkoutTitle.text = session.title
            it.txtInstructorName.text =
                "${it.context.getString(R.string.title_by)} ${session.author}"
            it.dayId.text =
                "${it.context.getString(R.string.title_day)} ${session.day} / ${session.totalDays}"
            it.txtLevelSubTitle.text = session.level
            it.txtDurationTime.text =
                "${session.duration} ${it.context.getString(R.string.title_min)}"
            it.txtIntensity.rating = session.intensity
            it.txtCalories.text = "${session.calories}"

            (it.recyclerExercise.adapter as ExerciseAdapter).updateList(session.exerciseList)
        }
    }
}