package fit.asta.health.old_course.session.ui

import android.app.Activity
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.R
import fit.asta.health.old_course.session.adapter.ExerciseAdapter
import fit.asta.health.old_course.session.adapter.listeners.OnExerciseClickListener
import fit.asta.health.old_course.session.data.SessionData
import fit.asta.health.old_course.session.listners.OnSessionClickListener
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl


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
            val recyclerExercise = it.findViewById<RecyclerView>(R.id.recyclerExercise)
            recyclerExercise.layoutManager = LinearLayoutManager(it.context)
            recyclerExercise.adapter = adapter
        }
    }

    override fun setAdapterClickListener(listener: OnExerciseClickListener) {
        rootView?.let {
            (it.findViewById<RecyclerView>(R.id.recyclerExercise).adapter as ExerciseAdapter).setAdapterClickListener(
                listener
            )
        }
    }

    override fun setSessionPlayClickListener(listener: OnSessionClickListener) {
        rootView?.let {
            it.findViewById<AppCompatImageView>(R.id.exercisePlay).setOnClickListener {
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
                it.findViewById(R.id.imgExercise)
            )
            it.findViewById<TextView>(R.id.txtWorkoutTitle).text = session.title
            it.findViewById<TextView>(R.id.txtInstructorName).text =
                "${it.context.getString(R.string.title_by)} ${session.author}"
            it.findViewById<TextView>(R.id.dayId).text =
                "${it.context.getString(R.string.title_day)} ${session.day} / ${session.totalDays}"
            it.findViewById<TextView>(R.id.txtLevelSubTitle).text = session.level
            it.findViewById<TextView>(R.id.txtDurationTime).text =
                "${session.duration} ${it.context.getString(R.string.title_min)}"
            it.findViewById<AppCompatRatingBar>(R.id.txtIntensity).rating = session.intensity
            it.findViewById<TextView>(R.id.txtCalories).text = "${session.calories}"

            (it.findViewById<RecyclerView>(R.id.recyclerExercise).adapter as ExerciseAdapter).updateList(
                session.exerciseList
            )
        }
    }
}