package fit.asta.health.old_course.session.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import fit.asta.health.R
import fit.asta.health.old_course.session.adapter.listeners.OnExerciseClickListenerImpl
import fit.asta.health.old_course.session.listners.OnSessionClickListenerImpl
import fit.asta.health.old_course.session.viewmodel.SessionObserver
import fit.asta.health.old_course.session.viewmodel.SessionViewModel
import org.koin.android.ext.android.inject


class SessionActivity : AppCompatActivity() {

    private val sessionView: SessionView by inject()
    private val viewModel: SessionViewModel by inject()

    companion object {

        private const val ARG_COURSE_ID = "courseId"
        private const val ARG_SESSION_ID = "sessionId"

        fun launch(context: Context, courseId: String, sessionId: String) {

            val intent = Intent(context, SessionActivity::class.java)
            intent.putExtra(ARG_COURSE_ID, courseId)
            intent.putExtra(ARG_SESSION_ID, sessionId)
            intent.apply {
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(sessionView.setContentView(this))
        sessionView.setAdapterClickListener(OnExerciseClickListenerImpl(this, viewModel))
        sessionView.setSessionPlayClickListener(OnSessionClickListenerImpl(this, viewModel))
        viewModel.observeSessionLiveData(this, SessionObserver(sessionView))
        viewModel.fetchSession(
            "",
            intent.getStringExtra(ARG_COURSE_ID)!!,
            intent.getStringExtra(ARG_SESSION_ID)!!
        )

        findViewById<AppCompatImageView>(R.id.imgBack).setOnClickListener {
            onBackPressed()
        }
    }
}